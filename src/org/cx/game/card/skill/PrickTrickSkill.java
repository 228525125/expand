package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.trick.ITrick;
import org.cx.game.card.trick.PrickTrick;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IPlace;

/**
 * 地刺
 * @author chenxian
 *
 */
public class PrickTrickSkill extends ActiveSkill {
	
	private Integer maimedBout = 0;
	private Integer damageScale = 0;
	private Integer boutDamageScale = 0;
	private Integer energyDownScale = 0;
	
	/**
	 * 
	 * @param consume               消耗
	 * @param cooldown              冷却
	 * @param velocity              蓄力
	 * @param style                 
	 * @param func
	 * @param maimedBout            残废回合
	 * @param damageScale           首次触发陷阱，受到伤害的比例，以总生命为基准
	 * @param boutDamageScale       每回合受到伤害的比例，以总生命为基准
	 * @param energyDownScale       移动力下降比例，以总移动力为基准
	 */
	public PrickTrickSkill(Integer consume, Integer cooldown, Integer velocity,
			Integer style, Integer func, Integer maimedBout, Integer damageScale, Integer boutDamageScale, Integer energyDownScale) {
		super(consume, cooldown, velocity, style, func);
		// TODO Auto-generated constructor stub
		this.damageScale = damageScale;
		this.maimedBout = maimedBout;
		this.boutDamageScale = boutDamageScale;
		this.energyDownScale = energyDownScale;
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return ITrick.Setup_Range;
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		affect(objects);
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IPlace place = (IPlace) objects[0];
		ITrick trick = new PrickTrick(ITrick.Setup_Bout,maimedBout, damageScale, boutDamageScale, energyDownScale,place,getOwner().getPlayer());
	}

}
