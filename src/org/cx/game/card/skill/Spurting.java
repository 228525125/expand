package org.cx.game.card.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.exception.RuleValidatorException;

/**
 * 溅射 attack
 * @author chenxian
 *
 */
public abstract class Spurting extends PassiveSkill {

	private Integer atkScale = 0;
	
	public Spurting(Integer atkScale) {
		super();
		// TODO Auto-generated constructor stub
		this.atkScale = atkScale;
	}
	
	public abstract List<LifeCard> getAffectedList();
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		LifeCard life = (LifeCard) objects[0];
		life.getDeath().addToHp(-getOwner().getAttack().getAtk()*atkScale/100);
		
		super.affect(objects);
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		List<LifeCard> list = getAffectedList();
		for(LifeCard life : list){
			try {
				life.affected(this);
			} catch (RuleValidatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getAttack().addIntercepter(this);
	}

}
