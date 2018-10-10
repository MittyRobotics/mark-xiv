package org.usfirst.frc.team1351.robot.util;

import org.usfirst.frc.team1351.robot.Definitions;
import org.usfirst.frc.team1351.robot.logger.TKOLogger;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogOutput;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.can.CANMessageNotFoundException;
import edu.wpi.first.wpilibj.util.AllocationException;

public class TKOHardware
{
	/**
	 * The idea behind TKOHardware is to have one common class with all the objects we would need. The code is highly modular, as seen below
	 * where all the arrays are of variable size.
	 */
	protected static XboxController xbox;
	protected static Joystick joysticks[] = new Joystick[Definitions.NUM_JOYSTICKS];
	protected static CANTalon driveTalons[] = new CANTalon[Definitions.NUM_DRIVE_TALONS];
	protected static CANTalon winchTalons[] = new CANTalon[Definitions.NUM_WINCH_TALONS];
	protected static CANTalon shooterTalons[] = new CANTalon[Definitions.NUM_SHOOTER_TALONS];
	protected static CANTalon intakeTalons[] = new CANTalon[Definitions.NUM_INTAKE_TALONS];
	protected static CANTalon ballFeederTalons[] = new CANTalon[Definitions.NUM_BALL_FEEDER_TALONS];
	protected static DoubleSolenoid doubleSolenoids[] = new DoubleSolenoid[Definitions.NUM_DSOLENOIDS];
	protected static DigitalInput digitalInputs[] = new DigitalInput[Definitions.NUM_DINPUTS];
	protected static Compressor compressor;
	// TODO: Figure out what gyro we have wtf?
	protected static ADXRS450_Gyro gyro;
	protected static AnalogOutput arduinoSignal = null;

	public TKOHardware()
	{
		xbox = null;
		for (int i = 1; i < Definitions.NUM_JOYSTICKS; i++)
		{
			joysticks[i] = null;
		}

		// After a follower talon is created, it should not be accessed (exception thrown)
		for (int i = 0; i < Definitions.NUM_DRIVE_TALONS; i++)
		{
			driveTalons[i] = null;
		}

		for (int i = 0; i < Definitions.NUM_WINCH_TALONS; i++)
		{
			winchTalons[i] = null;
		}

		for (int i = 0; i < Definitions.NUM_SHOOTER_TALONS; i++)
		{
			shooterTalons[i] = null;
		}

		for (int i = 0; i < Definitions.NUM_INTAKE_TALONS; i++)
		{
			intakeTalons[i] = null;
		}

		for (int i = 0; i < Definitions.NUM_BALL_FEEDER_TALONS; i++)
		{
			ballFeederTalons[i] = null;
		}

		for (int i = 0; i < Definitions.NUM_DSOLENOIDS; i++)
		{
			doubleSolenoids[i] = null;
		}
		for (int i = 0; i < Definitions.NUM_DINPUTS; i++)
		{
			digitalInputs[i] = null;
		}
		compressor = null;
		arduinoSignal = null;
		gyro = null;
	}

