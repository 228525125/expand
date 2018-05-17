package org.cx.game.ai.state;

import org.cx.game.ai.CorpsAgent;
import org.cx.game.ai.policy.RunbackPolicy;

/**
 * 回位
 * @author chenxian
 *
 */
public class RunbackState extends AbstractState<CorpsAgent> {

	private static RunbackState state = null;
	
	private RunbackState() {
		// TODO Auto-generated constructor stub
		addPolicy(RunbackPolicy.getInstance());
	}
	
	public static RunbackState getInstance(){
		if(null==state)
			state = new RunbackState();
		
		return state;
	}
	
	@Override
	public void execute(CorpsAgent t) {
		// TODO Auto-generated method stub
		super.execute(t);
		
		/**
		 * 如果已在指定位置，就切换到待命状态
		 */
		if(t.isGuardPosition())
			t.changeState(StandState.getInstance());
	}
}
