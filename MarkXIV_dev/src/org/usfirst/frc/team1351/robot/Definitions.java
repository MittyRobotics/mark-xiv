// Last edited by Ishan Shah
// on 01/12/2017

//TODO: These are not real values, update them!!! 

package org.usfirst.frc.team1351.robot;

import java.util.ArrayList;
import java.util.HashMap;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SPI.Port;

public class Definitions
{
	// Autonomous constants
	// TODO: These are not real values, update them!!!

	public static final double TICKS_PER_INCH = 503; // Low Gear Value
	public static final double AUTON_DRIVE_P = 0.5; // .370 //0.25
	public static final double AUTON_DRIVE_I = 0.0025; // -0.1 //0.0025
	public static final double AUTON_DRIVE_D = 200.0; //100
	public static final double AUTON_GYRO_TURN_P = 0.04;
	public static final double AUTON_GYRO_TURN_I = 0; // 0.1
	public static final double AUTON_GYRO_TURN_D = 0.1;
	public static final double DRIVE_ATOM_INCREMENTER = 500; // 6.5; //3.25;
	public static final double TURN_ATOM_INCREMENTER = 15;

	// Drive constants

	public static final long[] CURRENT_TIMEOUT_LENGTH =
	{ 1000L, 1000L, 1000L, 1000L, 1000L, 1000L };
	public static final int DEF_DATA_REPORTING_THREAD_WAIT = 250;
	public static final double PULSES_PER_INCH = 332.5020781;
	public static final boolean[] DRIVE_BRAKE_MODE =
	{ false, false, false, false };
	public static final boolean[] WINCH_BRAKE_MODE =
	{ true, true };
	public static final boolean[] SHOOTER_BRAKE_MODE =
	{ false, false };
	public static final boolean[] INTAKE_BRAKE_MODE =
	{ false };
	public static final boolean[] BALL_FEEDER_BRAKE_MODE =
	{ false };

	public static final CANTalon.FeedbackDevice DRIVE_ENCODER_TYPE = CANTalon.FeedbackDevice.QuadEncoder;
	public static final CANTalon.FeedbackDevice DEF_ENCODER_TYPE = CANTalon.FeedbackDevice.QuadEncoder;
	public static final CANTalon.FeedbackDevice WINCH_ENCODER_TYPE = CANTalon.FeedbackDevice.QuadEncoder;
	public static final CANTalon.FeedbackDevice SHOOTER_ENCODER_TYPE = CANTalon.FeedbackDevice.CtreMagEncoder_Relative;
	public static final CANTalon.TalonControlMode DRIVE_TALONS_NORMAL_CONTROL_MODE = CANTalon.TalonControlMode.PercentVbus;
	public static final CANTalon.TalonControlMode WINCH_TALONS_NORMAL_CONTROL_MODE = CANTalon.TalonControlMode.PercentVbus;
	public static final CANTalon.TalonControlMode SHOOTER_TALONS_NORMAL_CONTROL_MODE = CANTalon.TalonControlMode.PercentVbus;
	public static final CANTalon.TalonControlMode INTAKE_TALONS_NORMAL_CONTROL_MODE = CANTalon.TalonControlMode.PercentVbus;
	public static final CANTalon.TalonControlMode BALL_FEEDER_TALONS_NORMAL_CONTROL_MODE = CANTalon.TalonControlMode.PercentVbus;

	public static final double DRIVE_P = 3.5;
	public static final double DRIVE_I = 0.01;
	public static final double DRIVE_D = 0;
	public static final double[] DRIVE_MULTIPLIER =
	{ -1., -1., 1., 1. };
	// TODO: |=(_)(|<
	public static final double WINCH_P = 1.0;
	public static final double WINCH_I = 0.01;
	public static final double WINCH_D = 0.0;
	public static final double SHOOTER_P = 0.05; // 0.2273333333?
	public static final double SHOOTER_I = 0.00016; // 0.000025;
	public static final double SHOOTER_D = 0.0;
	public static final double SHOOTER_F = 0.f;// 0.02404067096; //Calculated 
	public static final double SHOOTER_SETSPEED = -2912.f; // -2912 Calculated by C. Fairley 17 Feb 2017 w/ Black Magic


	public static final double WINCH_LADEN_FIXED_OUTPUT = 0.5f;
	public static final double WINCH_UNLADEN_FIXED_OUTPUT = 0.2f;
	public static final double WINCH_UNLADEN_CURRENT = 15.f; //TODO:  This is a little above for range, needs to be a clear difference however for this to work

	public static final double DRIVE_MULTIPLIER_LEFT = DRIVE_MULTIPLIER[0];
	public static final double DRIVE_MULTIPLIER_RIGHT = DRIVE_MULTIPLIER[2];

