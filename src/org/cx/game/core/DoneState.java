package org.cx.game.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;

public class DoneState extends PlayState {

	@Override
	public void deploy() throws RuleValidatorException {
		// TODO Auto-generated method stub
		context.setPlayState(context.getDeployState());
		context.deploy();
	}

	@Override
	public void done() throws RuleValidatorException {
		// TODO Auto-generated method stub
		IPlayer curPlayer = context.getControlPlayer();
		List<Corps> list = curPlayer.getAttendantList(true);
		for(Corps corps : list){
			try {
				corps.activate(false);
			} catch (RuleValidatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
