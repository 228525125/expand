package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Hero;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.Ground;

public class Merge extends AbstractAction implements IAction {

	private Corps leader = null;
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	public Corps getLeader() {
		return this.leader;
	}
	
	public void setLeader(Corps leader) {
		this.leader = leader;
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		Corps corps = (Corps) objects[0];
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("corps", getOwner());
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Merge,map);
		super.notifyObservers(info);
		
		Ground ground = getOwner().getGround();
		
		ground.removeCorps(getOwner());
		ground.getQueue().remove(getOwner());
		leader.addCorpsToTeammateList(getOwner());
		
		getOwner().activate(false);
		this.leader = corps;
	}
	
	

}
