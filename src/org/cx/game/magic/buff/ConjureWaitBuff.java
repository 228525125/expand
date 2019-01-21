package org.cx.game.magic.buff;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.intercepter.AbstractIntercepter;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.widget.AbstractOption;

/**
 * 法术引导状态，当施法者使用需要引导的法术时，在法术生效之前就会进入该状态
 * @author chenxian
 *
 */
public class ConjureWaitBuff extends AbstractBuff {

	public static final Integer ConjureWaitBuff_ID = 10350004;
	
	private AbstractOption option = null;
	
	public ConjureWaitBuff(Integer bout, AbstractOption option, AbstractCorps corps) {
		super(ConjureWaitBuff_ID, bout, corps);
		// TODO Auto-generated constructor stub
		this.option = option;
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
		 * 引导期间不能反击
		 */
		getOwner().getDefend().setCanFightBack(false);
		
		/*
		 * 受到攻击被打断
		 */
		IIntercepter defendIn = new AbstractIntercepter() {
			
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				ConjureWaitBuff.this.option.cancelExecuteWait();
				invalid();
			}
		};
		recordIntercepter(getOwner().getDefend(), defendIn);
		
		/*
		 * 引导期间不能做任何事，包括反击
		 */
		IIntercepter activateIn = new AbstractIntercepter() {

			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				if((Boolean)((Object[])args[0])[0]){
					getOwner().activate(false);
					getOwner().getDefend().setCanFightBack(false);
				}
			}
		};
		recordIntercepter(getOwner().getActivate(), activateIn);
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
