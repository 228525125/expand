package org.cx.game.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.Ground;

public class DoneState extends AbstractPlayState {

	@Override
	public void deploy() {
		// TODO Auto-generated method stub
		getContext().setPlayState(new DeployState());
		getContext().deploy();
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		Player curPlayer = getContext().getControlPlayer();
		Ground ground = getContext().getGround();
		for(Corps corps : ground.getCorpsList(curPlayer)){
			Corps sc = (Corps) corps;
			if(sc.getActivate().getActivation().equals(true))
				sc.activate(false);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("control", curPlayer);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Context_Done,map);
		super.notifyObservers(info);

		getContext().switchControl();                   //转换控制权
		
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
	
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return (Context) super.getContext();
	}

}
