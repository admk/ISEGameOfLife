/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ise.gameoflife.groups;

import ise.gameoflife.inputs.LeaveNotification.Reasons;
import ise.gameoflife.models.HuntingTeam;
import ise.gameoflife.participants.AbstractGroupAgent;
import ise.gameoflife.tokens.AgentType;
import java.util.List;

/**
 *
 * @author george
 */
public class LoansGroup extends AbstractGroupAgent {

    //TODO: 1) Add history of charities/loans.
    //      2) Add history of reserved food
    //      3) Add an abstract inspectOtherGroups() in GroupDataModel. Concrete implementation here.
    // *What if we use public static methods for the histories to keep the framework intact?
    @Override
    protected void onActivate() {
        //Do nothing!
    }

    @Override
    protected boolean respondToJoinRequest(String playerID) {
        //TODO: To keep it simple always accept agents no matter what (Is that ok?) otherwise reuse code
        return true;
    }

    @Override
    protected List<HuntingTeam> selectTeams() {
        //TODO: Reuse code from TestPoliticalGroup
        return null;
    }

    @Override
    protected void onMemberLeave(String playerID, Reasons reason) {
        //TODO: Reuse code from TestPoliticalGroup but it doesn't really matter because we don't care about politics
    }

    @Override
    protected AgentType decideGroupStrategy() {
        //TODO: The panel should make a decision. This decision will determine how much food we will spend this round/
        //The amount spent is taken from the reserve pool. Two possibilities: Either groups go hunting and spend energy = food
        //or spend money for public service (build roads, schools, big pointless sculptures etc)
        return null;
    }

    @Override
    protected void beforeNewRound() {
        //TODO: Reuse code from TestPoliticalGroup
    }

}