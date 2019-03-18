package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Hero;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.Ground;
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

		Corps leader = ((Merge) getOwner().getMerge()).getLeader();
		leader.removeCorpsFromTeammateList(getOwner());
		
		Ground ground = leader.getGround();
		ground.placementCorps(place.getPosition(), getOwner());
		ground.getQueue().add(getOwner());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("corps", getOwner());
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Leave,map);
		super.notifyObservers(info);
		
		getOwner().activate(false);
	}

}
