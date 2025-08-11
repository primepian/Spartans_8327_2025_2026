package org.firstinspires.ftc.teamcode.pedroPathing.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;
import com.acmerobotics.dashboard.FtcDashboard;

@TeleOp(name="Color Tracker Test", group="Tests")
public class ColorDetector extends LinearOpMode {

    OpenCvWebcam webcam;
    ColorDetectorBluePipeline pipeline;
    @Override
    public void runOpMode() throws InterruptedException {

        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        // init cam
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        FtcDashboard dashboard = FtcDashboard.getInstance();
        dashboard.startCameraStream(webcam, 30);

        // init pipeline
        pipeline = new ColorDetectorBluePipeline();
        webcam.setPipeline(pipeline);


        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);  //(OpenCvCameraRotation.UPRIGHT) cambia orientacion de la camara

            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera error", errorCode);
                telemetry.update();
            }
        });

        telemetry.addLine("Waiting for start");
        telemetry.update();
//        waitForStart();

        while (opModeIsActive() || opModeInInit()) {
            if (pipeline.targetVisible) {
                telemetry.addData("Crosshair X", pipeline.crosshairX);
                telemetry.addData("Crosshair Y", pipeline.crosshairY);
            } else {
                telemetry.addLine("No Hay");
            }

            telemetry.update();
            sleep(100);
        }

        webcam.stopStreaming();
    }
}