	// The following dead block should be revitalized selectively for testing before being killed.
	/*
	 * public static synchronized void initTesting() { System.out.println("Initializing objects (testing)");
	 * 
	 * // TODO maybe destroy objects before initializing them? if (xbox == null) xbox = new XboxController(Definitions.JOYSTICK_ID[0]);
	 * 
	 * if (joysticks[1] == null) joysticks[1] = new Joystick(1);
	 * 
	 * for (int i = 0; i < Definitions.NUM_DRIVE_TALONS; i++) { if (driveTalons[i] == null) { try { driveTalons[i] = new
	 * CANTalon(Definitions.DRIVE_TALON_ID[i]); } catch (AllocationException | CANMessageNotFoundException e) { e.printStackTrace();
	 * System.out.println("MOTOR CONTROLLER " + i + " NOT FOUND OR IN USE"); TKOLogger.getInstance().addMessage("MOTOR CONTROLLER " + i +
	 * " CAN ERROR"); } } } for (int i = 0; i < Definitions.NUM_FLY_TALONS; i++) { if (flyTalons[i] == null) { try { flyTalons[i] = new
	 * CANTalon(Definitions.FLY_TALON_ID[i]); } catch (AllocationException | CANMessageNotFoundException e) { e.printStackTrace();
	 * System.out.println("MOTOR CONTROLLER " + i + " NOT FOUND OR IN USE"); TKOLogger.getInstance().addMessage("MOTOR CONTROLLER " + i +
	 * " CAN ERROR"); } } } for (int i = 0; i < Definitions.NUM_CONVEYOR_TALONS; i++) { if (conveyorTalons[i] == null) { try {
	 * conveyorTalons[i] = new CANTalon(Definitions.CONVEYOR_ID[i]); } catch (AllocationException | CANMessageNotFoundException e) {
	 * e.printStackTrace(); System.out.println("MOTOR CONTROLLER " + i + " NOT FOUND OR IN USE");
	 * TKOLogger.getInstance().addMessage("MOTOR CONTROLLER " + i + " CAN ERROR"); } } } if (doubleSolenoids[0] == null) // gearbox
	 * doubleSolenoids[0] = new DoubleSolenoid(0, 1); if (doubleSolenoids[1] == null) // arm doubleSolenoids[1] = new DoubleSolenoid(2, 3);
	 * if (doubleSolenoids[2] == null) // intake doubleSolenoids[2] = new DoubleSolenoid(4, 5);
	 * 
	 * if (limitSwitches[0] == null) // ball switch limitSwitches[0] = new DigitalInput(0); if (limitSwitches[1] == null) // intake switch
	 * limitSwitches[1] = new DigitalInput(1); if (limitSwitches[2] == null) // arm switch limitSwitches[2] = new DigitalInput(2);
	 * 
	 * if (compressor == null) compressor = new Compressor(0);
	 * 
	 * configDriveTalons(Definitions.DRIVE_P, Definitions.DRIVE_I, Definitions.DRIVE_D, Definitions.DRIVE_TALONS_NORMAL_CONTROL_MODE);
	 * configFlyTalons(Definitions.SHOOTER_kP, Definitions.SHOOTER_kI, Definitions.SHOOTER_kD, Definitions.FLY_TALONS_NORMAL_CONTROL_MODE);
	 * configConveyorTalons(Definitions.CONVEYOR_CONTROL_MODE);
	 * 
	 * System.out.println("Initialized objects (testing)"); }
	 */

	public static synchronized void initTesting()
	{
		if (xbox == null)
			xbox = new XboxController(Definitions.JOYSTICK_ID[0]);

		// joysticks[2] = new Joystick(2);

		for (int i = 1; i < Definitions.NUM_JOYSTICKS; i++)
		{
			if (joysticks[i] == null)
				joysticks[i] = new Joystick(Definitions.JOYSTICK_ID[i]);
		}
		
		if (gyro == null)
		{
			gyro = new ADXRS450_Gyro(Definitions.GYRO_SPI_PORT);
			// gyro.initGyro();
			// gyro.setSensitivity(7. / 1000.);
			gyro.reset();
			System.out.println("Gyro initialized: " + Timer.getFPGATimestamp());
		}

		initDriveTalons();
		initIntakeTalons();
		initShooterTalons();
		initWinchTalons();
		initBallFeederTalons();

		configDriveTalons(Definitions.DRIVE_P, Definitions.DRIVE_I, Definitions.DRIVE_D, Definitions.DRIVE_TALONS_NORMAL_CONTROL_MODE);
		configWinchTalons(Definitions.WINCH_P, Definitions.WINCH_I, Definitions.WINCH_D, Definitions.WINCH_TALONS_NORMAL_CONTROL_MODE);
		configShooterTalons(Definitions.SHOOTER_P, Definitions.SHOOTER_I, Definitions.SHOOTER_D,
				Definitions.SHOOTER_TALONS_NORMAL_CONTROL_MODE);
		configIntakeTalons(Definitions.INTAKE_TALONS_NORMAL_CONTROL_MODE);
		configBallFeederTalons(Definitions.BALL_FEEDER_TALONS_NORMAL_CONTROL_MODE);

		for (int i = 0; i < Definitions.NUM_DINPUTS; i++)
		{
			if (digitalInputs[i] == null)
			{
				try
				{
					digitalInputs[i] = new DigitalInput(Definitions.DINPUT_ID[i]);
				}
				catch (AllocationException e)
				{
					e.printStackTrace();
					TKOLogger.getInstance().addMessage("DIGITAL INPUT " + i + " NOT FOUND");
				}
			}
		}

		if (doubleSolenoids[0] == null)
			doubleSolenoids[0] = new DoubleSolenoid(0, 1);
		if (doubleSolenoids[1] == null) 
			doubleSolenoids[1] = new DoubleSolenoid(2, 3); 
		if (doubleSolenoids[2] == null) 
			doubleSolenoids[2] = new DoubleSolenoid(4, 5); 
		if (compressor == null)
			compressor = new Compressor(Definitions.PCM_ID);

	}

