package org.firstinspires.ftc.teamcode.pedroPathing.Tests;

import static com.pedropathing.follower.FollowerConstants.leftFrontMotorName;
import static com.pedropathing.follower.FollowerConstants.leftRearMotorName;
import static com.pedropathing.follower.FollowerConstants.rightFrontMotorName;
import static com.pedropathing.follower.FollowerConstants.rightRearMotorName;
import static com.pedropathing.follower.FollowerConstants.secondaryTranslationalPIDFCoefficients;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Color Tracker Test", group="Tests")
public class ColorDetector extends LinearOpMode {
    private DcMotorEx leftFront;
    private DcMotorEx leftRear;
    private DcMotorEx rightFront;
    private DcMotorEx rightRear;

    OpenCvWebcam webcam;
    ColorDetectorBluePipeline pipeline;
    @Override
    public void runOpMode() throws InterruptedException {
        leftFront = hardwareMap.get(DcMotorEx.class, leftFrontMotorName);
        leftRear = hardwareMap.get(DcMotorEx.class, leftRearMotorName);
        rightRear = hardwareMap.get(DcMotorEx.class, rightRearMotorName);
        rightFront = hardwareMap.get(DcMotorEx.class, rightFrontMotorName);


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
        waitForStart();

        while (opModeIsActive()) {
            if (pipeline.targetVisible) {
                telemetry.addData("Crosshair X", pipeline.crosshairX);
                telemetry.addData("Crosshair Y", pipeline.crosshairY);
                if (pipeline.crosshairX > 170){
                    right();
                    telemetry.addLine("right");
                } else if (pipeline.crosshairX < 150) {
                    left();
                    telemetry.addLine("left");
                }
                else {
                    telemetry.addLine("Good parameter");
                }

            } else {
                telemetry.addLine("No Hay");
            }

            telemetry.update();
            sleep(20);
        }

        webcam.stopStreaming();
    }
    public void right(){
        leftFront.setPower(0.5);
        rightFront.setPower(-0.5);
        leftRear.setPower(-0.5);
        rightRear.setPower(0.5);
    }
    public void left(){
        leftFront.setPower(-0.5);
        rightFront.setPower(0.5);
        leftRear.setPower(0.5);
        rightRear.setPower(-0.5);
    }
}
