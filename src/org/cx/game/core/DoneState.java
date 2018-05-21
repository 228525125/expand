package org.cx.game.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;

public class DoneState extends AbstractPlayState {

	@Override
	public void deploy() {
		// TODO Auto-generated method stub
		context.setPlayState(context.getDeployState());
		context.deploy();
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		Player curPlayer = (Player) context.getControlPlayer();
		List<Corps> list = curPlayer.getAttendantList(true);
		for(AbstractCorps corps : list){
			corps.activate(false);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("control", curPlayer);
		NotifyInfo info = new NotifyInfo(NotifyInfo.Context_Done,map);
		super.notifyObservers(info);

		context.switchControl();                   //转换控制权
		
		deploy();                //部署
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

}
