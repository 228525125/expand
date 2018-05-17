package org.cx.game.ai.state;

import org.cx.game.ai.PlayerAgent;
import org.cx.game.ai.policy.DonePolicy;

/**
 * 等待状态，当操作完毕，进入该状态；
 * @author chenxian
 *
 */
public class WaitState extends AbstractState<PlayerAgent> {

	private static WaitState state = null;
	
	private WaitState() {
		// TODO Auto-generated constructor stub
		addPolicy(DonePolicy.getInstance());
	}
	
	public static WaitState getInstance(){
		if(null==state)
			state = new WaitState();
		
		return state;
	}
}
