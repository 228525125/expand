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

	private Integer atkUpScale;  
	private LifeCard host;       //光环效果的发起人
	
	public TongchoudikaiAureoleBuff(Integer bout, Integer atkUpScale, LifeCard host, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.atkUpScale = atkUpScale;
		this.host = host;
	}

	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		Integer atk = this.host.getAtk();
		this.host.getAttack().addToAtk(atk*atkUpScale/100);
		
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
