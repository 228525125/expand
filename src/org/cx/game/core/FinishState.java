package org.cx.game.core;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

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
		/*for(AbstractPlayer player : context.getPlayerList()){
			player.getCommandBuffer().clear();
		}*/
		Map<String,Object> map = new HashMap<String,Object>();
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Context_Finish,map);
		super.notifyObservers(info);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

}
