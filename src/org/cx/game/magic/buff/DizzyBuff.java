package org.cx.game.magic.buff;

import java.util.List;

import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.AbstractBuff;
import org.cx.game.intercepter.AbstractIntercepter;
import org.cx.game.intercepter.IIntercepter;

/**
 * 眩晕
 * @author chenxian
 *
 */
public class DizzyBuff extends AbstractBuff {

	public static final Integer DizzyBuff_ID = 10350001;
	
	public DizzyBuff(Integer bout, Corps corps) {
		super(DizzyBuff_ID, bout, corps);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}

	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		/*
		 * 被击晕后，锁定的目标全部取消
		 */
		List<AbstractBuff> buffs = getOwner().getNexusBuff(AttackLockBuff.class);
		for(AbstractBuff buff : buffs)
			buff.invalid();
		
		/*
		 * 晕迷中的单位无法反击
		 */
		getOwner().getDefend().setCanFightBack(false);
		
		IIntercepter activateIn = new AbstractIntercepter() {    //当activate状态为true时，将激活状态改为false

			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				if((Boolean)((Object[])args[0])[0])
					affect();
			}
		};
		
		recordIntercepter(getOwner().getActivate(), activateIn);
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);

		getOwner().activate(false);
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
