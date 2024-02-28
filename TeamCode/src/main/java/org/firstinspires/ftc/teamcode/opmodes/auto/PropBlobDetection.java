package org.firstinspires.ftc.teamcode.opmodes.auto;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class PropBlobDetection extends OpenCvPipeline {

    /*
    YELLOW  = Parking Right
    CYAN    = Parking Left
    MAGENTA = Parking Center
    */

    // Dimensions of the camera image
    private final int width = 544;

    // Color definitions
    private final Scalar
            YELLOW = new Scalar(255, 255, 0),
            CYAN = new Scalar(0, 255, 255),
            MAGENTA = new Scalar(255, 0, 0);
    // Running variable storing the parking position
    private volatile PropBlobPosition position = PropBlobPosition.LEFT;
    private Scalar lowHSV;
    private Scalar highHSV;

    public enum AllianceColor {
        RED,
        BLUE
    }

    public PropBlobDetection(AllianceColor color) {
        if (color == AllianceColor.RED) {
            lowHSV = new Scalar(0, 100, 100); // lower bound HSV for red
            highHSV = new Scalar(40, 255, 255); // higher bound HSV for red
        } else if (color == AllianceColor.BLUE) {
            lowHSV = new Scalar(90, 100, 100); // lower bound HSV for blue
            highHSV = new Scalar(145, 255, 255); // higher bound HSV for blue
        }
    }

    Mat mat = new Mat();
    Mat thresh = new Mat();
    Mat edges = new Mat();
    Mat hierarchy = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        // DETECTING COLOR THROUGH A THRESHOLD, HSV because RGB is less reliable with lighting changes
        // Make a working copy of the input matrix in HSV
//        Mat mat = new Mat();
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        // Threshold the image to get only red values
        // TODO: Tune the HSV values (red wraps around 180)
//        Mat thresh = new Mat();
        Core.inRange(mat, lowHSV, highHSV, thresh);

        // blur image?
        //Mat blur = new Mat();
        //Imgproc.GaussianBlur(thresh, blur, new Size(3, 3), 0, 0);

        // Original used to be blob detect with connected component labelling.

        // Run edge detector
//        Mat edges = new Mat();
        Imgproc.Canny(thresh, edges, 100, 300);
        // CANNY EDGE DETECTOR

        // Find contours?
        List<MatOfPoint> contours = new ArrayList<>();
//        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        MatOfPoint2f[] contoursPoly  = new MatOfPoint2f[contours.size()];
        Rect[] boundRect = new Rect[contours.size()];
        for (int i = 0; i < contours.size(); i++) {
            contoursPoly[i] = new MatOfPoint2f();
            Imgproc.approxPolyDP(new MatOfPoint2f(contours.get(i).toArray()), contoursPoly[i], 3, true);
            boundRect[i] = Imgproc.boundingRect(new MatOfPoint(contoursPoly[i].toArray()));
        }

        // Edges are polygons, largest one is assumed to be the prop

        // bounding boxes
        double maxArea = 0;
        int maxAreaX = 0;
        for (int i = 0; i != boundRect.length; i++) {
            if (boundRect[i].area() > maxArea) {
                maxArea = boundRect[i].area();
                maxAreaX = boundRect[i].x;
            }
            // draw red bounding rectangles on mat
            // the mat has been converted to HSV so we need to use HSV as well
            Imgproc.rectangle(mat, boundRect[i], new Scalar(0.5, 76.9, 89.8));
        }

        if (maxAreaX < width / 3) {
            position = PropBlobPosition.LEFT;
//            Imgproc.rectangle(
//                    mat,
//                    sleeve_left_point_A,
//                    sleeve_left_point_B,
//                    CYAN,
//                    2
//            );
        } else if (maxAreaX < width * 2 / 3){
            position = PropBlobPosition.CENTER;
//            Imgproc.rectangle(
//                    mat,
//                    sleeve_center_point_A,
//                    sleeve_center_point_B,
//                    MAGENTA,
//                    2
//            );
        } else {
            position = PropBlobPosition.RIGHT;
//            Imgproc.rectangle(
//                    mat,
//                    sleeve_right_point_A,
//                    sleeve_right_point_B,
//                    YELLOW,
//                    2
//            );
        }
        return mat;
    }

    public enum PropBlobPosition {
        LEFT,
        CENTER,
        RIGHT,
    }

    // Returns an enum being the current position where the robot will park
    public PropBlobPosition getPosition() {
        return position;
    }
}
