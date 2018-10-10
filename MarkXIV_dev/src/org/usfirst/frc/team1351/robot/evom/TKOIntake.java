package org.usfirst.frc.team1351.robot.evom;

import org.usfirst.frc.team1351.robot.Definitions;
import org.usfirst.frc.team1351.robot.util.TKOHardware;
import org.usfirst.frc.team1351.robot.util.TKOThread;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

/**
 * This is an example of how to make a class that runs as a thread. The most important reason for making TKOThread was to make the thread
 * implementation thread-safe everywhere, meaning that if we happened to use two threads to do the same thing to an object, we would not
 * have memory corruption / other problems.
 * 
 * @author Bharat and Sahil
 * @version 02/15/2017
 */
public class TKOIntake implements Runnable // implements Runnable is important
// to make this class support the
// Thread (run method)
{
	/*
	 * This creates an object of the TKOThread class, passing it the runnable of this class (TKOIntake) TKOThread is just a thread that
	 * makes it easy to make using the thread safe
	 */
	public TKOThread intakeThread = null;
	private static TKOIntake m_Instance = null;

	// Typical constructor made protected so that this class is only accessed
	// statically, though that doesnt matter
	protected TKOIntake()
	{
	}

	/**
	 * This function makes the class a singleton, so that there can only be one instance of the class even though the class is not static
	 * This is needed for the Thread to work properly.
	 */
	public static synchronized TKOIntake getInstance()
	{
		if (m_Instance == null)
		{
			m_Instance = new TKOIntake();
			m_Instance.intakeThread = new TKOThread(m_Instance);
		}
		return m_Instance;
	}

	/**
	 * The {@code start} method starts the thread, making it call the run method (only once) but can do this for threads in different
	 * classes in parallel. The {@code isThreadRunning} method checks with a boolean whether the thread is running. We only start the thread
	 * if it is not. The {@code setThreadRunning} method sets the boolean to true, and the {@code start} method starts the Thread. We use
	 * the {@code isThreadRunning} in the run function to verify whether our thread should be running or not, to make a safe way to stop the
	 * thread. This function is completely thread safe.
	 * 
	 * @category
	 
	 
	 * 
	 */
	public void start()
	{
		if (!intakeThread.isAlive() && m_Instance != null)
		{
			intakeThread = new TKOThread(m_Instance);
			intakeThread.setPriority(Definitions.getPriority("ball"));
		}
		if (!intakeThread.isThreadRunning())
		{
			intakeThread.setThreadRunning(true);
		}

		TKOHardware.configIntakeTalons(TalonControlMode.PercentVbus);
	}

	/**
	 * The {@code stop} method disables the thread, simply by setting the {@code isThreadRunning} to false via {@code setThreadRunning} and
	 * waits for the method to stop running (on the next iteration of run).
	 */
	public void stop()
	{
		if (intakeThread.isThreadRunning())
		{
			intakeThread.setThreadRunning(false);
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
			while (intakeThread.isThreadRunning())
			{

				if (TKOHardware.getJoystick(2).getRawButton(2))
				{
					System.out.println("THREAD RAN!");
					TKOHardware.getIntakeTalon().set(.8);//0.4);// when button is presses, the intake
					// Motor will be enabled and set
					// speed to 0.4
				}
				else
				{
					TKOHardware.getIntakeTalon().set(0); // if button is not pressed, the intake
					// motor will set speed to 0 and stops
					// running
				}

				synchronized (intakeThread) // synchronized per the thread to
											// make sure that we wait safely
				{
					intakeThread.wait(100); // the wait time that the thread
											// sleeps, in milliseconds
				}
			}
		}
		catch (

		Exception e)
		{
			e.printStackTrace();
		}
	}
}
