package org.cx.game.ai.state;

import org.cx.game.ai.CorpsAgent;
import org.cx.game.ai.MessageDispatcher;
import org.cx.game.ai.Telegram;
import org.cx.game.ai.policy.AttackPolicy;
import org.cx.game.ai.policy.NearTargetForAttackPolicy;

/**
 * 攻击状态，当智能体有攻击目标的时候，进入该状态，如果是英雄还应该包含战斗策略；
 * @author chenxian
 *
 */
public class AttackState extends AbstractState<CorpsAgent> {

	private static AttackState state = null;
	
	private AttackState() {
		// TODO Auto-generated constructor stub
		addPolicy(AttackPolicy.getInstance());
		addPolicy(NearTargetForAttackPolicy.getInstance());
	}
	
	public static AttackState getInstance(){
		if(null==state)
			state = new AttackState();
		
		return state;
	}
	
	@Override
	public void execute(CorpsAgent t) {
		// TODO Auto-generated method stub
		super.execute(t);
		
		/*
		 * 如果攻击目标不存在了，就汇报战斗结束
		 */
		if(null==t.getAttackTarget()){
			MessageDispatcher.getInstance().senderMessage(t, t.getTeam(), Telegram.MessageType_Fight_Finish, null);
		}
	}
}
