package org.cx.game.card.buff;

import java.util.List;

import org.cx.game.card.LifeCard;

public class TauntBuff extends Buff {

	public final static Integer TauntBuff_ID = 10300006;
	private LifeCard taunter = null;
	
	public TauntBuff(LifeCard taunter, LifeCard life) {
		super(TauntBuff_ID, IBuff.Max_Bout, life);
		// TODO Auto-generated constructor stub
		this.taunter = taunter;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		/*
		 * 被嘲讽后，锁定嘲讽者
		 */
		List<IBuff> buffs = getOwner().getNexusBuff(AttackLockBuff.class);
		for(IBuff buff : buffs)
			buff.invalid();
		
		new AttackLockBuff(getOwner(),taunter).effect();
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		affect();
	}

	public LifeCard getTaunter() {
		return taunter;
	}
}
