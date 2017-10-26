package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;

public class ZengjiagongjiBuff extends Buff {

	public final static Integer ZengjiagongjiBuff_ID = 10300002;
	
	private Integer atk = 0;
	
	public ZengjiagongjiBuff(Integer bout, Integer atk, LifeCard life) {
		super(ZengjiagongjiBuff_ID, bout, life);
		// TODO Auto-generated constructor stub
		
		this.atk = atk;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		getOwner().getAttack().addToExtraAtk(this.atk);
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
		
		getOwner().getAttack().addToExtraAtk(-this.atk);
	}

}
