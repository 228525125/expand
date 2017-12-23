package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;

public class ZengjiagongjiBuff extends Buff {

	public final static Integer ZengjiagongjiBuff_ID = 10300002;
	
	public ZengjiagongjiBuff(Integer bout, Integer atk, LifeCard life) {
		super(ZengjiagongjiBuff_ID, bout, life);
		// TODO Auto-generated constructor stub
		
		setAtk(atk);
	}
}
