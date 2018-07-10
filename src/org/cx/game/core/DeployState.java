package org.cx.game.core;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

public class DeployState extends AbstractPlayState {
	
	@Override
	public void deploy() {
		// TODO Auto-generated method stub
		AbstractPlayer curPlayer = context.getControlPlayer();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("control", curPlayer);
		map.put("bout", context.getBout());
		map.put("day", context.getDay());
		map.put("week", context.getWeek());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Context_Deploy,map);
		super.notifyObservers(info);
		
		//判断是否电脑玩家，如果是就启动AI
		if(curPlayer.isComputer())
			curPlayer.automation();
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		//操作完毕
		context.setPlayState(new DoneState());
		context.done();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		//操作中比赛随时可能结束
		context.setPlayState(new FinishState());
		context.finish();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

}
