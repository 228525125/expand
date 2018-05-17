package org.cx.game.ai;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.core.Player;

public class PlayerAgent extends AbstractAgent<PlayerAgent> {

	private Player owner = null;
	private StateMachine<PlayerAgent> stateMachine = null;
	
	private List<AbstractAgent> agentList = new ArrayList<AbstractAgent>();
	
	public PlayerAgent(Integer id, Player owner) {
		super(id);
		// TODO Auto-generated constructor stub
		this.owner = owner;
		this.stateMachine = new StateMachine<PlayerAgent>(this);
	}
	
	public void updateAgentState(){
		AbstractAgent agent = getAllowUpdateAgent();
		while (null!=agent) {
			agent.update();			
			agent = getAllowUpdateAgent();
		}
	}
	
	@Override
	public Boolean isUpdateComplete() {
		// TODO Auto-generated method stub
		return super.isUpdateComplete() && null==getAllowUpdateAgent();
	}
	
	@Override
	public StateMachine<PlayerAgent> getStateMachine() {
		// TODO Auto-generated method stub
		return this.stateMachine;
	}
	
	public Player getOwner(){
		return this.owner;
	}
	
	/**
	 * 智能体、团队等
	 * @return
	 */
	public List<AbstractAgent> getAgentList() {
		return agentList;
	}
	
	public void addAgent(AbstractAgent agent){
		this.agentList.add(agent);
	}
	
	public Boolean removeAgent(AbstractAgent agent){
		return this.agentList.remove(agent);
	}
	
	private AbstractAgent getAllowUpdateAgent() {
		for(AbstractAgent agent : getAgentList()){
			if(!agent.isUpdateComplete())
				return agent;
		}
		return null;
	}

}
