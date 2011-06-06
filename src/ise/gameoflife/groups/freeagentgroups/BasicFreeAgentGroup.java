package ise.gameoflife.groups.freeagentgroups;

import ise.gameoflife.models.HuntingTeam;
import ise.gameoflife.participants.AbstractFreeAgentGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Benedict
 */
public class BasicFreeAgentGroup extends AbstractFreeAgentGroup
{
	private Comparator<String> c = new Comparator<String>() {
			private Random r = new Random();
			@Override
			public int compare(String o1, String o2)
			{
				return (r.nextBoolean() ? -1 : 1);
			}
		};

	@Override
	public List<HuntingTeam> selectTeams(List<String> freeAgents)
	{
		ArrayList<HuntingTeam> teams = new ArrayList<HuntingTeam>();
		List<String> agents = new ArrayList<String>(freeAgents);
		Collections.sort(agents, c);

		int count = agents.size();
                
                for(int i=0; i < count; i += 2){
			int ubound = (i + 2 >= count) ? count : i + 2;
			teams.add(new HuntingTeam (agents.subList(i, ubound)));
		}

		return teams;
	}
	
}
