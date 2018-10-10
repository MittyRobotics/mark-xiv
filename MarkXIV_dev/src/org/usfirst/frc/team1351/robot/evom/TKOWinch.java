package org.usfirst.frc.team1351.robot.evom;

import org.usfirst.frc.team1351.robot.Definitions;
import org.usfirst.frc.team1351.robot.util.TKOException;
import org.usfirst.frc.team1351.robot.util.TKOHardware;
import org.usfirst.frc.team1351.robot.util.TKOThread;

public class TKOWinch implements Runnable // implements Runnable is important to make this class support the Thread (run method)
{

	public TKOThread winchThread = null;
	private static TKOWinch m_Instance = null;

	// Typical constructor made protected so that this class is only accessed statically, though that doesnt matter
	protected TKOWinch()
	{
		// TODO Auto-generated constructor stub
	}

	public static synchronized TKOWinch getInstance()
	{
		if (m_Instance == null)
		{
			m_Instance = new TKOWinch();
			m_Instance.winchThread = new TKOThread(m_Instance);
		}
		return m_Instance;
	}

	public void start()
	{
		if (!winchThread.isAlive() && m_Instance != null)
		{
			winchThread = new TKOThread(m_Instance);
			winchThread.setPriority(Definitions.getPriority("winch"));
		}
		if (!winchThread.isThreadRunning())
		{
			winchThread.setThreadRunning(true);
		}
	}

	/**
	 * The {@code stop} method disables the thread, simply by setting the {@code isThreadRunning} to false via {@code setThreadRunning} and
	 * waits for the method to stop running (on the next iteration of run).
	 */
	public void stop()
	{
		if (winchThread.isThreadRunning())
		{
			winchThread.setThreadRunning(false);
		}
	}

	void autoWinchUp()
	{
		try
		{
			if (!TKOHardware.getJoystick(1).getRawButton(2) && TKOHardware.getWinchTalon().getOutputCurrent() > Definitions.WINCH_UNLADEN_CURRENT)
			{
				TKOHardware.getWinchTalon().set(Definitions.WINCH_LADEN_FIXED_OUTPUT);
			}
			else if (!TKOHardware.getJoystick(1).getRawButton(3) && TKOHardware.getWinchTalon().getOutputCurrent() <= Definitions.WINCH_UNLADEN_CURRENT)
			{
				TKOHardware.getWinchTalon().set(Definitions.WINCH_UNLADEN_FIXED_OUTPUT);
			} 
			else
			{
				TKOHardware.getWinchTalon().enableBrakeMode(true);
				TKOHardware.getWinchTalon().set(0);
			}
		}
		catch (TKOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The run method is what the thread actually calls once. The continual running of the thread loop is done by the while loop, controlled
	 * by a safe boolean inside the TKOThread object. The wait is synchronized to make sure the thread safely sleeps.
	 */
	@Override
	public void run()
	{
		try
		{
			while (winchThread.isThreadRunning())
			{
//				System.out.printf("Current: %8.3f \t \n", TKOHardware.getWinchTalon().getOutputCurrent()); 

				if (TKOHardware.getJoystick(1).getTrigger())
				{
					autoWinchUp(); //Does not stop at top 
				}
				else if (TKOHardware.getJoystick(1).getRawButton(11))
				{
					TKOHardware.getWinchTalon().set(Definitions.WINCH_LADEN_FIXED_OUTPUT);
				}
				else if (TKOHardware.getJoystick(1).getRawButton(10))
				{
					TKOHardware.getWinchTalon().set(Definitions.WINCH_UNLADEN_FIXED_OUTPUT);
				}
				else if (TKOHardware.getJoystick(1).getRawButton(7)) { //down
					TKOHardware.getWinchTalon().set(-1.f * Definitions.WINCH_UNLADEN_FIXED_OUTPUT); 
				}
				else if(TKOHardware.getJoystick(1).getRawButton(6)) { //hold
					TKOHardware.getWinchTalon().set(0.1); 
				}
				else if (TKOHardware.getJoystick(2).getRawButton(11))
				{
					TKOHardware.getWinchTalon().set(-1.f * Definitions.WINCH_LADEN_FIXED_OUTPUT);
				}
				else if (TKOHardware.getJoystick(2).getRawButton(10))
				{
					TKOHardware.getWinchTalon().set(-1.f * Definitions.WINCH_UNLADEN_FIXED_OUTPUT); //same as joystick 1, 7
				}
				else if (TKOHardware.getJoystick(2).getRawButton(7)) { //same as joystick 1, 10
					TKOHardware.getWinchTalon().set(Definitions.WINCH_UNLADEN_FIXED_OUTPUT); 
				}
				else if(TKOHardware.getJoystick(2).getRawButton(6)) { //hold
					TKOHardware.getWinchTalon().set(-0.1); 
				}
				else
				{
					TKOHardware.getWinchTalon().set(0.f);
				}

				synchronized (winchThread) // synchronized per the thread to make sure that we wait safely
				{
					winchThread.wait(100); // the wait time that the thread sleeps, in milliseconds
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
