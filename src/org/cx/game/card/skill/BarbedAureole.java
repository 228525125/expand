package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Aureole;
import org.cx.game.card.buff.BarbedAureoleBuff;
import org.cx.game.card.buff.IBuff;

/**
 * 荆棘光环
 * @author chenxian
 *
 */
public class BarbedAureole extends Aureole {

	private Integer returnRatio = 0;
	
	public BarbedAureole(Integer style, Integer range, Integer returnRatio) {
		super(style, range);
		// TODO Auto-generated constructor stub
		this.returnRatio = returnRatio;
	}

	@Override
	public void leave(LifeCard life) {
		// TODO Auto-generated method stub
		for(IBuff buff : life.getBuffList()){
			if (buff instanceof BarbedAureoleBuff) {
				buff.invalid();
			}
		}
	}
	
	private static final Integer MaxBout = 999;
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		new BarbedAureoleBuff(MaxBout, getStyle(), IBuff.Type_Benefit, getFunc(), returnRatio, life).effect();
	}

	@Override
	public Class getBuffClass() {
		// TODO Auto-generated method stub
		return BarbedAureole.class;
	}

}
