package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.skill.IBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 冰箭
 * @author chenxian
 *
 */
public class IceArrow extends ActiveSkill {

	private Integer bout;
	private Integer attackScale;
	private Integer energyScale;
	private Integer speedScale;
	
	/**
	 * 
	 * @param consume
	 * @param cooldown
	 * @param velocity
	 * @param style
	 * @param type
	 * @param bout  冻结回合
	 * @param attackScale 普通攻击的比例
	 * @param energyScale 移动范围下降比例
	 * @param speedScale 攻击速度下降比例
	 * @param life
	 */
	public IceArrow(Integer consume, Integer cooldown, Integer velocity,
			Integer style, Integer func, Integer bout, Integer attackScale, Integer energyScale, Integer speedScale) {
		super(consume, cooldown, velocity, style, func);
		// TODO Auto-generated constructor stub
		this.bout = bout;
		this.attackScale = attackScale;
		this.energyScale = energyScale;
		this.speedScale = speedScale;
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return getOwner().getAttack().getRange();
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard affected = (LifeCard) objects[0];
		Integer atk = getOwner().getAttack().getAtk();
		affected.getDeath().magicToHp(-atk*attackScale/100);
		new FreezeBuff(bout,getStyle(),IBuff.Type_Harm, getFunc(),0,energyScale,speedScale,affected).effect();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		LifeCard affected = (LifeCard) objects[0];
		affected.affected(this);
	}

}
