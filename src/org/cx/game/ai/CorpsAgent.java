package org.cx.game.ai;

import org.cx.game.ai.state.AbstractState;
import org.cx.game.ai.state.PriorState;
import org.cx.game.ai.state.StandState;
import org.cx.game.corps.Corps;

/**
 * 执行者，分解计划为各种操控行为，该环节负责计算各种数据来满足操控行为的执行；
 * 执行者可以通过MessageDispatcher来与决策者通讯；
 * @author chenxian
 *
 */
public class CorpsAgent extends AbstractAgent<CorpsAgent> {

	private Integer guardPosition = null;       //初始位置
	
	private Corps owner = null;
	private TeamAgent team = null;
	private StateMachine<CorpsAgent> stateMachine = null;
	
	public CorpsAgent(Integer id, Integer guardPosition, TeamAgent team, Corps owner) {
		super(id);
		// TODO Auto-generated constructor stub
		this.guardPosition = guardPosition;
		
		this.owner = owner;
		this.team = team;
		this.team.addExecutor(this);
		
		this.stateMachine = new StateMachine<CorpsAgent>(this);
		this.stateMachine.setPriorState(PriorState.getInstance(this));
		this.stateMachine.setCurrentState(StandState.getInstance());
	}
	
	/**
	 * 是否在指定的防守位置
	 * @return
	 */
	public Boolean isGuardPosition(){
		return this.guardPosition.equals(getOwner().getPosition());
	}
	
	/**
	 * 虚弱
	 * @return
	 */
	public Boolean isWeak(){
		return false;
	}
	
	@Override
	public StateMachine<CorpsAgent> getStateMachine() {
		// TODO Auto-generated method stub
		return this.stateMachine;
	}
	
	public Corps getOwner() {
		return owner;
	}
	
	public TeamAgent getTeam() {
		return team;
	}
	
	public Integer getGuardPosition() {
		return guardPosition;
	}
	
	/**
	 * 获取有效目标，默认最近的
	 * @return
	 */
	public Corps getAttackTarget() {
		return this.team.getNearByEnemy(getOwner().getPosition());
	}
	
	public AbstractState<CorpsAgent> getCurrentState(){
		return this.stateMachine.getCurrentState();
	}
	
	/**
	 * 销毁
	 * @return
	 */
	public Boolean destroy(){
		return this.team.removeExecutor(this);
	}

}
