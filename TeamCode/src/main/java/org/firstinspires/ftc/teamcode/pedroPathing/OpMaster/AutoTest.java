package org.firstinspires.ftc.teamcode.pedroPathing.OpMaster;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
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
    private final Pose basket = new Pose(128, 15, Math.toRadians(137));
    private final Pose controlPoint1 = new Pose(128, 15, Math.toRadians(137));
    private final Pose sample1Pose = new Pose(98.2, 35.6, Math.toRadians(270));
    private final Pose sample2Pose = new Pose(98.2, 27.1, Math.toRadians(270));
    private final Pose sample3Pose = new Pose(98.2, 16.4, Math.toRadians(270));
    private final Pose ParkingPose = new Pose(84.3, 48.6, Math.toRadians(90));

    private Path Start_Basket, Basket_Sample1, Sample1_Basket, Basket_Sample2, Sample2_Basket, Basket_Sample3, Sample3_Basket, Basket_Parking;

    public void buildPaths() {
        Start_Basket = new Path(new BezierLine(new Point(startingPose),new Point(basket)));
        Start_Basket.setLinearHeadingInterpolation(startingPose.getHeading(), basket.getHeading());

        Basket_Sample1 = new Path(new BezierCurve(new Point(basket),new Point(controlPoint1),new Point(sample1Pose)));
        Basket_Sample1.setLinearHeadingInterpolation(basket.getHeading(), sample1Pose.getHeading());

        Sample1_Basket = new Path(new BezierLine(new Point(sample1Pose),new Point(basket)));
        Sample1_Basket.setLinearHeadingInterpolation(sample1Pose.getHeading(), basket.getHeading());

        Basket_Sample2 = new Path(new BezierLine(new Point(basket),new Point(sample2Pose)));
        Basket_Sample2.setLinearHeadingInterpolation(basket.getHeading(), sample2Pose.getHeading());

        Sample2_Basket = new Path(new BezierLine(new Point(sample2Pose),new Point(basket)));
        Sample2_Basket.setLinearHeadingInterpolation(sample2Pose.getHeading(), basket.getHeading());

        Basket_Sample3 = new Path(new BezierLine(new Point(basket),new Point(sample3Pose)));
        Basket_Sample3.setLinearHeadingInterpolation(basket.getHeading(), sample3Pose.getHeading());

        Sample3_Basket = new Path(new BezierLine(new Point(sample3Pose),new Point(basket)));
        Sample3_Basket.setLinearHeadingInterpolation(sample3Pose.getHeading(), basket.getHeading());

        Basket_Parking = new Path(new BezierLine(new Point(basket),new Point(ParkingPose)));
        Basket_Parking.setLinearHeadingInterpolation(basket.getHeading(), ParkingPose.getHeading());
    }

    public void autonomousPathUpdate() {
        switch (State) {
            case 0:
                follower.followPath(Start_Basket,true);
                setPathState(1);
                break;
            case 1:
                if(!follower.isBusy()) {
                    follower.followPath(Basket_Sample1,true);
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()) {
                    follower.followPath(Sample1_Basket,true);
                    setPathState(3);
                }
                break;
            case 3:
                if(!follower.isBusy()) {
                    follower.followPath(Basket_Sample2,true);
                    setPathState(4);
                }
                break;
            case 4:
                if(!follower.isBusy()) {
                    follower.followPath(Sample2_Basket,true);
                    setPathState(5);
                }
                break;
            case 5:
                if(!follower.isBusy()) {
                    follower.followPath(Basket_Sample3,true);
                    setPathState(6);
                }
                break;
            case 6:
                if(!follower.isBusy()) {
                    follower.followPath(Sample3_Basket,true);
                    setPathState(7);
                }
                break;
            case 7:
                if(!follower.isBusy()) {
                    follower.followPath(Basket_Parking,true);
                    setPathState(-1);
                }
                break;
        }
    }
    public void setPathState(int pState) {
        State = pState;
        pathTimer.resetTimer();
    }

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
        follower.setStartingPose(startingPose);
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