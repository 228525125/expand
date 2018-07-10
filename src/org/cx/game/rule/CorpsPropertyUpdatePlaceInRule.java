package org.cx.game.rule;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.LandformEffect;
import org.cx.game.widget.Place.PlaceInAction;

public class CorpsPropertyUpdatePlaceInRule extends AbstractRule {

	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return PlaceInAction.class;
	}
	
	@Override
	public PlaceInAction getOwner() {
		// TODO Auto-generated method stub
		return (PlaceInAction) super.getOwner();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Corps corps = (Corps) ((Object[]) args[0])[0];
		Integer landform = getOwner().getOwner().getLandform();
		updateLandformAtk(corps, landform);
		updateLandformDef(corps, landform);
	}
	
	private void updateLandformAtk(Corps corps, Integer landform) {
		Integer profession = corps.queryTagForCategory(CommonIdentifierE.Profession).get(0);
		corps.getAttack().setLandformAtk(corps.getAtk()*LandformEffect.getAttackAdvantage(profession, landform)/100);
		
		corps.updateAtk();
	}
	
	private void updateLandformDef(Corps corps, Integer landform) {
		Integer profession = corps.queryTagForCategory(CommonIdentifierE.Profession).get(0);
		corps.getAttacked().setLandformDef(corps.getDef()*LandformEffect.getDefendAdvantage(profession, landform)/100);
		
		corps.updateDef();
	}

}
