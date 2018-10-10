package org.usfirst.frc.team1351.robot.atoms.auton;

import org.usfirst.frc.team1351.robot.Definitions;
import org.usfirst.frc.team1351.robot.atoms.Atom;
import org.usfirst.frc.team1351.robot.util.TKOException;
import org.usfirst.frc.team1351.robot.util.TKOHardware;

import edu.wpi.first.wpilibj.Timer;

public class ShootAtom extends Atom
{

	public ShootAtom()
	{
	}

	@Override
	public void init()
	{
		System.out.println("Gear atom initialized");
	}

	@Override
	public void execute()
	{
		try
		{
			TKOHardware.getShooterTalon().enable();
			TKOHardware.getShooterTalon().set(0.1f * Definitions.SHOOTER_SETSPEED);
			Timer.delay(1); 
			TKOHardware.getBallFeederTalon().set(1);
			Timer.delay(5);
			TKOHardware.getShooterTalon().disable();
			TKOHardware.getBallFeederTalon().set(0);
		}
		
		catch (TKOException e1)
		{
			e1.printStackTrace();
		}
	}
	
	

}
