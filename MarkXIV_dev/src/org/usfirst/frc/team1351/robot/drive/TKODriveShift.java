package org.usfirst.frc.team1351.robot.drive;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class TKODriveShift extends SampleRobot
{

	CANTalon oneTalon, twoTalon, threeTalon, fourTalon;
	DoubleSolenoid shifter;
	Joystick stick, stick2;
	double y0, y1;
	boolean automatic = false;
	final int forwardChannel = 7;
	final int reverseChannel = 6;
	final Value upShift = Value.kForward;
	final Value downShift = Value.kReverse;
	static double deadzone = 0.05;

	@Override
	public void robotInit()
	{
		oneTalon = new CANTalon(0);
		twoTalon = new CANTalon(1);
		threeTalon = new CANTalon(2);
		fourTalon = new CANTalon(3);
		shifter = new DoubleSolenoid(forwardChannel, reverseChannel);
		stick = new Joystick(0);
		stick2 = new Joystick(1);
		oneTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		twoTalon.changeControlMode(TalonControlMode.Follower);
		twoTalon.set(oneTalon.getDeviceID());

		threeTalon.changeControlMode(TalonControlMode.PercentVbus);
		fourTalon.changeControlMode(TalonControlMode.Follower);
		fourTalon.set(threeTalon.getDeviceID());

		oneTalon.enable();
		twoTalon.enable();
		threeTalon.enable();
		fourTalon.enable();

	}

	@Override
	public void autonomous()
	{
	}

	@Override
	public void operatorControl()
	{
		while (isOperatorControl() && isEnabled())
		{
			SmartDashboard.putBoolean("AUTOMATIC", automatic);
			SmartDashboard.putNumber("Speed", oneTalon.getSpeed());

			if (stick.getY() > -deadzone && stick.getY() < deadzone)
			{
				y0 = 0;

			}
			else
			{
				y0 = stick.getY();
				// fourTalon.set(y0);
				// sets the motors to run at the percent that the left joystick
				// is pushed forward
			}
			threeTalon.set(y0);
			oneTalon.set(-y0);

			// if (stick2.getY() > -deadzone && stick2.getY() < deadzone) {
			// y1 = 0;
			// } else {
			// y1 = stick2.getY();
			// // twoTalon.set(-y1);
			// // sets the motors to run at the percent that the right joystick
			// // is pushed forward
			// }
			// oneTalon.set(-y1);

			if (stick.getRawButton(3))
			{
				if (automatic == false)
				{
					automatic = true;
				}
				else if (automatic == true)
				{
					automatic = false;
				}
			}
			if (automatic == false)
			{

				if (stick.getRawButton(1) && shifter.get() != Value.kForward)
				{
					shifter.set(Value.kForward);

				}
				if (stick.getRawButton(2) && shifter.get() != Value.kReverse)
				{
					shifter.set(Value.kReverse);
				}

			}
			else
			{
				if ((oneTalon.getSpeed() >= 2400 || oneTalon.getSpeed() <= -2400) && shifter.get() != Value.kForward)
				{
					shifter.set(Value.kForward);
				}
				if ((oneTalon.getSpeed() <= 2000 && oneTalon.getSpeed() >= -2000) && shifter.get() != Value.kReverse)
				{
					shifter.set(Value.kReverse);
				}
			}
			Timer.delay(0.005); // wait for a motor update time
			System.out.println(oneTalon.getSpeed());
		}
		System.out.println("WTFFFFF");
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test()
	{
	}
}