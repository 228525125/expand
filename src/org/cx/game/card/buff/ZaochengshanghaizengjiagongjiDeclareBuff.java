package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;

public class ZaochengshanghaizengjiagongjiDeclareBuff extends Buff {

	public final static Integer ZaochengshanghaizengjiagongjiDeclareBuff_ID = 10300017;
	
	private Integer harm = 0;
	private Integer atkUpValue = 0;
	
	public ZaochengshanghaizengjiagongjiDeclareBuff(Integer harm, Integer atkUpValue,
			LifeCard life) {
		super(ZaochengshanghaizengjiagongjiDeclareBuff_ID, IBuff.Max_Bout, life);
		// TODO Auto-generated constructor stub
		this.harm = harm;
		this.atkUpValue = atkUpValue;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		getOwner().getAttack().addToAtk(atkUpValue);
		getOwner().getDeath().addToHp(harm);
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
