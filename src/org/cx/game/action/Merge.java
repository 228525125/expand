package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Hero;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.AbstractGround;

public class Merge extends AbstractAction implements IAction {

	private Hero hero = null;
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	public Hero getHero() {
		return this.hero;
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		Hero hero = (Hero) objects[0];
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("corps", getOwner());
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Merge,map);
		super.notifyObservers(info);
		
		hero.addTroops(getOwner());
		AbstractGround ground = getOwner().getGround();
		ground.removeCorps(getOwner());
		this.hero = hero;
	}

}
