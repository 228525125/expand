package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.DunpaifangyuAureoleBuff;
import org.cx.game.card.buff.IBuff;

public class DunpaifangyuAureole extends Aureole {

	private Integer atkDownScale = 0;
	
	public DunpaifangyuAureole(Integer range, Integer atkDownScale) {
		super(range);
		// TODO Auto-generated constructor stub
		this.atkDownScale = atkDownScale;
	}

	@Override
	public void leave(LifeCard life) {
		// TODO Auto-generated method stub
		for(IBuff buff : life.getBuffList()){
			if (buff instanceof DunpaifangyuAureoleBuff) {
				buff.invalid();
			}
		}
	}

	@Override
	public Class getBuffClass() {
		// TODO Auto-generated method stub
		return DunpaifangyuAureole.class;
	}
	
	private static final Integer MaxBout = 999;
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		new DunpaifangyuAureoleBuff(MaxBout, atkDownScale, life).effect();
	}

}
