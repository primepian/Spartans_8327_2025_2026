package org.firstinspires.ftc.teamcode.pedroPathing.OpMaster;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@Autonomous
public class AutoTest extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int State;

    private final Pose startingPose = new Pose(136.000, 30.359, Math.toRadians(180));
    private final Pose scorePose = new Pose(128, 15, Math.toRadians(137));
    private final Pose controlPoint1 = new Pose(128, 15, Math.toRadians(137));
    private final Pose sample1Pose = new Pose(98.2, 35.6, Math.toRadians(270));
    private final Pose sample2Pose = new Pose(98.2, 27.1, Math.toRadians(270));
    private final Pose sample3Pose = new Pose(98.2, 16.4, Math.toRadians(270));
    private final Pose ParkingPose = new Pose(84.3, 48.6, Math.toRadians(90));

    private Path Start_Basket, Basket_Sample1, Sample1_Basket, Basket_Sample2, Sample2_Basket, Basket_Sample3, Sample3_Basket, Basket_Parking;

    public void buildPaths() {
        Start_Basket = new Path(new BezierLine(new Point(startingPose),new Point(scorePose)));
        Start_Basket.setLinearHeadingInterpolation(startingPose.getHeading(), scorePose.getHeading());

        Basket_Sample1 = new Path(new BezierCurve(new Point(scorePose),new Point(controlPoint1),new Point(sample1Pose)));
        Basket_Sample1.setLinearHeadingInterpolation(startingPose.getHeading(), scorePose.getHeading());

        Sample1_Basket = new Path(new BezierLine(new Point(sample1Pose),new Point(scorePose)));
        Sample1_Basket.setLinearHeadingInterpolation(startingPose.getHeading(), scorePose.getHeading());

        Basket_Sample2 = new Path(new BezierLine(new Point(scorePose),new Point(sample2Pose)));
        Basket_Sample2.setLinearHeadingInterpolation(startingPose.getHeading(), scorePose.getHeading());

        Start_Basket = new Path(new BezierLine(new Point(startingPose),new Point(scorePose)));
        Start_Basket.setLinearHeadingInterpolation(startingPose.getHeading(), scorePose.getHeading());

        
    }

    public void autonomousPathUpdate() {
        switch (State) {

        }
    }

    public void setPathState(int pState) {
        State = pState;
        pathTimer.resetTimer();
    }

    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    @Override
    public void loop() {

        // These loop the movements of the robot
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.addData("path state", State);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();
    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {
    }
}