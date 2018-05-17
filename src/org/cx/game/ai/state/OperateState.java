package org.cx.game.ai.state;

import org.cx.game.ai.PlayerAgent;

/**
 * 控制状态，当Player获得控制权的时候，进入该状态
 * @author chenxian
 *
 */
public class OperateState extends AbstractState<PlayerAgent> {

	private static OperateState state = null;
	
	private OperateState() {
		// TODO Auto-generated constructor stub
	}
	
	public static OperateState getInstance(){
		if(null==state)
			state = new OperateState();
		
		return state;
	}
	
	@Override
	public void execute(PlayerAgent t) {
		// TODO Auto-generated method stub
		t.updateAgentState();
		
		if(t.isUpdateComplete())
			t.changeState(WaitState.getInstance());
	}
}
