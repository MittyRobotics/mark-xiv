//package org.usfirst.frc.team1351.robot.atoms.auton;
//
//import org.usfirst.frc.team1351.robot.Definitions;
//import org.usfirst.frc.team1351.robot.atoms.Atom;
//import org.usfirst.frc.team1351.robot.logger.TKOLogger;
//import org.usfirst.frc.team1351.robot.util.TKOException;
//import org.usfirst.frc.team1351.robot.util.TKOHardware;
//
//import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//public class GearCurrentAtom extends Atom
//{
//	double ticksPerInch;
//	double distance, incrementer, threshold;
//	double p, i, d;
//	int setting;
//
//	public GearCurrentAtom(double _dist, int n)
//	
//	{
//
//		p = SmartDashboard.getNumber("Drive P: ", 0.f);
//		i = SmartDashboard.getNumber("Drive I:  ", 0.f);
//		d = SmartDashboard.getNumber("Drive D: ", 0.f);
//
//
//		distance = _dist * Definitions.TICKS_PER_INCH;
//		incrementer = Definitions.DRIVE_ATOM_INCREMENTER;
//		threshold = 562; // we can be within approx. half an inch
//
//		setting = n;
//	}
//
//	public void init()
//	{
//		try
//		{
//			TKOHardware.autonInit(p, i, d);
//		}
//		catch (TKOException e)
//		{
//			e.printStackTrace();
//			System.out.println("Talon initialization failed!");
//		}
//		System.out.println("Drive atom initialized");
//	}
//
//	@Override
//	public void execute()
//	{
//		System.out.println("Executing drive atom");
////		try
////		{
////			if (setting == 1)
////			{
////				TKOHardware.getDSolenoid(0).set(Definitions.SHIFTER_HIGH);
////				TKOHardware.getDSolenoid(2).set(Value.kForward);
////				TKOHardware.getDSolenoid(1).set(Value.kForward);
////				Timer.delay(0.5);
////			}
////		}
////		catch (TKOException e1)
////		{
////			e1.printStackTrace();
////		}
//
//		Timer t = new Timer();
//		t.reset();
//		t.start();
//		try
//		{
//			System.out.println("Distance: " + distance);
//			distance += TKOHardware.getLeftDrive().getPosition();
//			System.out.println("Distance is now: " + distance);
////			
//			if (distance > 0)
//			{
//				while (DriverStation.getInstance().isEnabled() && TKOHardware.getLeftDrive().getSetpoint() < distance && TKOHardware.getLeftDrive().getOutputCurrent() < Definitions.GEAR_AUTON_CURRENT)
//				{
//					if (t.get() > 45.0)
//					{
//						System.out.println("Timeout!");
//						break;
//					}
//					System.out.println("Left Error: " + TKOHardware.getLeftDrive().getError() + " Left Setpoint: " + TKOHardware.getLeftDrive().getSetpoint());
//
//					// current setpoint + incrementer
//					TKOHardware.getLeftDrive().set(TKOHardware.getLeftDrive().getSetpoint() + incrementer);
////					TKOHardware.getRightDrive().set(TKOHardware.getRightDrive().getSetpoint() + incrementer);
////					System.out.println("Set: " + TKOHardware.getLeftDrive().getSetpoint()); 
//
//					/*
//					 * System.out.println("Encoder Left: " + TKOHardware.getLeftDrive().getPosition() + "\t Encoder Right: " +
//					 * TKOHardware.getRightDrive().getPosition() + "\t Left Setpoint: " + TKOHardware.getLeftDrive().getSetpoint());
//					 * TKOLogger.getInstance().addMessage( "Encoder Left: " + TKOHardware.getLeftDrive().getPosition() +
//					 * "\t Encoder Right: " + TKOHardware.getRightDrive().getPosition() + "\t Left Setpoint: " +
//					 * TKOHardware.getLeftDrive().getSetpoint());
//					 */
//					Timer.delay(0.01);
//				}
//			} 
//			else
//			// driving in reverse
//			{
//				while (DriverStation.getInstance().isEnabled() && TKOHardware.getLeftDrive().getSetpoint() > distance && TKOHardware.getLeftDrive().getOutputCurrent() < Definitions.GEAR_AUTON_CURRENT)
//				{
//					if (t.get() > 45.0)
//					{
//						System.out.println("Timeout!");
//						break;
//					}
//
//					System.out.println("Left Error: " + TKOHardware.getLeftDrive().getError() + " Left Setpoint: " + TKOHardware.getLeftDrive().getSetpoint());
//
//					TKOHardware.getLeftDrive().set(TKOHardware.getLeftDrive().getSetpoint() - incrementer);
////					TKOHardware.getRightDrive().set(TKOHardware.getRightDrive().getSetpoint() - incrementer);
//
//					/*
//					 * System.out.println("Encoder Left: " + TKOHardware.getLeftDrive().getPosition() + "\t Encoder Right: " +
//					 * TKOHardware.getRightDrive().getPosition() + "\t Left Setpoint: " + TKOHardware.getLeftDrive().getSetpoint());
//					 * TKOLogger.getInstance().addMessage( "Encoder Left: " + TKOHardware.getLeftDrive().getPosition() +
//					 * "\t Encoder Right: " + TKOHardware.getRightDrive().getPosition() + "\t Left Setpoint: " +
//					 * TKOHardware.getLeftDrive().getSetpoint());
//					 */
//					Timer.delay(0.001);
//				}
//			}
//			
//
//			TKOHardware.getLeftDrive().set(distance);
//			System.out.println("Left has been set to: " + distance + " and is currently at: " + TKOHardware.getLeftDrive().getPosition());
//			TKOHardware.getRightDrive().set(distance);
//
//			double diff = Math.abs(TKOHardware.getLeftDrive().getPosition() - distance);
//			while (diff > threshold && DriverStation.getInstance().isEnabled() && TKOHardware.getLeftDrive().getOutputCurrent() < Definitions.GEAR_AUTON_CURRENT)
//			{
//				/*
//				 * TKOLogger.getInstance().addMessage("NOT CLOSE ENOUGH TO TARGET DIST: " + diff);
//				 * System.out.println("NOT CLOSE ENOUGH TO TARGET DIST: " + diff + "Right Get at: " +
//				 * TKOHardware.getRightDrive().getPosition());
//				 */
//				System.out.println("Left Position: " + TKOHardware.getLeftDrive().getPosition() + " Left Setpoint: " + TKOHardware.getLeftDrive().getSetpoint() + " Left Error: " + TKOHardware.getLeftDrive().getError());
//				System.out.println("Right Position: " + TKOHardware.getRightDrive().getPosition() + " Right Setpoint: " + TKOHardware.getRightDrive().getSetpoint() + " Right Error: " + TKOHardware.getRightDrive().getError());
//				diff = Math.abs(TKOHardware.getLeftDrive().getPosition() - distance);
//				Timer.delay(0.05);
//			}
//			
//			TKOHardware.getDSolenoid(1).set(Definitions.GEAR_DOOR_DOWN);
//			TKOHardware.getDSolenoid(2).set(Definitions.GEAR_ARMS_OUT);
//			
//			Timer.delay(1);
//
//			System.out.println("Setpoint at end of atom: " + TKOHardware.getLeftDrive().getSetpoint());
//			System.out.println(
//					"Position at end of atom: " + TKOHardware.getLeftDrive().getPosition() + "\t" + TKOHardware.getRightDrive().get());
//
//			// TKOHardware.getLeftDrive().disableControl();
//			// TKOHardware.getRightDrive().disableControl();
//
//			TKOHardware.getLeftDrive().enableBrakeMode(true);
//			TKOHardware.getRightDrive().enableBrakeMode(true);
//
//			try
//			{
//				if (setting == 2)
//				{
//					TKOHardware.getDSolenoid(0).set(Definitions.SHIFTER_HIGH);
//					TKOHardware.getDSolenoid(2).set(Value.kForward);
//					TKOHardware.getDSolenoid(1).set(Value.kForward);
//					Timer.delay(0.5);
//				}
//			}
//			catch (TKOException e1)
//			{
//				e1.printStackTrace();
//			}
//		
//		}
//		catch (TKOException e1)
//		{
//			e1.printStackTrace();
//		}
//		System.out.println("Done executing drive atom");
//	}
//
//}
