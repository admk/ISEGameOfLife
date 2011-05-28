package ise.gameoflife.enviroment;

import ise.gameoflife.AbstractAgent;
import ise.gameoflife.enviroment.actionhandlers.HuntHandler;
import ise.gameoflife.tokens.RegistrationRequest;
import ise.gameoflife.tokens.RegistrationResponse;
import java.util.UUID;
import org.simpleframework.xml.Element;
import presage.Action;
import presage.EnvDataModel;
import presage.EnvironmentConnector;
import presage.Input;
import presage.Participant;
import presage.Simulation;
import presage.environment.AbstractEnvironment;
import presage.environment.messages.ENVDeRegisterRequest;
import presage.environment.messages.ENVRegisterRequest;
import presage.environment.messages.ENVRegistrationResponse;

/**
 * The primary environment code for the GameOfLife that we define. This will
 * contain a list of all the groups, food etc. etc. that exist in
 * @author Benedict Harcourt
 */
public class Environment extends AbstractEnvironment
{

	static public abstract class ActionHandler implements AbstractEnvironment.ActionHandler
	{
		@Override	abstract public boolean canHandle(Action action);
		@Override	abstract public Input handle(Action action, String actorID);
	}
	
	@Element
	protected EnvironmentDataModel dmodel;

	/**
	 * A reference to the simulation that we're part of, for the purpose of
	 * adding participants etc.
	 */
	protected Simulation sim;
	
	@Deprecated
	public Environment()
	{
		super();
	}
	
	@presage.annotations.EnvironmentConstructor( { "queueactions",
			"randomseed", "dmodel" })
	public Environment(boolean queueactions, long randomseed,
			EnvironmentDataModel dmodel) {
		super(queueactions, randomseed);

		// Separation of data from code!
		this.dmodel = dmodel;
	}

	@Override
	public boolean deregister(ENVDeRegisterRequest deregistrationObject)
	{
		if (!authenticator.get(deregistrationObject.getParticipantID()).equals(deregistrationObject.getParticipantAuthCode()))
		{
			return false;
		}
		return dmodel.removeParticipant(deregistrationObject.getParticipantID());
	}

	@Override
	public ENVRegistrationResponse onRegister(ENVRegisterRequest registrationObject)
	{
		final RegistrationRequest obj = (RegistrationRequest)registrationObject;
		if (!dmodel.registerParticipant(obj)) return null;
		return new RegistrationResponse(obj.getParticipantID(), UUID.randomUUID(), new EnvConnector(this));
	}

	@Override
	public EnvDataModel getDataModel()
	{
		return dmodel;
	}

	@Override
	protected void onInitialise(Simulation sim)
	{
		this.sim = sim;
		// TODO: Add message handlers
		this.actionhandlers.add(new HuntHandler(this));
	}

	@Override
	protected void updatePerceptions()
	{
		// FIXME: Write this function
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected void updatePhysicalWorld()
	{
		for (Participant agent : sim.players.values())
		{
			if (sim.isParticipantActive(agent.getId()))
			{
				if (agent instanceof ise.gameoflife.AbstractAgent)
				{
					// TODO: Put a consume food result here when this is complete
					agent.enqueueInput((Input)null);
				}
			}
		}
		// FIXME: Write this function
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected void updateNetwork()
	{
		// FIXME: Write this function
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void setTime(long cycle)
	{
		this.dmodel.setTime(cycle);
	}
	
}