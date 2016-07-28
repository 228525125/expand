package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

/**
 * 同仇敌忾
 * @author chenxian
 *
 */
public class TongchoudikaiAureoleBuff extends Buff {

	private Integer atkUpValue;  
	private LifeCard host;       //光环效果的发起人
	
	public TongchoudikaiAureoleBuff(Integer bout, Integer atkUpValue, LifeCard host, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.atkUpValue = atkUpValue;
		this.host = host;
	}

	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		this.host.getAttack().addToAtk(atkUpValue);
		
		super.affect(objects);
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		IIntercepter deathIn = new Intercepter() {
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				affect();
			}
		};
		
		recordIntercepter(getOwner().getDeath(), deathIn);
		
		super.effect();
	}
	
}
