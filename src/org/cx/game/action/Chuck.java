package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

public class Chuck extends AbstractAction implements IAction {

	public Chuck() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	@Override
	public void action(Object...objects) {
		// TODO Auto-generated method stub
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("card", getOwner());
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Chuck, map);
		super.notifyObservers(info);           //通知所有卡片对象，丢弃事件
	}

}
