package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;

public class SharpToothBuff extends SimpleBuff {

	private Integer atkUpScale = 0;
	
	public SharpToothBuff(Integer bout, Integer atkUpScale, LifeCard life) {
		super(bout, IMagic.Style_Magic, IBuff.Type_Benefit, IMagic.Func_Gain, life);
		// TODO Auto-generated constructor stub
		
		this.atkUpScale = atkUpScale;
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		Integer atkUpValue = getOwner().getAttack().getAtk()*this.atkUpScale/100;
		addToKeepAtk(atkUpValue);
		
		affect();
	}

}
