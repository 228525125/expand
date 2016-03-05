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

	private Integer downMoveScale = 0;
	private Integer maimedBout = 0;
	
	public PrickTrickSkill(Integer consume, Integer cooldown, Integer velocity,
			Integer style, Integer func, Integer maimedBout, Integer downMoveScale) {
		super(consume, cooldown, velocity, style, func);
		// TODO Auto-generated constructor stub
		this.downMoveScale = downMoveScale;
		this.maimedBout = maimedBout;
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
		ITrick trick = new PrickTrick(ITrick.Setup_Bout,getStyle(),IBuff.Type_Harm,getFunc(),maimedBout,downMoveScale,place,getOwner().getPlayer());
		trick.setup();
	}

}