	public static final boolean[] DRIVE_REVERSE_OUTPUT_MODE =
	{ true, false, false, false };
	public static final boolean[] DRIVE_REVERSE_SENSOR =
	{ false, false, false, false };
	// TODO: |=(_)(|<
	public static final boolean[] WINCH_REVERSE_OUTPUT_MODE =
	{ false, false };
	public static final boolean[] WINCH_REVERSE_SENSOR =
	{ false, false };
	public static final boolean[] SHOOTER_REVERSE_OUTPUT_MODE =
	{ false, false };
	public static final boolean[] SHOOTER_REVERSE_SENSOR =
	{ false, false };
	public static final boolean[] INTAKE_REVERSE_OUTPUT_MODE =
	{ false };
	public static final boolean[] INTAKE_REVERSE_SENSOR =
	{ false };
	public static final boolean[] BALL_FEEDER_REVERSE_OUTPUT_MODE =
	{ false };
	public static final boolean[] BALL_FEEDER_REVERSE_SENSOR =
	{ false };

	public static final double CURRENT_SAFETY_THRESHOLD = 60.; // Amps for the motors

	// Hardware constants

	public static final int[] DRIVE_TALON_ID =
	{ 0, 1, 2, 3 };
	public static final int[] WINCH_TALON_ID =
	{ 4, 5 };
	public static final int[] SHOOTER_TALON_ID =
	{ 6, 7 };
	public static final int[] INTAKE_TALON_ID =
	{ 8 };
	public static final int[] BALL_FEEDER_TALON_ID =
	{ 9 };
	public static final int[] JOYSTICK_ID =
	{ 0, 1, 2, 3 };
	public static final int[] DINPUT_ID =
	{ 0 };

	public static final int NUM_DRIVE_TALONS = 4;
	public static final int NUM_WINCH_TALONS = 2;
	public static final int NUM_SHOOTER_TALONS = 2;
	public static final int NUM_INTAKE_TALONS = 1;
	public static final int NUM_BALL_FEEDER_TALONS = 1;
	public static final int ALL_TALONS = NUM_DRIVE_TALONS + NUM_WINCH_TALONS + NUM_SHOOTER_TALONS + NUM_INTAKE_TALONS
			+ NUM_BALL_FEEDER_TALONS;

	public static final int NUM_ENCODERS = 4;
	public static final int NUM_XBOX = 1; // `/4`/ ! |-|45 4|\\| ><|30>< |=|2!99!|\\|9 |D|_4`/5747!0|\\| |\\|3\\/\\/|35
	public static final int NUM_JOYSTICKS = 3;
	public static final int NUM_DSOLENOIDS = 3;
	public static final int NUM_DINPUTS = 0; 

	public static final int PCM_ID = 0;

	// Evom constants

	public static final Port GYRO_SPI_PORT = Port.kOnboardCS0;
	public static final int GYRO_ANALOG_CHANNEL = 1;

	// TODO: Add in PID values, Incrementer Values, Output Reverse Values, Normal Control Modes, Current Maxes, and Timeouts

	public static final double[] TALON_CURRENT_TIMEOUT =
	{ 5, 5, 5, 5, 5, 5, 5, 5, 5 };

	// TODO Fix
	public static final double REVOLUTIONS_TO_TICKS = 6000. / 1024.;
	public static final double TICKS_TO_REVOLUTIONS = 1024. / 6000.;

	// Pneumatics constants
	public static final DoubleSolenoid.Value SHIFTER_LOW = DoubleSolenoid.Value.kReverse;
	public static final DoubleSolenoid.Value SHIFTER_HIGH = DoubleSolenoid.Value.kForward;
	public static final DoubleSolenoid.Value GEAR_DOOR_UP = DoubleSolenoid.Value.kForward;
	public static final DoubleSolenoid.Value GEAR_DOOR_DOWN = DoubleSolenoid.Value.kReverse;
	public static final Value GEAR_ARMS_IN = Value.kReverse;
	public static final Value GEAR_ARMS_OUT = Value.kForward;

	// TODO: |=(_)(|< "Only Understood by privileged members"-Tiina 2017
	public static final int SHIFTER_A = 2; // drive train shifting piston
	public static final int SHIFTER_B = 6;

	// Thread definitions

	public static ArrayList<String> threadNames = new ArrayList<String>();
	public static HashMap<String, Integer> threadPriorities;

	public static void addThreadName(String name)
	{
		threadNames.add(name);
	}

	public static int getPriority(String name)
	{
		switch (name)
		{
		case "drive":
			return 2;
		case "pneumatics":
			return 3;
		case "gear":
			return 4;
		case "ball": // state machine
			return 5;
		case "winch":
			return 6;
		case "vision":
			return 7;
		case "ledArduino":
			return 8;
		case "logger":
			return 9;
		case "talonSafety":
			return 10;

		default:
			return Thread.NORM_PRIORITY; // 5
		}
	}
}
