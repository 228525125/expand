package org.cx.game.ai.state;

import org.cx.game.ai.CorpsAgent;
import org.cx.game.ai.MessageDispatcher;
import org.cx.game.ai.TeamAgent;
import org.cx.game.ai.Telegram;

/**
 * 战斗状态，当团队成员受到威胁，进入该状态；
 * @author chenxian
 *
 */
public class FightState extends AbstractState<TeamAgent> {

	private static FightState state = null;
	
	private FightState() {
		// TODO Auto-generated constructor stub
	}
	
	public static FightState getInstance(){
		if(null==state)
			state = new FightState();
		
		return state;
	}
	
	@Override
	public void execute(TeamAgent t) {
		// TODO Auto-generated method stub
		t.updateExecutorState();
	}
	
	@Override
	public Boolean onMessage(TeamAgent t, Telegram msg) {
		// TODO Auto-generated method stub
		Boolean ret = false;
		switch (msg.getMessageType()) {
		case 8002:           //Telegram.MessageType_Fight_Finish
			for(CorpsAgent corpsAgent : t.getExecutorList()){
				MessageDispatcher.getInstance().senderMessage(t, corpsAgent, Telegram.MessageType_Guard, null);
			}
			t.changeState(GuardState.getInstance());
			ret = true;
			break;

		default:
			break;
		}
		
		return ret;
	}
}
