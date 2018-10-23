package org.cx.game.rule;

import org.cx.game.action.Picked;
import org.cx.game.corps.Corps;
import org.cx.game.corps.Hero;
import org.cx.game.widget.treasure.Treasure;
import org.cx.game.widget.treasure.EquipmentTreasure;
import org.cx.game.widget.treasure.EquipmentTreasure.TreasureEquipmentPicked;

public class CorpsPropertyUpdateTreasureEquipmentPickedRule extends AbstractRule {

	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return TreasureEquipmentPicked.class;
	}
	
	@Override
	public TreasureEquipmentPicked getOwner() {
		// TODO Auto-generated method stub
		return (TreasureEquipmentPicked) super.getOwner();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Hero hero = (Hero) ((Object[]) args[0])[0];
		updateWeaponAtk(hero);
		updateArmourDef(hero);
	}
	
	private void updateWeaponAtk(Hero hero) {
		Integer atk = 0;
		for(Treasure treasure : hero.getTreasures()){
			if (treasure instanceof EquipmentTreasure) {
				EquipmentTreasure te = (EquipmentTreasure) treasure;
				atk += te.getAtk();
			}
		}
		hero.getAttack().setWeaponAtk(atk);
		
		hero.updateAtk();
	}
	
	private void updateArmourDef(Hero hero) {
		// TODO Auto-generated method stub
		Integer def = 0;
		for(Treasure treasure : hero.getTreasures()){
			if (treasure instanceof EquipmentTreasure) {
				EquipmentTreasure te = (EquipmentTreasure) treasure;
				def += te.getDef();
			}
		}
		hero.getDefend().setArmourDef(def);
		
		hero.updateDef();
	}

}
