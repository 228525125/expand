package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Hero;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.Place;

public class Leave extends AbstractAction implements IAction {

	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		Place place = (Place) objects[0];

		Hero hero = ((Merge) getOwner().getMerge()).getHero();
		hero.removeCorpsFromTroops(getOwner());
		
		AbstractGround ground = getOwner().getGround();
		ground.placementCorps(place.getPosition(), getOwner());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("corps", getOwner());
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Leave,map);
		super.notifyObservers(info);
		
		getOwner().activate(false);
	}

}
