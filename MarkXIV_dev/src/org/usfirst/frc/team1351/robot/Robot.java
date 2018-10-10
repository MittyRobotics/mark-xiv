// Last edited by Ishan Shah
// on 01/12/2017

package org.usfirst.frc.team1351.robot;

import org.usfirst.frc.team1351.robot.atoms.Molecule;
import org.usfirst.frc.team1351.robot.atoms.auton.DriveAtom;
import org.usfirst.frc.team1351.robot.atoms.auton.GearAtom;
import org.usfirst.frc.team1351.robot.atoms.auton.GyroTurnAtom;
import org.usfirst.frc.team1351.robot.drive.TKODrive;
import org.usfirst.frc.team1351.robot.evom.TKOPneumatics;
import org.usfirst.frc.team1351.robot.evom.TKOWinch;
import org.usfirst.frc.team1351.robot.logger.TKOLogger;
import org.usfirst.frc.team1351.robot.evom.TKOIntake;
import org.usfirst.frc.team1351.robot.evom.TKOShooter;
import org.usfirst.frc.team1351.robot.util.TKOHardware;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot
{
	// I changed this from <String> to <Object> because it didn't work with <String> fix if necessary
	SendableChooser<Integer> autonChooser = new SendableChooser<>();
	UsbCamera gear;
	UsbCamera back;

	public Robot()
	{
		// Don't put stuff here, use robotInit();
	}

	@Override
	public void robotInit()
	{
		System.out.println("-----WELCOME TO MarkXIV 2017-----");
		System.out.println("-----SYSTEM BOOT: " + Timer.getFPGATimestamp() + "-----");

		SmartDashboard.putNumber("Shooter P: ", Definitions.SHOOTER_P);
		SmartDashboard.putNumber("Shooter I: ", Definitions.SHOOTER_I);
		SmartDashboard.putNumber("Shooter D: ", Definitions.SHOOTER_D);
		SmartDashboard.putNumber("Shooter - Input Speed: ", Definitions.SHOOTER_SETSPEED);

		SmartDashboard.putNumber("Drive distance: ", 12);
		SmartDashboard.putNumber("Turn angle: ", 90);

		SmartDashboard.putNumber("Turn Incrementer: ", Definitions.TURN_ATOM_INCREMENTER);
		SmartDashboard.putNumber("Turn P: ", Definitions.AUTON_GYRO_TURN_P);
		SmartDashboard.putNumber("Turn I: ", Definitions.AUTON_GYRO_TURN_I);
		SmartDashboard.putNumber("Turn D: ", Definitions.AUTON_GYRO_TURN_D);

		SmartDashboard.putNumber("Drive P: ", Definitions.AUTON_DRIVE_P);
		SmartDashboard.putNumber("Drive I: ", Definitions.AUTON_DRIVE_I);
		SmartDashboard.putNumber("Drive D: ", Definitions.AUTON_DRIVE_D);

		autonChooser.addDefault("Move Forward", new Integer(0));
		autonChooser.addObject("Red Center (Longer)", new Integer(1));
		autonChooser.addObject("Blue Center", new Integer(2));
		autonChooser.addObject("Right Side (Turns to the left)", new Integer(3));
		autonChooser.addObject("Left Side (Turns to the right)", new Integer(4));
		autonChooser.addObject("Square", new Integer(5));
		autonChooser.addObject("Empty", new Integer(6));
		SmartDashboard.putData("Auto modes", autonChooser);
		TKOHardware.initTesting();

		// TKOVision.initCamera((char) 0);
		// TKOVision.initCamera((char) 1);
		// TKOVision.initCamera((char) 2);

		gear = CameraServer.getInstance().startAutomaticCapture("gear", 0);
		gear.setResolution(160, 120);
		gear.setFPS(30);

		back = CameraServer.getInstance().startAutomaticCapture("back", 1);
		back.setResolution(160, 120);
		back.setFPS(30);

		System.out.println("Initialization finished");

	}

	@Override
	public void autonomous()
	{
		System.out.println("Enabling autonomous!");

		TKOLogger.getInstance().start();
		// TKODataReporting.getInstance().start();
		// TKOTalonSafety.getInstance().start();
		// TKOLEDArduino.getInstance().start();
		// TKOPneumatics.getInstance().start();
		// TKOPneumatics.getInstance().reset();

		Molecule molecule = new Molecule();
		molecule.clear();

		double distance = SmartDashboard.getNumber("Drive distance: ", 12);
		double angle = SmartDashboard.getNumber("Turn angle: ", 90);

		if (autonChooser.getSelected().equals(0))
		{
			// molecule.add(new ShootAtom());

			// C-P for a one-foot tester for 2017 auton drive forward
			molecule.add(new DriveAtom(130, 1));
			// molecule.add(new DriveAtom(12, 1));
			// molecule.add(new DriveAtom(12, 1));
			// molecule.add(new DriveAtom(12, 1));
			// molecule.add(new DriveAtom(12, 1));
			// molecule.add(new DriveAtom(12, 1));
			// molecule.add(new DriveAtom(12, 1));
			// molecule.add(new DriveAtom(12, 1));
			// molecule.add(new DriveAtom(17, 1)); //113, 1)); //36, 1));
			// Numbers are various inches depeding on where we're going b/c god knows we don't
			// molecule.add(new GyroTurnAtom(45));
			// molecule.add(new DriveAtom(12, 1));

			// DAVIS GEAR TEST TUNING
			// molecule.add(new DriveAtom(36, 1));
			// molecule.add(new GearAtom());
			// molecule.add(new DriveAtom(12, 1));

			// ACTUAL MOVE FOWARD
			// molecule.add(new DriveAtom(distance, 1));
			// System.out.println("YOU DONE FUCKED UP");

			// molecule.add(new GearAtom());
		}
		else if (autonChooser.getSelected().equals(1)) // 4 feet forward, drop gear RED
		{
			molecule.add(new DriveAtom(76.875, 1));// 76.875, 1)); //74.875 ORIGINAL
			molecule.add(new GearAtom());
			molecule.add(new DriveAtom(-30, 0));

		}
		else if (autonChooser.getSelected().equals(2)) // 4 feet forward, drop gear BLUE
		{
			molecule.add(new DriveAtom(72.875, 1));// 76.875, 1));
			molecule.add(new GearAtom());
			molecule.add(new DriveAtom(-30, 2));

		}
		else if (autonChooser.getSelected().equals(3))// right side gear auton
		{
			molecule.add(new DriveAtom(73.3661417322835, 1));// 54...//
			molecule.add(new GyroTurnAtom(-57.5));
			molecule.add(new DriveAtom(54.2, 1));// 93...//81
			molecule.add(new GearAtom());
			molecule.add(new DriveAtom(-30, 2));
		}
		else if (autonChooser.getSelected().equals(4))// left side gear auton
		{
			molecule.add(new DriveAtom(73.3661417322835, 1));// 54...//
			molecule.add(new GyroTurnAtom(57.5));
			molecule.add(new DriveAtom(54.2, 1));// 93...//81
			molecule.add(new GearAtom());
			molecule.add(new DriveAtom(-30, 2));
		}
		else if (autonChooser.getSelected().equals(5))// Turn in a box (test for gyro)
		{
			molecule.add(new DriveAtom(8, 1));
			molecule.add(new GyroTurnAtom(90));
			molecule.add(new DriveAtom(8, 1));
			molecule.add(new GyroTurnAtom(90));
			molecule.add(new DriveAtom(8, 1));
			molecule.add(new GyroTurnAtom(90));
			molecule.add(new DriveAtom(8, 1));
			molecule.add(new GyroTurnAtom(90));
		}
		else if (autonChooser.getSelected().equals(6))
		{

		}
		else
		{
			System.out.println("Molecule empty why this");
		}

		System.out.println("Running molecule");
		molecule.initAndRun();
		System.out.println("Finished running molecule");

		try
		{
			// TKOPneumatics.getInstance().stop();
			// TKOPneumatics.getInstance().pneuThread.join();
			TKOLogger.getInstance().stop();
			TKOLogger.getInstance().loggerThread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void operatorControl()
	{
		TKOIntake.getInstance().start();
		TKOWinch.getInstance().start();
		TKOShooter.getInstance().start();
		TKODrive.getInstance().start();
		TKOPneumatics.getInstance().start();

		while (isOperatorControl() && isEnabled())
		{
			Timer.delay(0.05); // wait for a motor update time
		}

		try
		{
			TKOShooter.getInstance().stop();
			TKOShooter.getInstance().shooterThread.join();
			TKOIntake.getInstance().stop();
			TKOIntake.getInstance().intakeThread.join();
			TKOWinch.getInstance().stop();
			TKOWinch.getInstance().winchThread.join();
			TKODrive.getInstance().stop();
			TKODrive.getInstance().driveThread.join();
			TKOPneumatics.getInstance().stop();
			TKOPneumatics.getInstance().pneuThread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		// TKOIntake.getInstance().stop();
		// TKOWinch.getInstance().stop();
		// TKOShooter.getInstance().stop();
	}

	@Override
	public void test()
	{
	}
}
