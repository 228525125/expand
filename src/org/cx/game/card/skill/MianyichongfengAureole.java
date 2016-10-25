package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.MianyichongfengAureoleBuff;

public class MianyichongfengAureole extends Aureole {

	public MianyichongfengAureole(Integer range) {
		super(range);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void leave(LifeCard life) {
		// TODO Auto-generated method stub
		for(IBuff buff : life.getBuffList()){
			if (buff instanceof MianyichongfengAureoleBuff) {
				buff.invalid();
			}
		}
	}

	@Override
	public Class getBuffClass() {
		// TODO Auto-generated method stub
		return MianyichongfengAureole.class;
	}
	
	private static final Integer MaxBout = 999;
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		new MianyichongfengAureoleBuff(MaxBout, life).effect();
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		
	}

}