	public static synchronized void initObjects()
	{
		// TODO maybe destroy objects before initializing them?
		if (xbox == null)
			xbox = new XboxController(Definitions.JOYSTICK_ID[0]);

		for (int i = 1; i < Definitions.NUM_JOYSTICKS; i++)
		{
			if (joysticks[i] == null)
				joysticks[i] = new Joystick(Definitions.JOYSTICK_ID[i]);
		}
		for (int i = 0; i < Definitions.NUM_DRIVE_TALONS; i++)
		{
			if (driveTalons[i] == null)
			{
				try
				{
					driveTalons[i] = new CANTalon(Definitions.DRIVE_TALON_ID[i]);
				}
				catch (AllocationException | CANMessageNotFoundException e)
				{
					e.printStackTrace();
					System.out.println("MOTOR CONTROLLER " + i + " NOT FOUND OR IN USE");
					TKOLogger.getInstance().addMessage("MOTOR CONTROLLER " + i + " CAN ERROR");
				}
			}
		}
		for (int i = 0; i < Definitions.NUM_WINCH_TALONS; i++)
		{
			if (winchTalons[i] == null)
			{
				try
				{
					winchTalons[i] = new CANTalon(Definitions.WINCH_TALON_ID[i]);
				}
				catch (AllocationException | CANMessageNotFoundException e)
				{
					e.printStackTrace();
					System.out.println("MOTOR CONTROLLER " + i + " NOT FOUND OR IN USE");
					TKOLogger.getInstance().addMessage("MOTOR CONTROLLER " + i + " CAN ERROR");
				}
			}
		}
		for (int i = 0; i < Definitions.NUM_SHOOTER_TALONS; i++)
		{
			if (shooterTalons[i] == null)
			{
				try
				{
					shooterTalons[i] = new CANTalon(Definitions.SHOOTER_TALON_ID[i]);
				}
				catch (AllocationException | CANMessageNotFoundException e)
				{
					e.printStackTrace();
					System.out.println("MOTOR CONTROLLER " + i + " NOT FOUND OR IN USE");
					TKOLogger.getInstance().addMessage("MOTOR CONTROLLER " + i + " CAN ERROR");
				}
			}
		}
		for (int i = 0; i < Definitions.NUM_INTAKE_TALONS; i++)
		{
			if (intakeTalons[i] == null)
			{
				try
				{
					intakeTalons[i] = new CANTalon(Definitions.INTAKE_TALON_ID[i]);
				}
				catch (AllocationException | CANMessageNotFoundException e)
				{
					e.printStackTrace();
					System.out.println("MOTOR CONTROLLER " + i + " NOT FOUND OR IN USE");
					TKOLogger.getInstance().addMessage("MOTOR CONTROLLER " + i + " CAN ERROR");
				}
			}
		}
		for (int i = 0; i < Definitions.NUM_DINPUTS; i++)
		{
			if (digitalInputs[i] == null)
			{
				try
				{
					digitalInputs[i] = new DigitalInput(Definitions.DINPUT_ID[i]);
				}
				catch (AllocationException e)
				{
					e.printStackTrace();
					TKOLogger.getInstance().addMessage("DIGITAL INPUT " + i + " NOT FOUND");
				}
			}
		}

		if (doubleSolenoids[0] == null)
			doubleSolenoids[0] = new DoubleSolenoid(Definitions.SHIFTER_A, Definitions.SHIFTER_B);
		if (compressor == null)
			compressor = new Compressor(Definitions.PCM_ID);
		if (gyro == null)
		{
			gyro = new ADXRS450_Gyro(Definitions.GYRO_SPI_PORT);
			// gyro.initGyro();
			// gyro.setSensitivity(7. / 1000.);
			gyro.reset();
			System.out.println("Gyro initialized: " + Timer.getFPGATimestamp());
		}

		// if (arduinoSignal == null)
		// arduinoSignal = new AnalogOutput(0);

		configDriveTalons(Definitions.DRIVE_P, Definitions.DRIVE_I, Definitions.DRIVE_D, Definitions.DRIVE_TALONS_NORMAL_CONTROL_MODE);
		configWinchTalons(Definitions.WINCH_P, Definitions.WINCH_I, Definitions.WINCH_D, Definitions.WINCH_TALONS_NORMAL_CONTROL_MODE);
		configShooterTalons(Definitions.SHOOTER_P, Definitions.SHOOTER_I, Definitions.SHOOTER_D,
				Definitions.SHOOTER_TALONS_NORMAL_CONTROL_MODE);
		configIntakeTalons(Definitions.INTAKE_TALONS_NORMAL_CONTROL_MODE);

	}

