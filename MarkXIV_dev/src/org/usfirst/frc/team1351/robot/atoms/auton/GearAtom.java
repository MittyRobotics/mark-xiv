package org.usfirst.frc.team1351.robot.atoms.auton;

import java.sql.Time;

import org.usfirst.frc.team1351.robot.Definitions;
import org.usfirst.frc.team1351.robot.atoms.Atom;
import org.usfirst.frc.team1351.robot.util.TKOException;
import org.usfirst.frc.team1351.robot.util.TKOHardware;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class GearAtom extends Atom
{

	public GearAtom()
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
			TKOHardware.getDSolenoid(0).set(Definitions.SHIFTER_LOW);
			TKOHardware.getDSolenoid(1).set(Definitions.GEAR_DOOR_DOWN);
			TKOHardware.getDSolenoid(2).set(Definitions.GEAR_ARMS_OUT);
			Timer.delay(1);
		}
		
		catch (TKOException e1)
		{
			e1.printStackTrace();
		}
	}
	
	

}
