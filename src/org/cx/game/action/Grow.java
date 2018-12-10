package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Logger;

/**
 * 蓄力
 * @author admin
 *
 */
public class Grow extends AbstractAction implements IAction {
	
	private Integer power = 0;        //能量数
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	/**
	 * 积蓄的能量数
	 * @return
	 */
	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		super.action(objects);
		
		this.power++;
		
		getOwner().activate(false);
		
		Logger.debug(this, getOwner().getName()+" "+"完成蓄力; 蓄力后power:"+power+";");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("corps", getOwner());
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Grow,map);
		super.notifyObservers(info);
	}

}
