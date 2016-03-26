package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.trick.FreezeTrick;
import org.cx.game.card.trick.ITrick;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IPlace;

/**
 * 猎人技能，冷冻陷阱
 * @author chenxian
 *
 */
public class FreezeTrickSkill extends ActiveSkill {

	private Integer freezeBout;
	private Integer damageScale;
	private Integer energyDownScale;  
	private Integer speedDownScale;
	
	/**
	 * 
	 * @param consume
	 * @param cooldown
	 * @param velocity
	 * @param style
	 * @param func
	 * @param freezeBout 冷冻效果持续回合
	 * @param damageScale 直接伤害为受伤者总生命值的百分比
	 * @param energyDownScale 移动范围降低比例
	 * @param speedDownScale 攻击速度降低比例
	 */
	public FreezeTrickSkill(Integer consume, Integer cooldown,
			Integer velocity, Integer style, Integer func,Integer freezeBout, Integer damageScale, Integer energyDownScale, Integer speedDownScale) {
		super(consume, cooldown, velocity, style, func);
		// TODO Auto-generated constructor stub
		this.freezeBout = freezeBout;
		this.damageScale = damageScale;
		this.energyDownScale = energyDownScale;
		this.speedDownScale = speedDownScale;
		setParameterTypeValidator(new Class[]{IPlace.class});
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return ITrick.Setup_Range;
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IPlace place = (IPlace) objects[0];
		ITrick trick = new FreezeTrick(ITrick.Setup_Bout,freezeBout,damageScale,energyDownScale,speedDownScale,place,getOwner().getPlayer());
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		affect(objects);
	}

}