	public static synchronized void initDriveTalons()
	{
		for (int i = 0; i < Definitions.NUM_DRIVE_TALONS; i++)
		{
			if (driveTalons[i] == null)
			{
				try
				{
					driveTalons[i] = new CANTalon(Definitions.DRIVE_TALON_ID[i]);
				}
				catch (AllocationException | CANMessageNotFoundException e)
				{
					e.printStackTrace();
					System.out.println("MOTOR CONTROLLER " + i + " NOT FOUND OR IN USE");
					TKOLogger.getInstance().addMessage("MOTOR CONTROLLER " + i + " CAN ERROR");
				}
			}
		}
	}

	public static synchronized void initWinchTalons()
	{
		for (int i = 0; i < Definitions.NUM_WINCH_TALONS; i++)
		{
			if (winchTalons[i] == null)
			{
				try
				{
					winchTalons[i] = new CANTalon(Definitions.WINCH_TALON_ID[i]);
				}
				catch (AllocationException | CANMessageNotFoundException e)
				{
					e.printStackTrace();
					System.out.println("MOTOR CONTROLLER " + i + " NOT FOUND OR IN USE");
					TKOLogger.getInstance().addMessage("MOTOR CONTROLLER " + i + " CAN ERROR");
				}
			}
		}
	}

	public static synchronized void initShooterTalons()
	{
		for (int i = 0; i < Definitions.NUM_SHOOTER_TALONS; i++)
		{
			if (shooterTalons[i] == null)
			{
				try
				{
					shooterTalons[i] = new CANTalon(Definitions.SHOOTER_TALON_ID[i]);
				}
				catch (AllocationException | CANMessageNotFoundException e)
				{
					e.printStackTrace();
					System.out.println("MOTOR CONTROLLER " + i + " NOT FOUND OR IN USE");
					TKOLogger.getInstance().addMessage("MOTOR CONTROLLER " + i + " CAN ERROR");
				}
			}
		}
	}

	public static synchronized void initIntakeTalons()
	{
		for (int i = 0; i < Definitions.NUM_INTAKE_TALONS; i++)
		{
			if (intakeTalons[i] == null)
			{
				try
				{
					intakeTalons[i] = new CANTalon(Definitions.INTAKE_TALON_ID[i]);
				}
				catch (AllocationException | CANMessageNotFoundException e)
				{
					e.printStackTrace();
					System.out.println("MOTOR CONTROLLER " + i + " NOT FOUND OR IN USE");
					TKOLogger.getInstance().addMessage("MOTOR CONTROLLER " + i + " CAN ERROR");
				}
			}
		}
	}

	public static synchronized void initBallFeederTalons()
	{
		for (int i = 0; i < Definitions.NUM_BALL_FEEDER_TALONS; i++)
		{
			if (ballFeederTalons[i] == null)
			{
				try
				{
					ballFeederTalons[i] = new CANTalon(Definitions.BALL_FEEDER_TALON_ID[i]);
				}
				catch (AllocationException | CANMessageNotFoundException e)
				{
					e.printStackTrace();
					System.out.println("MOTOR CONTROLLER " + i + " NOT FOUND OR IN USE");
					TKOLogger.getInstance().addMessage("MOTOR CONTROLLER " + i + " CAN ERROR");
				}
			}
		}
	}

