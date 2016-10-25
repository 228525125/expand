package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Aureole;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.WarnAureoleBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 警戒光环
 * @author chenxian
 *
 */
public class WarnAureole extends Aureole {

	private Integer immuneDamageRatio = 0;
	
	public WarnAureole(Integer range, Integer immuneDamageRatio) {
		super(range);
		// TODO Auto-generated constructor stub
		this.immuneDamageRatio = immuneDamageRatio;
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void leave(LifeCard life) {
		// TODO Auto-generated method stub
		for(IBuff buff : life.getBuffList()){
			if (buff instanceof WarnAureoleBuff) {
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
		
		new WarnAureoleBuff(MaxBout, immuneDamageRatio, life).effect();
	}

	@Override
	public Class getBuffClass() {
		// TODO Auto-generated method stub
		return WarnAureoleBuff.class;
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		
	}
}
