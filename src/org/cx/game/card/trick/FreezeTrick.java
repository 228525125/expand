package org.cx.game.card.trick;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.FreezeBuff;
import org.cx.game.card.buff.FreezeTrickBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.SimpleTrickBuff;
import org.cx.game.card.magic.IMagic;
import org.cx.game.card.trick.Trick;
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
public class FreezeTrick extends SimpleTrick {
	
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
	public FreezeTrick(Integer bout, Integer effectBout, Integer damageScale, Integer energyDownScale, Integer speedDownScale,
			IPlace place, IPlayer player) {
		super(bout, DEFAULT_TOUCHNUMBEROFTIMES, effectBout, damageScale, 0, energyDownScale, speedDownScale, 0, 0, place, player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SimpleTrickBuff getTrickBuff() {
		// TODO Auto-generated method stub
		return new FreezeTrickBuff();             //这里先随便构造一个buff，SimpleTrick里面才会对buff进行赋值
	}

}