	public static synchronized void configDriveTalons(double p, double I, double d, TalonControlMode mode)
	{
		for (int i = 0; i < Definitions.NUM_DRIVE_TALONS; i++)
		{
			driveTalons[i].delete();
			driveTalons[i] = null;
			driveTalons[i] = new CANTalon(Definitions.DRIVE_TALON_ID[i]);
			if (driveTalons[i] != null)
			{
				if (i % 2 == 1) // if follower
				{
					driveTalons[i].changeControlMode(CANTalon.TalonControlMode.Follower);
					driveTalons[i].set(driveTalons[i - 1].getDeviceID()); // set to follow the CANTalon with id i - 1;
				}
				else
				// if not follower
				{
					driveTalons[i].changeControlMode(mode);
					driveTalons[i].setFeedbackDevice(Definitions.DRIVE_ENCODER_TYPE);
					driveTalons[i].setPID(p, I, d);
				}
				driveTalons[i].enableBrakeMode(Definitions.DRIVE_BRAKE_MODE[i]);
				driveTalons[i].reverseOutput(Definitions.DRIVE_REVERSE_OUTPUT_MODE[i]);
				driveTalons[i].reverseSensor(Definitions.DRIVE_REVERSE_SENSOR[i]);
				driveTalons[i].setVoltageRampRate(50.);
			}
		}
	}

	public static synchronized void configWinchTalons(double p, double I, double d, TalonControlMode mode)
	{
		for (int i = 0; i < Definitions.NUM_WINCH_TALONS; i++)
		{
			winchTalons[i].delete();
			winchTalons[i] = null;
			winchTalons[i] = new CANTalon(Definitions.WINCH_TALON_ID[i]);
			if (winchTalons[i] != null)
			{
				if (i % 2 == 1) // if follower
				{
					winchTalons[i].changeControlMode(CANTalon.TalonControlMode.Follower);
					winchTalons[i].set(winchTalons[i - 1].getDeviceID()); // set to follow the CANTalon with id i - 1;
				}
				else
				// if not follower
				{
					winchTalons[i].changeControlMode(mode);
					winchTalons[i].setFeedbackDevice(Definitions.WINCH_ENCODER_TYPE);
					winchTalons[i].setPID(p, I, d);
				}
				winchTalons[i].enableBrakeMode(Definitions.WINCH_BRAKE_MODE[i]);
				winchTalons[i].reverseOutput(Definitions.WINCH_REVERSE_OUTPUT_MODE[i]);
				winchTalons[i].reverseSensor(Definitions.WINCH_REVERSE_SENSOR[i]);
				winchTalons[i].setVoltageRampRate(96.);
			}
		}
	}

	public static synchronized void configShooterTalons(double p, double I, double d, TalonControlMode mode)
	{
		for (int i = 0; i < Definitions.NUM_SHOOTER_TALONS; i++)
		{
			shooterTalons[i].delete();
			shooterTalons[i] = null;
			shooterTalons[i] = new CANTalon(Definitions.SHOOTER_TALON_ID[i]);
			if (shooterTalons[i] != null)
			{
				if (i % 2 == 1) // if follower
				{
					shooterTalons[i].changeControlMode(CANTalon.TalonControlMode.Follower);
					shooterTalons[i].set(shooterTalons[i - 1].getDeviceID()); // set to follow the CANTalon with id i - 1;
				}
				else
				// if not follower
				{
					shooterTalons[i].changeControlMode(mode);
					shooterTalons[i].setFeedbackDevice(Definitions.SHOOTER_ENCODER_TYPE);
					shooterTalons[i].setPID(p, I, d);
				}
				shooterTalons[i].enableBrakeMode(Definitions.SHOOTER_BRAKE_MODE[i]);
				shooterTalons[i].reverseOutput(Definitions.SHOOTER_REVERSE_OUTPUT_MODE[i]);
				shooterTalons[i].reverseSensor(Definitions.SHOOTER_REVERSE_SENSOR[i]);
				shooterTalons[i].setVoltageRampRate(96.);
				shooterTalons[i].clearIAccum();
			}
		}
	}

