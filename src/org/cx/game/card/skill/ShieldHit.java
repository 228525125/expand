package org.cx.game.card.skill;

import org.cx.game.action.IAttack;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.DizzyBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IControlQueue;

/**
 * 盾击
 * @author chenxian
 *
 */
public class ShieldHit extends ActiveSkill {

	private Integer atkScale = 0;
	private Integer bout = 0;
	public static final Integer ShieldHit_ID = 10200007;
	
	/**
	 * 
	 * @param consume
	 * @param cooldown
	 * @param applyType
	 * @param atkScale 攻击比例
	 * @param bout 眩晕回合
	 * @param life
	 */
	public ShieldHit(Integer cooldown, Integer atkScale, Integer bout) {
		super(ShieldHit_ID, cooldown);
		// TODO Auto-generated constructor stub
		this.atkScale = atkScale;
		this.bout = bout;
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return getOwner().getAttack().getRange();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		Integer atk = getOwner().getAtk();
		life.getDeath().addToHp(-atk*atkScale/100);
		
		new DizzyBuff(bout, life).effect();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		LifeCard life = (LifeCard) objects[0];
		life.affected(this);
	}
	

}
