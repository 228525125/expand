package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;

public class HardSkinBuff extends SimpleBuff {

	private Integer defUpScale = 0;
	
	public HardSkinBuff(Integer bout, Integer defUpScale, LifeCard life) {
		super(bout, IMagic.Style_Magic, IBuff.Type_Benefit, IMagic.Func_Gain, life);
		// TODO Auto-generated constructor stub
		this.defUpScale = defUpScale;
	}

	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		Integer defUpValue = getOwner().getAttacked().getImmuneDamageRatio()*this.defUpScale/100;
		addToKeepImmuneDamageRatio(defUpValue);
		
		affect();
	}
	
	
}
