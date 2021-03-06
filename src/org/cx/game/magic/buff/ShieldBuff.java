package org.cx.game.magic.buff;

import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.AbstractBuff;
import org.cx.game.intercepter.AbstractIntercepter;
import org.cx.game.intercepter.IIntercepter;

/**
 * 护盾
 * @author chenxian
 *
 */
public class ShieldBuff extends AbstractBuff {

	public static final Integer ShieldBuff_ID = 10300004;
	
	public ShieldBuff(Corps corps) {
		super(ShieldBuff_ID, AbstractBuff.Max_Bout, corps);
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
		
		IIntercepter inDeath = new AbstractIntercepter("addToHp") {
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				Integer hp = (Integer)args[0];
				if(hp<0)
					affect(args);
			}
		};
		
		recordIntercepter(getOwner().getDeath(), inDeath);
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		((Object[])objects[0])[0] = Integer.valueOf(0);
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
