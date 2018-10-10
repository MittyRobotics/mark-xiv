
package org.usfirst.frc.team1351.robot;

import edu.wpi.first.wpilibj.SampleRobot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends SampleRobot
{

	CANTalon zeroTalon, oneTalon, twoTalon, threeTalon;
	XboxController xbox;
	DoubleSolenoid shifter;

	public static final int ZERO_TALON_ID = 8;
	public static final int ONE_TALON_ID = 2;
	public static final int TWO_TALON_ID = 5;
	public static final int THREE_TALON_ID = 4;
	public static final TalonControlMode MODE = TalonControlMode.PercentVbus;

	public static final int XBOX_ID = 0;

	public static final int FORWARD_CHANNEL = 0;
	public static final int REVERSE_CHANNEL = 1;
	public static final Value UP_SHIFT = Value.kForward;
	public static final Value DOWN_SHIFT = Value.kReverse;
	public static final Value NEUTRAL_SHIFT = Value.kOff;

	public static final double UP_SHIFT_CURRENT = 20.f;
	public static final double DOWN_SHIFT_CURRENT = 60.f;
	public static int SHIFTER_STATE = 0; // 0 = Neutral, 1 = High, -1 = Low

	// TODO: Calculate f value!
	public static final double f = 0.0928;
	public static double p = 0.41;
	public static String P_INPUT = "P Value";
	public static double i = .000001;
	public static String I_INPUT = "I Value";
	public static double d = .2;
	public static String D_INPUT = "D Value";

	public int readout = 0;

	public Robot()
	{
	}

	public void robotInit()
	{
		zeroTalon = new CANTalon(ZERO_TALON_ID);
		oneTalon = new CANTalon(ONE_TALON_ID);
		twoTalon = new CANTalon(TWO_TALON_ID);
		threeTalon = new CANTalon(THREE_TALON_ID);

		teleopInit();

		xbox = new XboxController(XBOX_ID);
		zeroTalon.reverseSensor(false);

		SmartDashboard.putNumber(P_INPUT, p);

		// shifter = new DoubleSolenoid(FORWARD_CHANNEL, REVERSE_CHANNEL);
		// shifter.set(NEUTRAL_SHIFT);
		// SHIFTER_STATE = 0;
	}

	public void teleopInit()
	{
		zeroTalon.changeControlMode(MODE);
		oneTalon.changeControlMode(TalonControlMode.Follower);
		oneTalon.set(zeroTalon.getDeviceID());
		twoTalon.changeControlMode(MODE);
		threeTalon.changeControlMode(TalonControlMode.Follower);
		threeTalon.set(twoTalon.getDeviceID());

		zeroTalon.enableBrakeMode(false);
		oneTalon.enableBrakeMode(false);
		twoTalon.enableBrakeMode(false);
		threeTalon.enableBrakeMode(false);
	}

	public void autonInit()
	{
		zeroTalon.changeControlMode(TalonControlMode.Speed);
		twoTalon.changeControlMode(TalonControlMode.Speed);
		oneTalon.set(ZERO_TALON_ID);
		threeTalon.set(TWO_TALON_ID);

		zeroTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		twoTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);

		p = SmartDashboard.getNumber(P_INPUT, p);
		i = SmartDashboard.getNumber(I_INPUT, i);
		d = SmartDashboard.getNumber(D_INPUT, d);

		zeroTalon.setPID(p, i, d, f, 0, 0.8, 1);
		twoTalon.setPID(p, i, d, f, 0, 0.8, 1);

	}

	public void autonomous()
	{

	}

	public void operatorControl()
	{
		teleopInit();
		while (isEnabled() && isOperatorControl())
		{
			// if(SHIFTER_STATE > 0 && zeroTalon.get)
			double left = xbox.getRawAxis(1);
			double right = xbox.getRawAxis(5);
			if (Math.abs(left) < 0.15)
				left = 0.0;
			if (Math.abs(right) < 0.1)
				right = 0.0;
			System.out.println("left " + left + " right " + right);
			zeroTalon.set(-1 * left);
			twoTalon.set(right);
			System.out.println("Left out " + zeroTalon.getOutputVoltage() + " right out " + twoTalon.getOutputVoltage() + " \n Left speed "
					+ zeroTalon.getSpeed());
			Timer.delay(0.1);
		}
	}

	public void test()
	{
		autonInit();
		while (isEnabled())
		{

			double left = xbox.getRawAxis(1);
			double right = xbox.getRawAxis(5);
			if (Math.abs(left) < 0.15)
				left = 0.0;
			if (Math.abs(right) < 0.15)
				right = 0.0;
			System.out.println("left " + left + " right " + right);
			zeroTalon.set(-1.f * left * 1500.f);
			twoTalon.set(right * 1500.f);
			if ((readout % 3) == 0)
			{
				System.out.println("Left speed " + zeroTalon.getSpeed() + " Right speed " + twoTalon.getSpeed() + " \n Left error"
						+ zeroTalon.getError() + " Right error" + twoTalon.getSpeed());
			}
			readout++;
			Timer.delay(0.1);
		}
	}
}
