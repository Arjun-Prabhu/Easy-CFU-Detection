package com.example.archived;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.features2d.SimpleBlobDetector;
import org.opencv.features2d.SimpleBlobDetector_Params;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

public class BlobDetection {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        // Load the image
        String imagePath = "C:/Users/arjun/IdeaProjects/CFUAnalysis/src/main/java/com/example/assets/IMG_3451.jpeg"; // Replace with the path to your Petri dish image
        Mat src = Imgcodecs.imread(imagePath);

        if (src.empty()) {
            System.out.println("Image not found!");
            return;
        }

        // Convert the image to grayscale
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        // Set up the SimpleBlobDetector parameters
        SimpleBlobDetector_Params params = new SimpleBlobDetector_Params();
        params.set_filterByArea(true);
        params.set_minArea(10);  // Minimum blob size (adjust as needed)
        params.set_maxArea(1000); // Maximum blob size (adjust as needed)

        params.set_filterByCircularity(true);
        params.set_minCircularity(0.8F); // Only detect circular blobs

        params.set_filterByConvexity(true);
        params.set_minConvexity(0.8F);

        params.set_filterByInertia(true);
        params.set_minInertiaRatio(0.5F); // Detect round blobs

        // Create the blob detector
        SimpleBlobDetector detector = SimpleBlobDetector.create(params);

        // Detect blobs
        MatOfKeyPoint keypoints = new MatOfKeyPoint();
        detector.detect(gray, keypoints);

        // Draw detected blobs
        Mat output = src.clone();
        Features2d.drawKeypoints(src, keypoints, output, new Scalar(0, 255, 0));

        // Print the number of blobs detected
        System.out.println("Detected Dots: " + keypoints.toList().size());

        // Save the result
        String outputPath = "detected_blobs.jpg"; // Replace with your desired output path
        Imgcodecs.imwrite(outputPath, output);
        System.out.println("Result saved to: " + outputPath);
    }
}
