package org.firstinspires.ftc.teamcode.pedroPathing.OpMaster;

import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@TeleOp(name = "TeleOpMaster", group = "OpMaster")
@Disabled
public class TeleOpMaster extends OpMode {
    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
    }

    @Override
    public void loop() {

    }
}
