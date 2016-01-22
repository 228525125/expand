package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Trick;
import org.cx.game.core.IPlayer;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.widget.IPlace;

/**
 * 冷冻陷阱
 * @author chenxian
 *
 */
public class FreezeTrick extends Trick {

	private Integer freezeBout;
	private Integer damageScale;
	private Integer energyDownScale;  
	private Integer speedDownScale;
	
	/**
	 * 
	 * @param bout
	 * @param style
	 * @param type
	 * @param func
	 * @param freezeBout 冷冻效果持续回合
	 * @param damageScale 直接伤害为受伤者总生命值的百分比
	 * @param energyDownScale 移动范围降低比例
	 * @param speedDownScale 攻击速度降低比例
	 * @param place
	 * @param player
	 */
	public FreezeTrick(Integer bout, Integer style, Integer type, Integer func, Integer freezeBout, Integer damageScale, Integer energyDownScale, Integer speedDownScale,
			IPlace place, IPlayer player) {
		super(bout, style, type, func, place, player);
		// TODO Auto-generated constructor stub
		this.freezeBout = freezeBout;
		this.damageScale = damageScale;
		this.energyDownScale = energyDownScale;
		this.speedDownScale = speedDownScale;
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		new FreezeBuff(freezeBout,getStyle(),getType(),getFunc(),life.getHp()*damageScale/100,energyDownScale,speedDownScale,life).effect();
	}

}