	public static synchronized void configIntakeTalons(TalonControlMode mode)
	{
		for (int i = 0; i < Definitions.NUM_INTAKE_TALONS; i++)
		{
			intakeTalons[i].delete();
			intakeTalons[i] = null;
			intakeTalons[i] = new CANTalon(Definitions.INTAKE_TALON_ID[i]);
			if (intakeTalons[i] != null)
			{
				if (i % 2 == 1) // if follower
				{
					intakeTalons[i].changeControlMode(CANTalon.TalonControlMode.Follower);
					intakeTalons[i].set(intakeTalons[i - 1].getDeviceID()); // set to follow the CANTalon with id i - 1;
				}
				else
				// if not follower
				{
					intakeTalons[i].changeControlMode(mode);
				}
				intakeTalons[i].enableBrakeMode(Definitions.INTAKE_BRAKE_MODE[i]);
				intakeTalons[i].reverseOutput(Definitions.INTAKE_REVERSE_OUTPUT_MODE[i]);
				intakeTalons[i].reverseSensor(Definitions.INTAKE_REVERSE_SENSOR[i]);
				intakeTalons[i].setVoltageRampRate(96.);
			}
		}
	}

	public static synchronized void configBallFeederTalons(TalonControlMode mode)
	{
		for (int i = 0; i < Definitions.NUM_BALL_FEEDER_TALONS; i++)
		{
			ballFeederTalons[i].delete();
			ballFeederTalons[i] = null;
			ballFeederTalons[i] = new CANTalon(Definitions.BALL_FEEDER_TALON_ID[i]);
			if (ballFeederTalons[i] != null)
			{
				if (i == 1 || i == 3) // if follower
				{
					ballFeederTalons[i].changeControlMode(CANTalon.TalonControlMode.Follower);
					ballFeederTalons[i].set(i - 1); // set to follow the CANTalon with id i - 1;
				}
				else
				// if not follower
				{
					ballFeederTalons[i].changeControlMode(mode);
				}
				ballFeederTalons[i].enableBrakeMode(Definitions.BALL_FEEDER_BRAKE_MODE[i]);
				ballFeederTalons[i].reverseOutput(Definitions.BALL_FEEDER_REVERSE_OUTPUT_MODE[i]);
				ballFeederTalons[i].reverseSensor(Definitions.BALL_FEEDER_REVERSE_SENSOR[i]);
				ballFeederTalons[i].setVoltageRampRate(96.);
			}
		}
	}

	public static synchronized void changeTalonMode(CANTalon target, CANTalon.TalonControlMode newMode, double newP, double newI,
			double newD) throws TKOException
	{
		if (target == null)
			throw new TKOException("ERROR Attempted to change mode of null CANTalon");
		if (newMode == target.getControlMode())
		{
			target.setPID(newP, newI, newD);
			return;
		}

		// if (target.getControlMode() != CANTalon.TalonControlMode.Position && target.getControlMode() != CANTalon.TalonControlMode.Speed)
		target.setFeedbackDevice(Definitions.DEF_ENCODER_TYPE);

		System.out.println(target.getP());
		System.out.println(target.getI());
		System.out.println(target.getD());

		target.changeControlMode(newMode);
		target.setPID(newP, newI, newD);
		target.enableControl();
		System.out.println("CHANGED TALON [" + target.getDeviceID() + "] TO [" + target.getControlMode().getValue() + "]");
	}

	public static synchronized void changeTalonMode(CANTalon target, CANTalon.TalonControlMode newMode) throws TKOException
	{
		if (target == null)
			throw new TKOException("ERROR Attempted to change mode of null CANTalon");

		// if (target.getControlMode() != CANTalon.TalonControlMode.Position && target.getControlMode() != CANTalon.TalonControlMode.Speed)
		target.setFeedbackDevice(Definitions.DEF_ENCODER_TYPE);

		target.changeControlMode(newMode);
		target.enableControl();

		System.out.println("CHANGED TALON [" + target.getDeviceID() + "] TO [" + target.getControlMode().getValue() + "]");
	}

