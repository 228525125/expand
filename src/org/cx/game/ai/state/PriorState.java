package org.cx.game.ai.state;

import org.cx.game.ai.CorpsAgent;
import org.cx.game.ai.Telegram;

/**
 * 优先状态，这种状态一般在满足条件后直接进入，例如，逃跑；
 * 同时也是消息路由器
 * @author chenxian
 *
 */
public class PriorState extends AbstractState<CorpsAgent> {

	
	private static PriorState state = null;
	
	public static PriorState getInstance(CorpsAgent owner){
		if(null==state)
			state = new PriorState();
		return state;
	}

	@Override
	public void execute(CorpsAgent t) {
		// TODO Auto-generated method stub
		if(!t.isWeak()){
			t.changeState(FleeState.getInstance());
			return ;
		}
		
		if(null!=t.getAttackTarget()){
			t.changeState(AttackState.getInstance());
		}
	}

	@Override
	public void enter(CorpsAgent t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit(CorpsAgent t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean onMessage(CorpsAgent t, Telegram msg) {
		// TODO Auto-generated method stub
		Boolean ret = false;
		
		switch (msg.getMessageType()) {
		case 8001:         //Telegram.MessageType_Fight
			t.changeState(AttackState.getInstance());
			ret = true;
			break;
		
		case 8003:         //Telegram.MessageType_Guard
			if(t.isGuardPosition())
				t.changeState(StandState.getInstance());
			else
				t.changeState(RunbackState.getInstance());
			ret = true;
			break;
			
		default:
			break;
		}
		return ret;
	}

}
