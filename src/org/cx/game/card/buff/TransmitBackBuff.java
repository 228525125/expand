package org.cx.game.card.buff;

import org.cx.game.action.IMove;
import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.Buff;
import org.cx.game.widget.IGround;

public class TransmitBackBuff extends Buff {

	private Integer position;
	
	public TransmitBackBuff(Integer bout, Integer style, Integer type, Integer func, Integer position,
			LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
		this.position = position;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		
	}
	
	@Override
	public void invalid() {
		// TODO Auto-generated method stub
		IGround ground = getOwner().getPlayer().getGround();
		ground.move(getOwner(), position, IMove.Type_Flash);
		
		super.invalid();
	}

}
