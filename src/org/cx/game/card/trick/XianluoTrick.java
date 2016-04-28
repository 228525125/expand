package org.cx.game.card.trick;

import org.cx.game.card.buff.SimpleTrickBuff;
import org.cx.game.card.buff.XianluoTrickBuff;
import org.cx.game.core.IPlayer;
import org.cx.game.widget.IPlace;

public class XianluoTrick extends SimpleTrick {

	public XianluoTrick(Integer bout, Integer effectBout,
			Integer defDownScale,
			IPlace place, IPlayer player) {
		super(bout, DEFAULT_TOUCHNUMBEROFTIMES, effectBout, 0,0,
				100, 0, 0, defDownScale, place,
				player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SimpleTrickBuff getTrickBuff() {
		// TODO Auto-generated method stub
		return new XianluoTrickBuff();
	}

}
