package org.usfirst.frc.team1351.robot.atoms.auton;

import org.usfirst.frc.team1351.robot.Definitions;
import org.usfirst.frc.team1351.robot.atoms.Atom;
import org.usfirst.frc.team1351.robot.logger.TKOLogger;
import org.usfirst.frc.team1351.robot.util.TKOException;
import org.usfirst.frc.team1351.robot.util.TKOHardware;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* TODO
 * tune incrementer
 * fix timer
 * set PID values in TKOHardware.java
 */

public class GyroTurnAtom extends Atom
{
	PIDController pid;
	double angle, incrementer, threshold;
	double p, i, d;

	public GyroTurnAtom(double _angle)
	{
		angle = _angle;
		threshold = 3;
		incrementer = SmartDashboard.getNumber("Turn Incrementer: ", Definitions.TURN_ATOM_INCREMENTER);
		p = SmartDashboard.getNumber("Turn P: ", Definitions.AUTON_GYRO_TURN_P);
		i = SmartDashboard.getNumber("Turn I: ", Definitions.AUTON_GYRO_TURN_I);
		d = SmartDashboard.getNumber("Turn D: ", Definitions.AUTON_GYRO_TURN_D);
	}

	public void init()
	{
		try
		{
			
			TKOHardware.autonInit(p, i, d);
//			TKOHardware.configDriveTalons(p, i, d, TalonControlMode.PercentVbus);
			TKOHardware.getRightDrive().changeControlMode(TalonControlMode.PercentVbus);
			TKOHardware.getLeftDrive().changeControlMode(TalonControlMode.PercentVbus);
			pid = new PIDController(p, i, d, TKOHardware.getGyro(), TKOHardware.getLeftDrive());
			

		} catch (TKOException e)
		{
			e.printStackTrace();
		}
		pid.reset();
		pid.setOutputRange(-1, 1);
		pid.setContinuous();
		pid.setAbsoluteTolerance(1);
		
		System.out.println("Gyro turn atom initialized");
	}

	@Override
	public void execute()
	{
		System.out.println("Executing gyro turn atom");
		try
		{
			TKOHardware.getGyro().reset();
		}
		catch (TKOException e)
		{
			
			e.printStackTrace();
		}
		try
		{
			pid.enable();
			if (angle >= 0)
			{
				while (DriverStation.getInstance().isEnabled() && pid.getSetpoint() < angle)
				{
					pid.setSetpoint(pid.getSetpoint() + incrementer);
					TKOHardware.getRightDrive().set(TKOHardware.getLeftDrive().get());
					System.out.println("Left Position: " + TKOHardware.getLeftDrive().get()
						+ "\t Right Position: " + TKOHardware.getRightDrive().get()
						+ "\t PID Setpoint: " + pid.getSetpoint());
					TKOLogger.getInstance().addMessage("Left Position: " + TKOHardware.getLeftDrive().get()
						+ "\t Right Position: " + TKOHardware.getRightDrive().get()
						+ "\t PID Setpoint: " + pid.getSetpoint());
					Timer.delay(0.001);
				}
			}
			else
			{
				while (DriverStation.getInstance().isEnabled() && pid.getSetpoint() > angle)
				{
					pid.setSetpoint(pid.getSetpoint() - incrementer);
					TKOHardware.getRightDrive().set(TKOHardware.getLeftDrive().get());
					System.out.println("Left Position: " + TKOHardware.getLeftDrive().get()
						+ "\t Right Position: " + TKOHardware.getRightDrive().get()
						+ "\t PID Setpoint: " + pid.getSetpoint());
					TKOLogger.getInstance().addMessage("Left Position: " + TKOHardware.getLeftDrive().get()
						+ "\t Right Position: " + TKOHardware.getRightDrive().get()
						+ "\t PID Setpoint: " + pid.getSetpoint());
					System.out.println("Current Angle: " + TKOHardware.getGyro().getAngle());
					Timer.delay(0.001);
				}
			}
			
			pid.setSetpoint(angle);
			
			Timer t = new Timer();
			
			t.reset();
			t.start();
			while (DriverStation.getInstance().isEnabled())
			{
				if (Math.abs(pid.getError()) > threshold)
				{
					t.reset();
					pid.setSetpoint(angle);
				}
				if (t.get() > .25)
				{
					System.out.println("Timeout in Gyro turn atom");
					break;
				}
				TKOHardware.getRightDrive().changeControlMode(TalonControlMode.Follower);
				TKOHardware.getRightDrive().set(TKOHardware.getLeftDrive().getDeviceID());
				TKOHardware.getRightDrive().reverseOutput(false);
				System.out.println("Target Angle: " + pid.getSetpoint()
					+ "\t PID Error: " + pid.getError()
					+ "\t Current angle: " + TKOHardware.getGyro().getAngle());
				TKOLogger.getInstance().addMessage("Target Angle: " + pid.getSetpoint()
					+ "\t PID Error: " + pid.getError()
					+ "\t Current angle: " + TKOHardware.getGyro().getAngle());
				Timer.delay(0.001);
			}
			t.stop();
			TKOHardware.getLeftDrive().set(0);
			TKOHardware.getRightDrive().set(0);
			Timer.delay(0.1);
		} catch (TKOException e1)
		{
			e1.printStackTrace();
		}
		pid.disable();
		System.out.println("Done executing gyro turn atom");
	}
}
