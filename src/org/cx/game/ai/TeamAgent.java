package org.cx.game.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cx.game.ai.policy.DistanceComparator;
import org.cx.game.ai.state.GuardState;
import org.cx.game.corps.Corps;

/**
 * 团队，它站在更高的层级使用AI技术；
 * @author chenxian
 *
 */
public class TeamAgent extends AbstractAgent<TeamAgent> {

	private StateMachine<TeamAgent> stateMachine = null;
	
	private List<CorpsAgent> executorList = new ArrayList<CorpsAgent>();
	private List<Corps> enemyList = new ArrayList<Corps>();          //敌人
	
	public TeamAgent(Integer id) {
		super(id);
		// TODO Auto-generated constructor stub
		this.stateMachine = new StateMachine<TeamAgent>(this);
		this.stateMachine.setCurrentState(GuardState.getInstance());
	}
	
	/**
	 * 更新队员的状态
	 */
	public void updateExecutorState(){
		CorpsAgent agent = getAllowUpdateExecutor(); 
		while(null!=agent){
			agent.update();
			agent = getAllowUpdateExecutor();
		}
	}
	
	@Override
	public Boolean isUpdateComplete() {
		// TODO Auto-generated method stub
		return super.isUpdateComplete() && null==getAllowUpdateExecutor();
	}
	
	@Override
	public StateMachine<TeamAgent> getStateMachine() {
		// TODO Auto-generated method stub
		return this.stateMachine;
	}

	/**
	 * 添加队员
	 * @param executor
	 */
	public void addExecutor(CorpsAgent executor){
		this.executorList.add(executor);
	}
	
	/**
	 * 
	 * @return 队员集合
	 */
	public List<CorpsAgent> getExecutorList() {
		return executorList;
	}
	
	/**
	 * 移除队员
	 * @param executor
	 * @return
	 */
	public Boolean removeExecutor(CorpsAgent executor){
		return this.executorList.remove(executor);
	}
	
	public List<Corps> getEnemyList() {
		refurbishEnemyList();
		return enemyList;
	}
	
	public void setEnemyList(List<Corps> enemyList) {
		this.enemyList = enemyList;
	}
	
	public Corps getNearByEnemy(Integer basePoint){
		if(getEnemyList().isEmpty())
			return null;
		
		Collections.sort(getEnemyList(), new DistanceComparator(basePoint));
		return getEnemyList().get(0);
	}
	
	/**
	 * 
	 * @return 操作未完成的队员
	 */
	private CorpsAgent getAllowUpdateExecutor(){
		for(CorpsAgent agent : this.executorList){
			if(!agent.isUpdateComplete())
				return agent;
		}
		return null;
	}
	
	/**
	 * 移除死亡的，脱离战斗且不在管辖区的
	 */
	private void refurbishEnemyList(){
		
	}
}