	// TODO Update
	public static synchronized void autonInit(double p, double i, double d) throws TKOException
	{
		TKOHardware.changeTalonMode(TKOHardware.getLeftDrive(), CANTalon.TalonControlMode.Position, p, i, d);
		TKOHardware.changeTalonMode(TKOHardware.getRightDrive(), CANTalon.TalonControlMode.Position, p, i, d);
		TKOHardware.getLeftDrive().reverseOutput(false);
		TKOHardware.getRightDrive().reverseOutput(true);
		TKOHardware.getLeftDrive().reverseSensor(false);
		TKOHardware.getRightDrive().reverseSensor(true); 
		// TODO return this to true
		TKOHardware.getLeftDrive().enableBrakeMode(false);
		TKOHardware.getRightDrive().enableBrakeMode(false);
//		TKOHardware.getLeftDrive().setPosition(0.); // resets encoder
//		TKOHardware.getRightDrive().setPosition(0.);
		TKOHardware.getLeftDrive().ClearIaccum(); // stops bounce
		TKOHardware.getRightDrive().ClearIaccum();
		Timer.delay(0.1);
		TKOHardware.getLeftDrive().set(TKOHardware.getLeftDrive().getPosition());
		TKOHardware.getRightDrive().set(TKOHardware.getRightDrive().getPosition());
		TKOHardware.getDSolenoid(0).set(Definitions.SHIFTER_LOW);
		TKOHardware.getDSolenoid(1).set(Definitions.GEAR_DOOR_DOWN);
//		TKOHardware.getDSolenoid(2).set(Definitions.GEAR_ARMS_IN); 
		TKOHardware.getLeftDrive().reset();
		TKOHardware.getRightDrive().reset();
		TKOHardware.getLeftDrive().enableControl();
		TKOHardware.getRightDrive().enableControl();
	}

	/**
	 * Sets *ALL* drive Talons to given value. CAUTION WHEN USING THIS METHOD, DOES NOT CARE ABOUT FOLLOWER TALONS. Intended for PID Tuning
	 * loop ONLY.
	 * 
	 * @deprecated Try not to use this method. It is very prone to introducing errors. Use getLeftDrive() and getRightDrive() or
	 *             getDriveTalon(int n) instead, unless you know what you are doing.
	 * @param double setTarget - Value to set for all the talons
	 */
	public static synchronized void setAllDriveTalons(double setTarget)
	{
		for (int i = 0; i < Definitions.NUM_DRIVE_TALONS; i++)
		{
			if (driveTalons[i] != null)
			{
				driveTalons[i].set(setTarget);
			}
		}
	}

	public static synchronized void destroyObjects()
	{
		if (xbox != null)
			xbox = null;

		for (int i = 0; i < Definitions.NUM_JOYSTICKS; i++)
		{
			if (joysticks[i] != null)
			{
				joysticks[i] = null;
			}
		}
		for (int i = 0; i < Definitions.NUM_DRIVE_TALONS; i++)
		{
			if (driveTalons[i] != null)
			{
				driveTalons[i].delete();
				driveTalons[i] = null;
			}
		}
		for (int i = 0; i < Definitions.NUM_DSOLENOIDS; i++)
		{
			if (doubleSolenoids[i] != null)
			{
				doubleSolenoids[i].free();
				doubleSolenoids[i] = null;
			}
		}
		if (compressor != null)
		{
			compressor.free();
			compressor = null;
		}
		if (arduinoSignal != null)
		{
			arduinoSignal.free();
			arduinoSignal = null;
		}
	}

	public static synchronized XboxController getXboxController() throws TKOException
	{
		if (xbox == null)
			throw new TKOException("ERROR: Xbox controller is null");
		return xbox;
	}

	public static synchronized Joystick getJoystick(int num) throws TKOException
	{
		if (num >= Definitions.NUM_JOYSTICKS)
		{
			throw new TKOException("Joystick requested out of bounds");
		}
		if (joysticks[num] != null)
			return joysticks[num];
		else
			throw new TKOException("Joystick " + (num) + "(array value) is null");
	}

	/**
	 * To avoid potential problems, use getLeftDrive() and/or getRightDrive() instead
	 */
	public static synchronized CANTalon getDriveTalon(int num) throws TKOException
	{
		if (num >= Definitions.NUM_DRIVE_TALONS)
		{
			throw new TKOException("Drive talon requested out of bounds");
		}
		if (driveTalons[num] != null)
		{
			// if (driveTalons[num].getControlMode() == CANTalon.TalonControlMode.Follower)
			// throw new TKOException("WARNING: Do not access follower talon");
			// else
				return driveTalons[num];
		}
		else
			throw new TKOException("Drive talon " + (num) + "(array value) is null");
	}

