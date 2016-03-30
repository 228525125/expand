package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.SimpleBuff;
import org.cx.game.card.magic.IMagic;

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
		
		addToKeepImmuneDamageRatio(defUpScale);
		
		affect();
	}
	
	
}
