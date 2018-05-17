package org.cx.game.ai.state;

import org.cx.game.ai.CorpsAgent;

/**
 * 逃跑状态
 * @author chenxian
 *
 */
public class FleeState extends AbstractState<CorpsAgent> {

	private static FleeState state = null;
	
	private FleeState() {
		// TODO Auto-generated constructor stub
		//addPolicy();
	}
	
	public static FleeState getInstance(){
		if(null==state)
			state = new FleeState();
		
		return state;
	}
}
