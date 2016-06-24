package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;

public class JingshenhujiaBuff extends Buff {

	private Integer hpUpValue = 0;
	
	public JingshenhujiaBuff(Integer bout, Integer hpUpValue, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.hpUpValue = hpUpValue;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Integer hp = getOwner().getHp();
		hp += hpUpValue;
		getOwner().setHp(hp);                        //增加上限
		getOwner().getDeath().magicToHp(hpUpValue);
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
		
		Integer hpLimit = getOwner().getHp();
		hpLimit -= hpUpValue;
		getOwner().setHp(hpLimit);
		Integer hpValue = getOwner().getDeath().getHp();
		hpValue = hpValue>hpLimit ? hpValue - hpLimit : 0;       //超出HP上限部分
		getOwner().getDeath().magicToHp(-hpValue);
	}

}
