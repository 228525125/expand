package org.cx.game.card.trick;

import org.cx.game.card.buff.Buff;
import org.cx.game.card.magic.IMagic;
import org.cx.game.core.IPlayer;
import org.cx.game.widget.IPlace;

public class XianluoTrick extends SimpleTrick {

	public XianluoTrick(Integer bout, Integer effectBout,
			Integer defDownScale,
			IPlace place, IPlayer player) {
		super(bout, DEFAULT_TOUCHNUMBEROFTIMES, IMagic.Style_physical, Buff.Type_Harm, IMagic.Func_Astrict, effectBout, 0,0,
				100, 0, 0, defDownScale, place,
				player);
		// TODO Auto-generated constructor stub
	}

}
