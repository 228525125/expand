package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;

public class JingshenhujiaBuff extends SimpleBuff {

	private Integer defUpScale = 0;
	
	public JingshenhujiaBuff(Integer bout, Integer defUpScale, LifeCard life) {
		super(bout, life);
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
