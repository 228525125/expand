package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Hero;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.Ground;
import org.cx.game.widget.treasure.Treasure;

public class Picked extends AbstractAction implements IAction {

	@Override
	public Treasure getOwner() {
		// TODO Auto-generated method stub
		return (Treasure) super.getOwner();
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		
		Hero hero = (Hero) objects[0];
		hero.addTreasure(getOwner());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("card", hero);
		map.put("treasure", getOwner());
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Treasure_Picked,map);
		super.notifyObservers(info);
		
		Ground ground = hero.getGround();
		ground.removeTreasure(getOwner());
	}
}
