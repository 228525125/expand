package org.cx.game.card.skill;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.DizzyBuff;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.core.Context;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.observer.NotifyInfo;

/**
 * 击晕 attack （没有使用）
 * @author chenxian
 *
 */
public class AttackDizzy extends PassiveSkill {

	private Integer chance;
	private LifeCard attacked;
	private Integer bout;
	
	public AttackDizzy(Integer style, Integer bout, Integer chance) {
		// TODO Auto-generated constructor stub
		super(style);
		this.chance = chance;
	}
	
	public Integer getChance() {
		return chance;
	}
	
	public void setChance(Integer chance) {
		this.chance = chance;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		new DizzyBuff(bout,attacked).effect();
		
		super.affect(objects);
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		attacked = (LifeCard) args[1];
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
