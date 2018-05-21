package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.treasure.ITreasure;
import org.cx.game.widget.treasure.Treasure;

public class Pick extends AbstractAction implements IAction {

	public final static Integer Pick_Range_Defautl = 1;
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		
		ITreasure treasure = (ITreasure) objects[0];
		
		/*
		 * 消耗精力
		 */
		Integer energy = getOwner().getMove().getEnergy();
		getOwner().getMove().setEnergy(--energy);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("card", getOwner());
		map.put("treasure", treasure);
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(NotifyInfo.Corps_Pick,map);
		super.notifyObservers(info);
		
		treasure.picked(getOwner());
	}
}
