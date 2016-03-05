package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.CureTiredBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.WoundHealingBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.validator.OwnerValidator;

/**
 * 伤口愈合，每回合按一定比例恢复HP，恢复从下一回合开始
 * @author chenxian
 *
 */
public class WoundHealing extends ActiveSkill {

	private Integer renewScale;
	private Integer bout;
	private Integer tireBout;
	
	/**
	 * 
	 * @param consume
	 * @param cooldown
	 * @param applyType
	 * @param style
	 * @param type
	 * @param bout 持续回合
	 * @param renewScale 每回合恢复总生命值比例
	 * @param tireBout 疲劳回合
	 * @param life
	 */
	public WoundHealing(Integer consume, Integer cooldown, Integer velocity, Integer style, Integer func, Integer bout, Integer renewScale, Integer tireBout) {
		super(consume, cooldown, velocity, style, func);
		// TODO Auto-generated constructor stub
		this.renewScale = renewScale;
		this.bout = bout;
		this.tireBout = tireBout;
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
		
		LifeCard life = (LifeCard) objects[0];		
		new WoundHealingBuff(bout,renewScale,getStyle(), IBuff.Type_Benefit, getFunc(), life).effect();
		new CureTiredBuff(tireBout,getStyle(),IBuff.Type_Neutral,getFunc(),life).effect();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		LifeCard life = (LifeCard) objects[0];
		addValidator(new OwnerValidator(life,getOwner().getPlayer(),true));
		
		super.useSkill(objects);
		
		life.affected(this);
	}

}
