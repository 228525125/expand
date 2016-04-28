package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.SimpleBuff;

/**
 * 怒吼
 * @author chenxian
 *
 */
public class AngerRoarBuff extends SimpleBuff {

	private Integer upAtkScale;
	
	public AngerRoarBuff(Integer bout, Integer upAtkScale,  LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.upAtkScale = upAtkScale;
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub		
		super.effect();
		
		affect();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		Integer atk = getOwner().getAttack().getAtk();
		Integer upAtkValue = atk*upAtkScale/100;
		addToKeepAtk(upAtkValue);
		
		super.affect(objects);
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub

	}

}
