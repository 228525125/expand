package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;

public class ZengjiazhanchangsuiconggongjiBuff extends Buff {

	public final static Integer ZengjiazhanchangsuiconggongjiBuff_ID = 10300008;
	
	private Integer atkUpValue = 0;
	
	public ZengjiazhanchangsuiconggongjiBuff(Integer atkUpValue, Integer bout, LifeCard life) {
		super(ZengjiazhanchangsuiconggongjiBuff_ID, bout, life);
		// TODO Auto-generated constructor stub
		this.atkUpValue = atkUpValue;
	}

	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		getOwner().getAttack().addToAtk(atkUpValue);
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		affect();
	}
	
	@Override
	public void invalid() {
		// TODO Auto-generated method stub
		super.invalid();
		
		getOwner().getAttack().addToAtk(-atkUpValue);
	}
	
}
