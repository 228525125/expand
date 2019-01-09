package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.AbstractPlace;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.Place;
import org.cx.game.widget.treasure.Mineral;
import org.cx.game.widget.treasure.Resource;

public class Call extends AbstractAction implements IAction {
	
	private Mineral consume = new Mineral();
	private Integer ration = 1;
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}

	public Mineral getConsume() {
		// TODO Auto-generated method stub
		return consume;
	}
	
	public void setConsume(Mineral consume) {
		this.consume = consume;
	}
	
	public Integer getRation() {
		// TODO Auto-generated method stub
		return this.ration;
	}
	
	public void setRation(Integer ration) {
		// TODO Auto-generated method stub
		this.ration = ration;
	}
	
	@Override
	public void action(Object...objects) {
		// TODO Auto-generated method stub
		super.action(objects);

		AbstractPlace place = (Place) objects[0];
		
		getOwner().getDeath().setStatus(CommonIdentifierE.Death_Status_Live);
		
		/* 招募的动作应在place_in之前，因为place_in动作与移动时的place_in动作相同 */
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("card", getOwner());
		map.put("position", place.getPosition());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Call,map);
		super.notifyObservers(info);           //通知所有卡片对象，召唤事件
		
		/*
		 * 放置到战场
		 * 因为顺序问题，没有写在Rule中
		 */
		AbstractGround ground = getOwner().getPlayer().getContext().getGround();
		ground.placementCorps(place.getPosition(), getOwner());
		
		getOwner().setPosition(place.getPosition());
		ground.getQueue().add(getOwner());
	}

}
