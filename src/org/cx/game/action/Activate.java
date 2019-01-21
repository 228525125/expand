package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Util;

public class Activate extends AbstractAction implements IAction {

	//public static final Integer ActivationConsume = 100;
	
	private Boolean activation = false;
	private Integer speed = 100;
	//private Integer vigour = 0;       //活力，活力为100时，可以行动一次，活力值越多，行动次数越多
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	public Boolean getActivation() {
		// TODO Auto-generated method stub
		return this.activation;
	}

	public void setActivation(Boolean activation) {
		// TODO Auto-generated method stub
		this.activation = activation;
	}
	
	public Integer getSpeed() {
		// TODO Auto-generated method stub
		return this.speed;
	}
	
	public void setSpeed(Integer speed) {
		// TODO Auto-generated method stub
		this.speed = speed;
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		
		Boolean activate = (Boolean) objects[0];
		
		setActivation(activate);
		
		Corps owner = getOwner();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("corps", owner);
		map.put("position", owner.getPosition());
		map.put("activate", activate);
		String desc = getOwner().getName()+" 【激活】 ；";
		map.put("description", desc);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Activate,map);
		notifyObservers(info);
	}
}
