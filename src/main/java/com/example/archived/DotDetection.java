package com.example.archived;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.*;

import java.util.ArrayList;
import java.util.List;

public class DotDetection {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        // Load the image
        String imagePath = "C:/Users/arjun/Downloads/test_cropped.jpg"; // Replace with the path to your Petri dish image
        Mat src = Imgcodecs.imread(imagePath);

        if (src.empty()) {
            System.out.println("Image not found!");
            return;
        }

        // Preprocess the image
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY); // Convert to grayscale

        Mat blurred = new Mat();
        Imgproc.GaussianBlur(gray, blurred, new Size(5, 5), 0); // Apply Gaussian blur

        // Threshold the image
        Mat binary = new Mat();
        Imgproc.threshold(blurred, binary, 128, 255, Imgproc.THRESH_BINARY_INV); // Invert binary

        // Find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Draw detected dots
        Mat output = src.clone();
        int dotCount = 0;
        for (MatOfPoint contour : contours) {
            // Filter by size to exclude noise
            double area = Imgproc.contourArea(contour);
            if (area > 10) { // Adjust threshold as needed
                dotCount++;

                // Draw the contour and center
                Rect boundingBox = Imgproc.boundingRect(contour);
                Point center = new Point(boundingBox.x + boundingBox.width / 2.0, boundingBox.y + boundingBox.height / 2.0);

                Imgproc.circle(output, center, 5, new Scalar(0, 255, 0), -1); // Draw center
                Imgproc.drawContours(output, List.of(contour), -1, new Scalar(255, 0, 0), 2); // Draw contour
            }
        }

        System.out.println("Detected Dots: " + dotCount);

        // Save the result
        String outputPath = "detected_dots.jpg"; // Replace with your desired output path
        Imgcodecs.imwrite(outputPath, output);
        System.out.println("Result saved to: " + outputPath);
    }
}
