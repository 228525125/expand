package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;

/**
 * 狗头人围猎
 * @author chenxian
 *
 */
public class DogHuntUnits extends HuntUnits {

	private Integer atkValue = 0;
	
	public DogHuntUnits(Integer range, Integer atkValue) {
		super(range);
		// TODO Auto-generated constructor stub
		this.atkValue = atkValue;
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
	
	private Integer upAtkValue = 0;
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Integer atk = getOwner().getAttack().getAtk();
		getOwner().getAttack().setAtk(atk - this.upAtkValue);
		
		if(0<getUnitNumber()){
			this.upAtkValue = atkValue*getUnitNumber();
			getOwner().getAttack().addToAtk(this.upAtkValue);
		}
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getAttack().addIntercepter(this);
		life.getMove().addIntercepter(this);
	}
}
