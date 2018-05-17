package org.cx.game.ai.state;

import org.cx.game.ai.CorpsAgent;
import org.cx.game.ai.MessageDispatcher;
import org.cx.game.ai.TeamAgent;
import org.cx.game.ai.Telegram;

/**
 * 防守待命状态，当威胁解除，进入该状态，所有队员将返回防御地点待命；
 * @author chenxian
 *
 */
public class GuardState extends AbstractState<TeamAgent> {

	private static GuardState state = null;
	
	private GuardState() {
		// TODO Auto-generated constructor stub
	}
	
	public static GuardState getInstance(){
		if(null==state)
			state = new GuardState();
		
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
		case 8001:                 //Telegram.MessageType_Fight
			for(CorpsAgent corpsAgent : t.getExecutorList()){
				MessageDispatcher.getInstance().senderMessage(t, corpsAgent, Telegram.MessageType_Fight, null);
			}
			t.changeState(FightState.getInstance());
			ret = true;
			break;

		default:
			break;
		}
		
		return ret;
	}
}
