package org.cx.game.magic.buff;

import java.util.List;

import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.AbstractBuff;

public class TauntBuff extends AbstractBuff {

	public final static Integer TauntBuff_ID = 10300006;
	private Corps taunter = null;
	
	public TauntBuff(Corps taunter, Corps corps) {
		super(TauntBuff_ID, AbstractBuff.Max_Bout, corps);
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
		List<AbstractBuff> buffs = getOwner().getNexusBuff(AttackLockBuff.class);
		for(AbstractBuff buff : buffs)
			buff.invalid();
		
		new AttackLockBuff(getOwner(),taunter).effect();
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		affect();
	}

	public Corps getTaunter() {
		return taunter;
	}
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
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
