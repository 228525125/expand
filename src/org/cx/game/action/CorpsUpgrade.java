package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Util;
import org.cx.game.widget.treasure.EmpiricValue;

public class CorpsUpgrade extends Upgrade implements IAction {
	
	public CorpsUpgrade(Map<Integer, String> requirement) {
		super(requirement);
		// TODO Auto-generated constructor stub
	}

	private EmpiricValue empiricValue = new EmpiricValue(0);          //经验值
	
	public EmpiricValue getEmpiricValue() {
		// TODO Auto-generated method stub
		return this.empiricValue;
	}
	
	public void setEmpiricValue(Integer funType, EmpiricValue empiricValue) {
		this.empiricValue = (EmpiricValue) Util.operating(funType, this.empiricValue, empiricValue);
		
		EmpiricValue req = getRequirement();
		if(getEmpiricValue().getValue()>=req.getValue()){
			action(req);
		}
	}
	
	public void setEmpiricValue(Integer funType, Integer empiricValue) {
		EmpiricValue ev = new EmpiricValue(empiricValue);
		setEmpiricValue(funType, ev);
	}
	
	@Override
	public EmpiricValue getRequirement() {
		// TODO Auto-generated method stub
		return (EmpiricValue) super.getRequirement();
	}
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		super.action(objects);
		
		EmpiricValue emp =  (EmpiricValue) objects[0];
		
		/*
		 * 扣减升级所需经验值
		 */
		setEmpiricValue(Util.Sub, emp);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("position", getOwner().getPosition());
		map.put("card", getOwner());
		map.put("level", getLevel());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Upgrade,map);
		super.notifyObservers(info);
	}

}
