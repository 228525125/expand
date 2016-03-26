package org.cx.game.card.trick;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.MaimedBuff;
import org.cx.game.card.magic.IMagic;
import org.cx.game.card.trick.Trick;
import org.cx.game.core.IPlayer;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IPlace;

/**
 * 地刺陷阱
 * @author chenxian
 *
 */
public class PrickTrick extends SimpleTrick {
	
	public PrickTrick(Integer bout, Integer effectBout, Integer damageScale, Integer boutDamageScale, Integer energyDownScale,
			IPlace place, IPlayer player) {
		super(bout, DEFAULT_TOUCHNUMBEROFTIMES, IMagic.Style_physical, IBuff.Type_Harm, IMagic.Func_Astrict, effectBout, damageScale, boutDamageScale, energyDownScale, 0, 0, 0, place, player);
	}

}
