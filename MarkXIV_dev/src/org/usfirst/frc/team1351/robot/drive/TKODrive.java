package org.usfirst.frc.team1351.robot.drive;

import org.usfirst.frc.team1351.robot.Definitions;
import org.usfirst.frc.team1351.robot.util.TKOException;
import org.usfirst.frc.team1351.robot.util.TKOHardware;
import org.usfirst.frc.team1351.robot.util.TKORuntimeException;
import org.usfirst.frc.team1351.robot.util.TKOThread;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class TKODrive implements Runnable
{
	private static TKODrive m_Instance = null;
	public TKOThread driveThread = null;
	private boolean brakeToggle = false;

	public static synchronized TKODrive getInstance()
	{
		if (TKODrive.m_Instance == null)
		{
			m_Instance = new TKODrive();
			m_Instance.driveThread = new TKOThread(m_Instance);
		}
		return m_Instance;
	}

	protected TKODrive()
	{
		SmartDashboard.putBoolean("Brake mode enabled: ", brakeToggle);
	}

	public void start()
	{
		System.out.println("Starting drive task");
		if (!driveThread.isAlive() && m_Instance != null)
		{
			driveThread = new TKOThread(m_Instance);
			driveThread.setPriority(Definitions.getPriority("drive"));
		}
		if (!driveThread.isThreadRunning())
			driveThread.setThreadRunning(true);

		System.out.println("Started drive task");

		init();
	}

	public synchronized void init()
	{
		try
		{
			TKOHardware.changeTalonMode(TKOHardware.getLeftDrive(), CANTalon.TalonControlMode.PercentVbus, Definitions.DRIVE_P,
					Definitions.DRIVE_I, Definitions.DRIVE_D);
			TKOHardware.changeTalonMode(TKOHardware.getRightDrive(), CANTalon.TalonControlMode.PercentVbus, Definitions.DRIVE_P,
					Definitions.DRIVE_I, Definitions.DRIVE_D);
		}
		catch (TKOException e)
		{
			e.printStackTrace();
		}
	}

	public void stop()
	{
		System.out.println("Stopping drive task");
		if (driveThread.isThreadRunning())
			driveThread.setThreadRunning(false);
		System.out.println("Stopped drive task");
	}

	double leftMove = 0.f;
	double rightMove = 0.f;
	double leftSign = 0.f;
	double rightSign = 0.f;

	public void squaredXbox()
	{
		try
		{
			// Get the squared inputs from xBox controller - actually x^4
			leftMove = Math.pow(TKOHardware.getXboxController().getY(Hand.kLeft), 2);
			rightMove = Math.pow(TKOHardware.getXboxController().getY(Hand.kRight), 2);

			// Just gets the sign - positive or negative
			leftSign = Math.abs(TKOHardware.getXboxController().getY(Hand.kLeft)) / TKOHardware.getXboxController().getY(Hand.kLeft);
			rightSign = Math.abs(TKOHardware.getXboxController().getY(Hand.kRight)) / TKOHardware.getXboxController().getY(Hand.kRight);

			if (reverse)
			{
				leftSign *= -1;
				rightSign *= -1;
			}
			setLeftRightMotorOutputsPercentVBus(leftMove * leftSign, rightMove * rightSign);

		}
		catch (TKOException e)
		{
			e.printStackTrace();
		}
	}

	public synchronized void setLeftRightMotorOutputsPercentVBus(double left, double right)
	{
		try
		{
			if (TKOHardware.getLeftDrive().getControlMode() == CANTalon.TalonControlMode.PercentVbus)
				TKOHardware.getLeftDrive().set(Definitions.DRIVE_MULTIPLIER_LEFT * left);
			else
				throw new TKORuntimeException("ERROR: Tried running tank drive when not PercentVbus");
			if (TKOHardware.getRightDrive().getControlMode() == CANTalon.TalonControlMode.PercentVbus)
				TKOHardware.getRightDrive().set(Definitions.DRIVE_MULTIPLIER_RIGHT * right);
			else
				throw new TKORuntimeException("ERROR: Tried running tank drive when not PercentVbus");
		}
		catch (TKOException e)
		{
			e.printStackTrace();
		}
	}

	private boolean creep = false;

	public void isCreep(boolean b)
	{
		creep = b;
	}

	private boolean reverse = false;

	@Override
	public void run()
	{
		try
		{
			while (driveThread.isThreadRunning())
			{
				if (!creep)
					squaredXbox();
				// for testing
//			 System.out.printf("0: %10.2f \t1: %10.2f \t2: %10.2f \t3 %10.2f \t LSp: %f \t RSp: %f \n", TKOHardware.getDriveTalon(0).getOutputCurrent(), TKOHardware.getDriveTalon(1).getOutputCurrent(), TKOHardware.getDriveTalon(2).getOutputCurrent(), TKOHardware.getDriveTalon(3).getOutputCurrent(), TKOHardware.getLeftDrive().getSpeed(), TKOHardware.getRightDrive().getSpeed()); 		
//			 Timer.delay(0.5);
				if (TKOHardware.getLeftDrive().getOutputCurrent() > Definitions.CURRENT_SAFETY_THRESHOLD
						|| TKOHardware.getRightDrive().getOutputCurrent() > Definitions.CURRENT_SAFETY_THRESHOLD)
				{
					TKOHardware.getXboxController().setRumble(RumbleType.kLeftRumble, 1.f);
					TKOHardware.getXboxController().setRumble(RumbleType.kRightRumble, 1.f);
				}
				else
				{
					TKOHardware.getXboxController().setRumble(RumbleType.kLeftRumble, 0.f);
					TKOHardware.getXboxController().setRumble(RumbleType.kRightRumble, 0.f);
				}

				// if (TKOHardware.getXboxController().getStartButton())
				// reverse = !reverse; //GOTO Hell

				if (TKOHardware.getXboxController().getXButton())
				{
					brakeToggle = !brakeToggle;
					if (brakeToggle)
					{
						TKOHardware.getLeftDrive().enableBrakeMode(true);
						TKOHardware.getRightDrive().enableBrakeMode(true);
					}
					else if (!brakeToggle)
					{
						TKOHardware.getLeftDrive().enableBrakeMode(false);
						TKOHardware.getRightDrive().enableBrakeMode(false);
					}
				}
				
				if (TKOHardware.getXboxController().getAButton()) {
					TKOHardware.getLeftDrive().set(-0.3);
					TKOHardware.getRightDrive().set(0.3);
					Timer.delay(0.5);
					TKOHardware.getLeftDrive().set(0);
					TKOHardware.getRightDrive().set(0);
					
				}
				// SmartDashboard.putNumber("Right Drive Current", TKOHardware.getRightDrive().getOutputCurrent());
				// SmartDashboard.putNumber("Right Drive Voltage", TKOHardware.getRightDrive().getOutputVoltage());
				// SmartDashboard.putNumber("Left Drive Current", TKOHardware.getLeftDrive().getOutputCurrent());
				// SmartDashboard.putNumber("Left Drive Voltage", TKOHardware.getLeftDrive().getOutputVoltage());

				synchronized (driveThread)
				{
					driveThread.wait(5);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
