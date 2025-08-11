package org.firstinspires.ftc.teamcode.pedroPathing.Tests;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import org.openftc.easyopencv.OpenCvPipeline;

/*
 *       x-----------------> 320
 *      y|
 *       |
 *       |
 *       |
 *       |
 *       v 240
* */

public class ColorDetectorBluePipeline extends OpenCvPipeline {

    public Scalar lowerHSV = new Scalar(101, 50, 38); //Valor mas bajo pa detectar
    public Scalar upperHSV = new Scalar(135, 255.0, 255.0);  //Valor mas Alto pa detectar

    private Mat hsvMat = new Mat(); //Guarda el frame convertido a hsv
    private Mat hsvBinaryMat = new Mat();   //Guarda el frame convertido a 1/0

    private ArrayList<MatOfPoint> contours = new ArrayList<>();
    private Mat hierarchy = new Mat();  //pa encontrar contornos

    private MatOfPoint biggestContour = null;

    public Scalar lineColor = new Scalar(0.0, 255.0, 0.0, 0.0); //Color de los contornos (Verde)
    public int lineThickness = 3;

    private Mat inputContours = new Mat();  //frame con contornos

    public volatile double crosshairX = -1;
    public volatile double crosshairY = -1;
    public volatile boolean targetVisible = false;


    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV); //RGB->HSV
        Core.inRange(hsvMat, lowerHSV, upperHSV, hsvBinaryMat); //toma valores de lowhsv/uphsv y los convierte en 1, otros colores en 0
        //Encontrar Contornos:
        contours.clear();
        hierarchy.release();
        Imgproc.findContours(hsvBinaryMat, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        //Encontrar Contorno mas grande
        this.biggestContour = null;
        for(MatOfPoint contour : contours) {
            if((biggestContour == null) || (Imgproc.contourArea(contour) > Imgproc.contourArea(biggestContour))) {
                this.biggestContour = contour;
            }
        }

        input.copyTo(inputContours);
        Imgproc.drawContours(inputContours, contours, -1, lineColor, lineThickness);

        if(biggestContour != null) {
            Rect boundingRect = Imgproc.boundingRect(biggestContour);

            crosshairX = (boundingRect.tl().x + boundingRect.br().x) / 2;
            crosshairY = (boundingRect.tl().y + boundingRect.br().y) / 2;
            targetVisible = true;

            Scalar crosshairCol = new Scalar(0.0, 0.0, 0.0);
            Imgproc.line(inputContours, new Point(crosshairX - 10, crosshairY), new Point(crosshairX + 10, crosshairY), crosshairCol, 5);
            Imgproc.line(inputContours, new Point(crosshairX, crosshairY - 10), new Point(crosshairX, crosshairY + 10), crosshairCol, 5);
        } else {
            targetVisible = false;
            crosshairX = -1;
            crosshairY = -1;
        }

        return inputContours;
    }
}
