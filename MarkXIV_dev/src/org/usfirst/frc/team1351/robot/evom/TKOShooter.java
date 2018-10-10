package org.usfirst.frc.team1351.robot.evom;

import org.usfirst.frc.team1351.robot.Definitions;
import org.usfirst.frc.team1351.robot.util.TKOThread;
import edu.wpi.first.wpilibj.XboxController;

import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team1351.robot.util.TKOException;
import org.usfirst.frc.team1351.robot.util.TKOHardware;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * @author Tiina
 * @version 02/13/17
 */

public class TKOShooter implements Runnable // implements Runnable is important to make this class support the Thread (run method)

{

	public TKOThread shooterThread = null;
	private static TKOShooter m_Instance = null;
	private double p, i, d, f, inputSpeed;

	// PIDController testpid;

	// Typical constructor made protected so that this class is only accessed statically, though that doesnt matter

	protected TKOShooter()

	{

		p = Definitions.SHOOTER_P;
		i = Definitions.SHOOTER_I;
		d = Definitions.SHOOTER_D;

		// try
		// {
		// testpid = new PIDController(p, i, d, TKOHardware.getShooterTalon(), TKOHardware.getShooterTalon());
		// }
		// catch (TKOException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	/**
	 * 
	 * This function makes the class a singleton, so that there can only be one instance of the class even though the class is not static
	 * 
	 * This is needed for the Thread to work properly.
	 * 
	 */

	public static synchronized TKOShooter getInstance()

	{

		if (m_Instance == null)
		{

			m_Instance = new TKOShooter();
			m_Instance.shooterThread = new TKOThread(m_Instance);

		}
		return m_Instance;

	}

	/**
	 * 
	 * The {@code start} method starts the thread, making it call the run method (only once) but can do this for threads in different
	 * 
	 * classes in parallel. The {@code isThreadRunning} method checks with a boolean whether the thread is running. We only start the thread
	 * 
	 * if it is not. The {@code setThreadRunning} method sets the boolean to true, and the {@code start} method starts the Thread. We use
	 * 
	 * the {@code isThreadRunning} in the run function to verify whether our thread should be running or not, to make a safe way to stop the
	 * 
	 * thread. This function is completely thread safe.
	 * 
	 * 
	 * 
	 * @category
	 * 
	 * 
	 * 
	 */

	public void start()

	{

		if (!shooterThread.isAlive() && m_Instance != null)

		{

			shooterThread = new TKOThread(m_Instance);
			shooterThread.setPriority(Definitions.getPriority("ball"));

		}

		if (!shooterThread.isThreadRunning())

		{

			shooterThread.setThreadRunning(true);

		}

		p = SmartDashboard.getNumber("Shooter P: ", Definitions.SHOOTER_P);
		i = SmartDashboard.getNumber("Shooter I: ", Definitions.SHOOTER_I);
		d = SmartDashboard.getNumber("Shooter D: ", Definitions.SHOOTER_D);

		TKOHardware.configShooterTalons(p, i, d, Definitions.SHOOTER_TALONS_NORMAL_CONTROL_MODE);

		// TKOHardware.getShooterTalon().disable();

	}

	/**
	 * 
	 * The {@code stop} method disables the thread, simply by setting the {@code isThreadRunning} to false via {@code setThreadRunning} and
	 * 
	 * waits for the method to stop running (on the next iteration of run).
	 * 
	 */

	public void stop()

	{

		if (shooterThread.isThreadRunning())

		{

			shooterThread.setThreadRunning(false);

		}

	}

	/**
	 * 
	 * The run method is what the thread actually calls once. The continual running of the thread loop is done by the while loop, controlled
	 * 
	 * by a safe boolean inside the TKOThread object. The wait is synchronized to make sure the thread safely sleeps.
	 * 
	 */

	@Override

	public void run()

	{

		try

		{

			TKOHardware.configShooterTalons(p, i, d, TalonControlMode.Speed);
//			TKOHardware.getShooterTalon().setF(Definitions.SHOOTER_F);
			TKOHardware.getShooterTalon().clearIAccum();
			TKOHardware.getShooterTalon().enable();

			while (shooterThread.isThreadRunning())
			{
				//TODO: make neg for reverse

				if (TKOHardware.getJoystick(2).getRawButton(4))
				{
					TKOHardware.getBallFeederTalon().set(0.75);
				}
				else if (TKOHardware.getJoystick(2).getRawButton(5))
				{
					TKOHardware.getBallFeederTalon().set(-0.75);
				}
				else
				{
					TKOHardware.getBallFeederTalon().set(0.0);
				}

				// lowestSpeed = TKOHardware.getShooterTalon().getSpeed() < lowestSpeed ? TKOHardware.getShooterTalon().getSpeed() :
				// lowestSpeed;
				// SmartDashboard.putNumber("Lowest Speed", lowestSpeed);
				if (TKOHardware.getJoystick(2).getTrigger())
				{
					TKOHardware.getShooterTalon().enable();
					TKOHardware.getShooterTalon().set(SmartDashboard.getNumber("Shooter - Input Speed: ", 0));
				}
				else
				{
					TKOHardware.getShooterTalon().disable();
					TKOHardware.getShooterTalon().set(0);
				}
				 System.out.println("Speeds: " + TKOHardware.getShooterTalon().getSpeed() + "\tVoltage: " +
				 TKOHardware.getShooterTalon().getOutputVoltage() + "\tCurrent: " + TKOHardware.getShooterTalon().getOutputCurrent());
//				System.out.printf("Speed: %10.2f\tVoltage: %10.2f\tCurrent: %10.2f\tError: %10.2f\tSetpoint: %10.2f\n",
//						TKOHardware.getShooterTalon().getSpeed(), TKOHardware.getShooterTalon().getOutputVoltage(),
//						TKOHardware.getShooterTalon().getOutputCurrent(), TKOHardware.getShooterTalon().getError() * 0.1464862417626641,
//						TKOHardware.getShooterTalon().getSetpoint());
					System.out.printf("Speed: %10.2f\tError: %10.2f\tSetpoint: %10.2f\n",
							TKOHardware.getShooterTalon().getSpeed(), TKOHardware.getShooterTalon().getError() * 0.1464862417626641,
							TKOHardware.getShooterTalon().getSetpoint());
				synchronized (shooterThread) // synchronized per the thread to make sure that we wait safely

				{

					shooterThread.wait(100); // the wait time that the thread sleeps, in milliseconds

				}
			}
			TKOHardware.getShooterTalon().set(0);

		}
		catch (Exception e)

		{

			e.printStackTrace();

		}

	}

}