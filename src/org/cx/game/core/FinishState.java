package org.cx.game.core;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.observer.NotifyInfo;

public class FinishState extends AbstractPlayState {

	@Override
	public void deploy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void done() {
		// TODO Auto-generated method stub

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		for(IPlayer player : context.getPlayerList()){
			player.getCommandBuffer().clear();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		NotifyInfo info = new NotifyInfo(NotifyInfo.Context_Finish,map);
		super.notifyObservers(info);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

}
