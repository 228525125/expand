package org.cx.game.card.skill;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;

/**
 * 穿刺，降低对方的防御力，仅针对本次攻击有效 attack
 * @author chenxian
 *
 */
public class Puncture extends PassiveSkill {

	private Integer chance;       //触发几率
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
	public Puncture(Integer style, Integer chance, Integer downImmuneDamageRatio) {
		// TODO Auto-generated constructor stub
		super(style);
		this.chance = chance;
		this.downScale = downImmuneDamageRatio;
	}
	
	private Integer downImmuneDamageRatioValue = 0;
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		if(attacked.getAttacked().getImmuneDamageRatio()>0){
			Integer immuneDamageRatio = attacked.getAttacked().getImmuneDamageRatio();
			downImmuneDamageRatioValue = immuneDamageRatio*downScale/100;
			attacked.getAttacked().setImmuneDamageRatio(immuneDamageRatio - downImmuneDamageRatioValue);
		}
		super.affect(objects);
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		Integer immuneDamageRatio = attacked.getAttacked().getImmuneDamageRatio();
		attacked.getAttacked().setImmuneDamageRatio(immuneDamageRatio + downImmuneDamageRatioValue);
		downImmuneDamageRatioValue = 0;
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		this.attacked = (LifeCard) ((Object[]) args[0])[0];
		if(Random.isTrigger(chance))
			affect();
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getAttack().addIntercepter(this);
	}
}
