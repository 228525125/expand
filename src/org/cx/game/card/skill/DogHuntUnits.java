package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.IMagic;

/**
 * 狗头人围猎
 * @author chenxian
 *
 */
public class DogHuntUnits extends HuntUnits {

	private Integer defScale = 0;
	private Integer upImmuneDamageRatio = 0;
	
	public DogHuntUnits(Integer range, Integer defScale) {
		super(IMagic.Style_physical, range);
		// TODO Auto-generated constructor stub
		this.defScale = defScale;
	}
	
	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		affect();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		affect();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Integer immuneDamageRatio = getOwner().getAttacked().getImmuneDamageRatio();
		getOwner().getAttacked().setImmuneDamageRatio(immuneDamageRatio - upImmuneDamageRatio);
		
		upImmuneDamageRatio = defScale*getUnitNumber();
		getOwner().getAttacked().addToImmuneDamageRatio(upImmuneDamageRatio);
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getAttacked().addIntercepter(this);
		life.getMove().addIntercepter(this);
	}
}