	public static synchronized CANTalon getLeftDrive() throws TKOException
	{
		if (driveTalons[0] == null || driveTalons[1] == null)
			throw new TKOException("Left Drive Talon is null");

		return driveTalons[0];
	}

	public static synchronized CANTalon getRightDrive() throws TKOException
	{
		if (driveTalons[2] == null || driveTalons[3] == null)
			throw new TKOException("Right Drive Talon is null");

		return driveTalons[2];
	}

	public static synchronized CANTalon getWinchTalon() throws TKOException
	{
		for (int i = 0; i < winchTalons.length; i++)
		{
			if (winchTalons[i] == null)
			{
				throw new TKOException("Winch Talon " + i + " is null");
			}
		}
		return winchTalons[0];
	}

	public static synchronized CANTalon getWinchTalon(int n) throws TKOException
	{
		if (winchTalons[n] == null)
		{
			throw new TKOException("Winch Talon " + n + " is null");
		}
		return winchTalons[n];
	}

	public static synchronized CANTalon getShooterTalon() throws TKOException
	{
		for (int i = 0; i < shooterTalons.length; i++)
		{
			if (shooterTalons[i] == null)
			{
				throw new TKOException("Shooter Talon " + i + " is null");
			}
		}
		return shooterTalons[0];
	}

	public static synchronized CANTalon getShooterTalon(int n) throws TKOException
	{
		if (shooterTalons[n] == null)
		{
			throw new TKOException("Shooter Talon " + n + " is null");
		}
		return shooterTalons[n];
	}

	public static synchronized CANTalon getIntakeTalon() throws TKOException
	{
		for (int i = 0; i < intakeTalons.length; i++)
		{
			if (intakeTalons[i] == null)
			{
				throw new TKOException("intake Talon " + i + " is null");
			}
		}
		return intakeTalons[0];
	}

	public static synchronized CANTalon getIntakeTalon(int n) throws TKOException
	{
		if (shooterTalons[n] == null)
		{
			throw new TKOException("Shooter Talon " + n + " is null");
		}
		return shooterTalons[n];
	}

	public static synchronized CANTalon getBallFeederTalon() throws TKOException
	{
		for (int i = 0; i < ballFeederTalons.length; i++)
		{
			if (ballFeederTalons[i] == null)
			{
				throw new TKOException("ball feeder Talon " + i + " is null");
			}
		}
		return ballFeederTalons[0];
	}

	public static synchronized CANTalon getBallFeederTalon(int n) throws TKOException
	{
		if (ballFeederTalons[n] == null)
		{
			throw new TKOException("Ball feeder Talon " + n + " is null");
		}
		return ballFeederTalons[n];
	}

	public static synchronized DoubleSolenoid getDSolenoid(int num) throws TKOException
	{
		if (num >= Definitions.NUM_DSOLENOIDS)
		{
			throw new TKOException("Piston requested out of bounds");
		}
		if (doubleSolenoids[num] != null)
			return doubleSolenoids[num];
		else
			throw new TKOException("Piston " + (num) + "(array value) is null");
	}

	public static synchronized DigitalInput getDigitalInput(int num) throws TKOException
	{
		if (num >= Definitions.NUM_DINPUTS)
		{
			throw new TKOException("DInput requested out of bounds");
		}
		if (digitalInputs[num] != null)
			return digitalInputs[num];
		else
			throw new TKOException("Joystick " + (num) + "(array value) is null");
	}

	public static synchronized Compressor getCompressor() throws TKOException
	{
		if (compressor == null)
			throw new TKOException("Compressor is null");
		return compressor;
	}

	public static synchronized void arduinoWrite(double voltage) throws TKOException
	{
		if (arduinoSignal == null)
			throw new TKOException("ARDUINO ANALOG OUT CHANNEL NULL");
		if (voltage < 0. || voltage > 5.)
			throw new TKOException("VOLTAGE OUT OF BOUNDS");
		arduinoSignal.setVoltage(voltage);
	}

	public static synchronized ADXRS450_Gyro getGyro() throws TKOException
	{
		if (gyro == null)
			throw new TKOException("ERROR: Gyro is null");
		return gyro;
	}
}
