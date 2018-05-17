package org.cx.game.ai.state;

import org.cx.game.ai.CorpsAgent;

/**
 * 站在原地发呆，就是什么也不干
 * @author chenxian
 *
 */
public class StandState extends AbstractState<CorpsAgent> {

	private static StandState state = null;
	
	private StandState() {
		// TODO Auto-generated constructor stub
	}
	
	public static StandState getInstance(){
		if(null==state)
			state = new StandState();
		
		return state;
	}
}
