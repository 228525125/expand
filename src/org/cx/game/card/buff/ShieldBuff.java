package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

/**
 * 护盾
 * @author chenxian
 *
 */
public class ShieldBuff extends Buff {

	public static final Integer ShieldBuff_ID = 10300004;
	
	public ShieldBuff(LifeCard life) {
		super(ShieldBuff_ID, IBuff.Max_Bout, life);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		IIntercepter inDeath = new Intercepter("addToHp") {
			
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

}