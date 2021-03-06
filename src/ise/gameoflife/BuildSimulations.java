package ise.gameoflife;

import ise.gameoflife.simulations.AgentsAndGroup;
import ise.gameoflife.simulations.CLIPolitics;
import ise.gameoflife.simulations.DoubleAgent;
import ise.gameoflife.simulations.FreeAgentsTest;
import ise.gameoflife.simulations.Loans;
import ise.gameoflife.simulations.SingleAgent;
import ise.gameoflife.simulations.Politics;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Magic simulation building class
 * Add classes to the simulationClasses const to have them auto-compiled
 * @author Benedict
 */
final class BuildSimulations
{

	private static final Class[] simulationClasses =
	{
		SingleAgent.class,
		DoubleAgent.class,
		AgentsAndGroup.class,
		Politics.class,
		CLIPolitics.class,
		FreeAgentsTest.class,
                Loans.class
	};

	private BuildSimulations()
	{
		// Nothing to see here. Move along, citizen!
	}

	/**
	 * Build the automatically built classes
	 * @param args Command line arguments
	 */
	public static void main(String args[])
	{
		for (Class<?> c : simulationClasses)
		{
			try
			{
				c.newInstance();
			}
			catch (Exception ex)
			{
				Logger.getLogger(BuildSimulations.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
