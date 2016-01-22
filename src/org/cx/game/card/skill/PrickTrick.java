package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Trick;
import org.cx.game.core.IPlayer;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IPlace;

/**
 * 地刺陷阱
 * @author chenxian
 *
 */
public class PrickTrick extends Trick {

	private Integer maimedBout = 0;
	private Integer downMoveScale = 0;
	
	public PrickTrick(Integer bout, Integer style, Integer type, Integer func, Integer maimedBout, Integer downMoveScale,
			IPlace place, IPlayer player) {
		super(bout, style, type, func, place, player);
		// TODO Auto-generated constructor stub
		this.downMoveScale = downMoveScale;
		this.maimedBout = maimedBout;
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		new MaimedBuff(getBout(),getStyle(),getType(),getFunc(),downMoveScale,life);
	}

}
