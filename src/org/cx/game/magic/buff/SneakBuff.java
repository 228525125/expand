package org.cx.game.magic.buff;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.intercepter.AbstractIntercepter;
import org.cx.game.intercepter.IIntercepter;

/**
 * 隐身状态
 * @author chenxian
 *
 */
public class SneakBuff extends AbstractBuff {

	public static final Integer SneakBuff_ID = 10300002;
	
	public SneakBuff(Integer bout, AbstractCorps corps) {
		super(SneakBuff_ID, bout, corps);
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
		
		IIntercepter attackIn = new AbstractIntercepter() {
			
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				getOwner().getMove().changeHide(false);
				invalid();
			}
		};
		recordIntercepter(getOwner().getAttack(), attackIn);
		
		IIntercepter conjureIn = new AbstractIntercepter() {
			
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				getOwner().getMove().changeHide(false);
				invalid();
			}
		};
		recordIntercepter(getOwner().getConjure(), conjureIn);
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
