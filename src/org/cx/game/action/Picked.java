package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Hero;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IGroundE;
import org.cx.game.widget.treasure.ITreasure;

public class Picked extends AbstractAction implements IAction {

	@Override
	public ITreasure getOwner() {
		// TODO Auto-generated method stub
		return (ITreasure) super.getOwner();
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
		
		IGroundE ground = hero.getGround();
		ground.removeTreasure(getOwner());
	}
}
