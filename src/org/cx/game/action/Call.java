package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.AbstractPlace;
import org.cx.game.widget.IGround;
import org.cx.game.widget.Place;
import org.cx.game.widget.treasure.IResource;
import org.cx.game.widget.treasure.Resource;

public class Call extends AbstractAction implements ICall {
	
	private IResource consume = new Resource();
	private Integer ration = 1;
	
	private Integer nop = 1;           //人数 
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}

	@Override
	public IResource getConsume() {
		// TODO Auto-generated method stub
		return consume;
	}
	
	public void setConsume(IResource consume) {
		this.consume = consume;
	}
	
	@Override
	public Integer getRation() {
		// TODO Auto-generated method stub
		return this.ration;
	}
	
	@Override
	public void setRation(Integer ration) {
		// TODO Auto-generated method stub
		if(!ration.equals(this.ration)){
			this.ration = ration;
		}
	}
	
	public Integer getNop() {
		return nop;
	}

	public void setNop(Integer nop) {
		if(!nop.equals(this.nop)){
			this.nop = nop;
			
			getOwner().getAttack().updateDmg();
		}
	}
	
	@Override
	public void action(Object...objects) throws RuleValidatorException {
		// TODO Auto-generated method stub

		AbstractPlace place = (Place) objects[0];
		Integer nop = (Integer) objects[1];
		
		/*
		 * 招募人数
		 */
		setNop(nop);
		
		/* 招募的动作应在place_in之前，因为place_in动作与移动时的place_in动作相同 */
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("card", getOwner());
		map.put("position", place.getPosition());
		NotifyInfo info = new NotifyInfo(NotifyInfo.Corps_Call,map);
		super.notifyObservers(info);           //通知所有卡片对象，召唤事件
		
		/*
		 * 加入战场
		 * 因为顺序问题，没有写在Rule中
		 */
		IGround ground = place.getOwner();
		ground.add(place.getPosition(), getOwner());
		
		/*
		 * 刚招募的部队允许反击
		 */
		getOwner().getDeath().setStatus(AbstractCorps.Death_Status_Live);
		getOwner().getAttacked().setFightBack(true);
	}

}
