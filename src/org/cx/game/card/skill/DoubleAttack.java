package org.cx.game.card.skill;

import java.util.List;

import org.cx.game.action.Attack;
import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IInterceptable;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.observer.NotifyInfo;

/**
 * 连击 attack
 * @author chenxian
 *
 */
public class DoubleAttack extends PassiveSkill {

	private Integer chance;
	private LifeCard attacked;
	
	public DoubleAttack(Integer style, Integer chance) {
		// TODO Auto-generated constructor stub
		super(style);
		this.chance = chance;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		Integer doubleAttackChance = chance;
		this.chance = 0;                //确保连击后不会再出现连击
		try {
			getOwner().attack(attacked);
		} catch (RuleValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.chance = doubleAttackChance;
		
		super.affect(objects);
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		if(Random.isTrigger(chance)){            //连击
			this.attacked = (LifeCard) args[1];
			affect();
		}
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getAttack().addIntercepter(this);
	}
}
