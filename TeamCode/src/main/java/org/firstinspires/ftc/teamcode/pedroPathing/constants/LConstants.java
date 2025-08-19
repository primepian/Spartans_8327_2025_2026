package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = .001989436789; //ForwardTuner
        ThreeWheelConstants.strafeTicksToInches = .001989436789; //LateralTuner
        ThreeWheelConstants.turnTicksToInches = .001989436789;  //TurnTuner
        ThreeWheelConstants.leftY = 5.6496063;      //in
        ThreeWheelConstants.rightY = -5.6496063;    //in
        ThreeWheelConstants.strafeX = 0.8464567;    //in
        ThreeWheelConstants.leftEncoder_HardwareMapName = "rightFront";
        ThreeWheelConstants.rightEncoder_HardwareMapName = "leftRear";
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "leftFront";
        ThreeWheelConstants.leftEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.rightEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.FORWARD;
    }
}




