package org.cx.game.core;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

public class DeployState extends AbstractPlayState {
	
	@Override
	public void deploy() {
		// TODO Auto-generated method stub
		Player conPlayer = getContext().getControlPlayer();
		Corps conCorps = getContext().getControlCorps();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("player", conPlayer);
		map.put("corps", conCorps);
		map.put("bout", getContext().getGround().getQueue().getBout());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Context_Deploy,map);
		super.notifyObservers(info);
		
		//判断是否电脑玩家，如果是就启动AI
		if(conPlayer.isComputer())
			conPlayer.automation();
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		//操作完毕
		getContext().setPlayState(new DoneState());
		getContext().done();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		//操作中比赛随时可能结束
		getContext().setPlayState(new FinishState());
		getContext().finish();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return (Context) super.getContext();
	}

}
