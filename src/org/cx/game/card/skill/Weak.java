package org.cx.game.card.skill;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.IBuff;
import org.cx.game.card.skill.IMagic;
import org.cx.game.card.skill.PassiveSkill;

/**
 * 虚弱
 * @author chenxian
 *
 */
public class Weak extends PassiveSkill {

	private Integer bout = 0;
	private Integer chance = 0;
	private Integer downScale = 0;
	private LifeCard attacked = null;
	
	/**
	 * 
	 * @param bout 回合
	 * @param downScale 攻击下降比例
	 * @param chance 触发几率
	 */
	public Weak(Integer bout, Integer downScale, Integer chance) {
		super(IMagic.Style_Magic);
		// TODO Auto-generated constructor stub
		this.chance = chance;
		this.bout = bout;
		this.downScale = downScale;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);

		new WeakBuff(bout, IMagic.Style_Magic, IBuff.Type_Harm, IMagic.Func_SustainedHarm, downScale, this.attacked).effect();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		this.attacked = (LifeCard) ((Object[]) args[0])[0];
		if (Random.isTrigger(chance)) {
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
