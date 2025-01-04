package com.example.cfuanalysis;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

public class UsefulMethods {



    public static void convertImage(BufferedImage input, WritableImage output) {
        PixelWriter writer;
        if (input == null) {
            System.out.println("Uh-oh");
        } else {
            writer = output.getPixelWriter();
            for (int i = 0; i < input.getWidth(); i++) {
                for (int j = 0; j < input.getHeight(); j++) {
                    writer.setArgb(i, j, input.getRGB(i, j));
                }
            }
        }
    }

    public static void convertBgrToRgb(byte[] input) {
        for (int i = 0; i < input.length; i = i + 3) {
            byte blue = input[i];
            input[i] = input[i + 2]; // Blue to red
            input[i + 2] = blue;
        }
    }
}
//
//    Mat colorImage = new Mat();
//        Imgproc.cvtColor(resizedImage, colorImage, Imgproc.COLOR_GRAY2BGR);
//
//                SimpleBlobDetector_Params params = new SimpleBlobDetector_Params();
//                params.set_filterByCircularity(true);
//                params.set_minCircularity((float) 0.65);
//                MatOfKeyPoint cfus = new MatOfKeyPoint();
//                SimpleBlobDetector detector = SimpleBlobDetector.create(params);
//                detector.detect(colorImage, cfus);
//
//                Features2d.drawKeypoints(colorImage, cfus, colorImage, new Scalar(0, 0, 255));
//
//                return colorImage;

