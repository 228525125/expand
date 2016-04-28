package org.cx.game.card.skill;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;

/**
 * 破甲，降低对方的防御力，且是永久降低 attack
 * @author chenxian
 *
 */
public class SunderArmor extends PassiveSkill {

	private Integer chance;
	private LifeCard attacked;
	private Integer downScale;
	
	/**
	 * 
	 * @param style
	 * @param type
	 * @param chance 触发几率
	 * @param downScale 免伤比下降比例
	 * @param life
	 */
	public SunderArmor(Integer chance, Integer downScale) {
		// TODO Auto-generated constructor stub
		super();
		this.chance = chance;
		this.downScale = downScale;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		if(attacked.getAttacked().getImmuneDamageRatio()>0){
			Integer immuneDamageRatio = attacked.getAttacked().getImmuneDamageRatio()*downScale/100;
			attacked.getAttacked().addToImmuneDamageRatio(-immuneDamageRatio);
		}
		super.affect(objects);
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		attacked = (LifeCard) args[1];
		if(Random.isTrigger(chance)){
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
