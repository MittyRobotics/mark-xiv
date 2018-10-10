// Last edited by Ben Kim
// on 01/21/2016

package org.usfirst.frc.team1351.robot.evom;

import org.usfirst.frc.team1351.robot.Definitions;
import org.usfirst.frc.team1351.robot.atoms.auton.DriveAtom;
import org.usfirst.frc.team1351.robot.util.TKOException;
import org.usfirst.frc.team1351.robot.util.TKOHardware;
import org.usfirst.frc.team1351.robot.util.TKOThread;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class TKOPneumatics implements Runnable
{
	public TKOThread pneuThread = null;
	private static TKOPneumatics m_Instance = null;
	long lastShiftTime = System.currentTimeMillis();
	long toggledPistonTime[] = new long[Definitions.NUM_DSOLENOIDS]; // + Definitions.NUM_SOLENOIDS];
	private boolean testEnabled = true;

	protected TKOPneumatics()
	{
		try
		{
			for (int i = 0; i < toggledPistonTime.length; i++)
			{
				toggledPistonTime[i] = 0;
			}
			TKOHardware.getCompressor().start();
			// TODO check that this is kReverse in all branches
			reset();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static synchronized TKOPneumatics getInstance()
	{
		if (TKOPneumatics.m_Instance == null)
		{
			m_Instance = new TKOPneumatics();
			m_Instance.pneuThread = new TKOThread(m_Instance);
		}
		return m_Instance;
	}

	public synchronized void start()
	{
		System.out.println("Starting pneumatics task");
		if (!pneuThread.isAlive() && m_Instance != null)
		{
			pneuThread = new TKOThread(m_Instance);
			pneuThread.setPriority(Definitions.getPriority("pneumatics"));
		}
		if (!pneuThread.isThreadRunning())
			pneuThread.setThreadRunning(true);

		try
		{
			TKOHardware.getCompressor().start();
			// TKOHardware.getCompressor().stop();
		}
		catch (TKOException e)
		{
			e.printStackTrace();
		}

		System.out.println("Started pneumatics task");
	}

	public synchronized void stop()
	{
		System.out.println("Stopping pneumatics task");
		if (pneuThread.isThreadRunning())
			pneuThread.setThreadRunning(false);
		try
		{
			TKOHardware.getCompressor().stop();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Stopped pneumatics task");
	}

	public synchronized void reset()
	{
		// try
		// {
		// TKOHardware.getDSolenoid(0).set(Definitions.SHIFTER_HIGH);
		// }
		// catch (TKOException e)
		// {
		// e.printStackTrace();
		// }
	}

	public void autoShift()
	{
		try
		{
			double currentThreshLeft = 30;
			double currentThreshRight = 30;
			short shiftDelay = 500;

			if (System.currentTimeMillis() - lastShiftTime < shiftDelay)
				return;

			if (TKOHardware.getLeftDrive().getOutputCurrent() > currentThreshLeft
					|| TKOHardware.getRightDrive().getOutputCurrent() > currentThreshRight)
				TKOHardware.getDSolenoid(0).set(Definitions.SHIFTER_LOW);
			else
				TKOHardware.getDSolenoid(0).set(Definitions.SHIFTER_LOW); 
			lastShiftTime = System.currentTimeMillis();
		} 
		catch (TKOException e)
		{
			e.printStackTrace();
		}
	}

	public synchronized void setManual(boolean b)
	{
		testEnabled = b;
	}

	public synchronized void pistonControl()
	{
		try
		{
				if (TKOHardware.getJoystick(1).getRawButton(2))
				{
					System.out.println("Manually moving arm solenoid up at time: " + System.currentTimeMillis());
//					if (System.currentTimeMillis() - toggledPistonTime[1] > 250)
//					{
//						Value currVal = TKOHardware.getDSolenoid(1).get();
//						Value newVal = currVal;
//						if (currVal == Value.kForward)
//							newVal = Value.kReverse;
//						else if (currVal == Value.kReverse)
//							newVal = Value.kForward;
//						TKOHardware.getDSolenoid(1).set(newVal);
//						toggledPistonTime[1] = System.currentTimeMillis();
//					}
					TKOHardware.getDSolenoid(1).set(Value.kForward);
					System.out.println("Arm solenoid is now: "  + TKOHardware.getDSolenoid(1).get());
				}
				if (TKOHardware.getJoystick(1).getRawButton(3))
				{
					System.out.println("Manually moving arm solenoid down at time: " + System.currentTimeMillis());
					TKOHardware.getDSolenoid(1).set(Value.kReverse);
					System.out.println("Arm solenoid is now: "  + TKOHardware.getDSolenoid(1).get());
				}
				

				if (TKOHardware.getJoystick(1).getRawButton(5))
				{
					System.out.println("Manually moving intake solenoid up at time: " + System.currentTimeMillis());
//					if (System.currentTimeMillis() - toggledPistonTime[2] > 250)
//					{
//						Value currVal = TKOHardware.getDSolenoid(2).get();
//						Value newVal = currVal;
//						if (currVal == Value.kForward)
//							newVal = Value.kReverse;
//						else if (currVal == Value.kReverse)
//							newVal = Value.kForward;
//						TKOHardware.getDSolenoid(2).set(newVal);
//						toggledPistonTime[2] = System.currentTimeMillis();
//					}
					TKOHardware.getDSolenoid(2).set(Value.kForward);
					System.out.println("Intake solenoid is now: "  + TKOHardware.getDSolenoid(2).get());
				}
				if (TKOHardware.getJoystick(1).getRawButton(4))
				{
					System.out.println("Manually moving intake solenoid down at time: " + System.currentTimeMillis());
					TKOHardware.getDSolenoid(2).set(Value.kReverse);
					System.out.println("Intake solenoid is now: "  + TKOHardware.getDSolenoid(2).get());
				}
			

			// shifting gearbox
			if (TKOHardware.getXboxController().getYButton())
			{
				TKOHardware.getDSolenoid(0).set(Definitions.SHIFTER_HIGH);
				lastShiftTime = System.currentTimeMillis();
			}
			else if (TKOHardware.getXboxController().getTriggerAxis(Hand.kRight) > .6)
			{
				TKOHardware.getDSolenoid(0).set(Definitions.SHIFTER_LOW);
				lastShiftTime = System.currentTimeMillis();
			}
			else//TODO: Test for abnormal turning
				TKOHardware.getDSolenoid(0).set(Definitions.SHIFTER_HIGH);
//				if (Math.abs(TKOHardware.getDriveTalon(0).getSpeed()) >= Definitions.SPEED_IN_TICKS_UPSHIFT && TKOHardware.getDSolenoid(0).get() != Value.kForward) {
//					TKOHardware.getDSolenoid(0).set(Value.kForward);
//				}
//				if (Math.abs(TKOHardware.getDriveTalon(0).getSpeed()) <= Definitions.SPEED_IN_TICK_DOWNSHIFT && TKOHardware.getDriveTalon(0).getSpeed() >=-2000) && TKOHardware.getDSolenoid(0).get() != Value.kReverse) {
//					TKOHardware.getDSolenoid(0).set(Value.kReverse);
//				}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		try
		{
			while (pneuThread.isThreadRunning())
			{
				pistonControl();

				// if (TKOHardware.getXboxController().getButtonB())
				// TKOArm.getInstance().breachPortcullis();
				//
				// if (TKOHardware.getXboxController().getButtonY())
				// TKOArm.getInstance().breachCheval();

				synchronized (pneuThread)
				{
					pneuThread.wait(20);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}