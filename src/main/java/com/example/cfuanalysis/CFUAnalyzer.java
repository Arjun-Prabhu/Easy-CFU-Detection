package com.example.cfuanalysis;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.opencv.core.*;
import org.opencv.features2d.Feature2D;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.SimpleBlobDetector;
import org.opencv.features2d.SimpleBlobDetector_Params;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static com.example.cfuanalysis.UsefulMethods.convertBgrToRgb;
import static com.example.cfuanalysis.UsefulMethods.convertImage;
import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.core.CvType.depth;
import static org.opencv.imgproc.Imgproc.*;

public class CFUAnalyzer extends Application {
    static WritableImage image;
    static BufferedImage bufferedImage;
    static Mat orgImage;

    public static void main(String[] args) {
        initializeImage();
        launch();
    }

    public static void initializeImage() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String input = "C:/Users/arjun/Downloads/Drawing-130.sketchpad.jpeg";
        orgImage = Imgcodecs.imread(input);

        Mat grayImage = new Mat();
        cvtColor(orgImage, grayImage, COLOR_RGB2GRAY);

        Mat blurred = new Mat();
        GaussianBlur(grayImage,blurred,new Size(1,1),0);

        Mat binImage = new Mat();
        threshold(blurred,binImage,115.75,255,THRESH_BINARY_INV);

        Mat detectedMat = detectCFUS(binImage);
        Imgcodecs.imwrite("completedAnalysis.jpg", detectedMat);
        //createImage(binImage);
    }

    private static void createImage(Mat resizedImage) {
        byte[] imgArray = new byte[resizedImage.rows() * resizedImage.cols() * (int) resizedImage.elemSize()];
        resizedImage.get(0, 0, imgArray);

        bufferedImage = new BufferedImage(resizedImage.cols(), resizedImage.rows(), BufferedImage.TYPE_BYTE_BINARY);
        bufferedImage.getRaster().setDataElements(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), imgArray);

        image = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
        convertImage(bufferedImage, image);
    }

    public static Mat detectCFUS(Mat input) {
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(input, contours, hierarchy, Imgproc.RETR_LIST, CHAIN_APPROX_SIMPLE);

        System.out.println("Contours found: " + contours.size());

        // Create an output image in BGR format to draw contours
        Mat outputImage = orgImage.clone(); // Black background

        for (MatOfPoint contour : contours) {
            Rect boundingBox = Imgproc.boundingRect(contour);
            Point center = new Point(boundingBox.x + boundingBox.width / 2.0, boundingBox.y + boundingBox.height / 2.0);

            Imgproc.circle(outputImage, center, 5, new Scalar(0, 255, 0), -1); // Draw center
            Imgproc.drawContours(outputImage, List.of(contour), -1, new Scalar(255, 0, 0), 2); // Draw contour
        }

        // Convert from BGR to RGB for proper display
        Mat rgbImage = new Mat();
        Imgproc.cvtColor(outputImage, rgbImage, Imgproc.COLOR_BGR2RGB);

        return rgbImage; // Return the image with contours
    }

    @Override
    public void start(Stage stage) throws Exception {
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        Scene scene = new Scene(new BorderPane(imageView));
        stage.setScene(scene);
        stage.show();
    }

}