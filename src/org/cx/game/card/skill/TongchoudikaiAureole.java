package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.TongchoudikaiAureoleBuff;

/**
 * 同仇敌忾
 * @author chenxian
 *
 */
public class TongchoudikaiAureole extends Aureole {
	
	private Integer atkUpScale;
	
	public TongchoudikaiAureole(Integer range, Integer atkUpScale) {
		// TODO Auto-generated constructor stub
		super(range);
		
		this.atkUpScale = atkUpScale;
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
	}

	@Override
	public void leave(LifeCard life) {
		// TODO Auto-generated method stub
		for(IBuff buff : life.getBuffList()){
			if (buff instanceof TongchoudikaiAureoleBuff) {
				buff.invalid();
			}
		}
	}

	@Override
	public Class getBuffClass() {
		// TODO Auto-generated method stub
		return TongchoudikaiAureole.class;
	}
	
	private static final Integer MaxBout = 999;
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		new TongchoudikaiAureoleBuff(MaxBout, atkUpScale, getOwner(), life).effect();
	}
	
}
