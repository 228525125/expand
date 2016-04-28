package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.Buff;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

/**
 * 隐藏
 * @author chenxian
 *
 */
public class HideBuff extends Buff {

	public HideBuff(Integer bout,
			LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		if(!getOwner().getHide())
			getOwner().setHide(true);
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		/*
		 * 下一次获得控制权时生效
		 */
		IIntercepter activateIn = new Intercepter("setActivate"){
			
			@Override
			public void before(Object[] args) {
				if((Boolean)args[0])
					affect();
			}
		};
		recordIntercepter(getOwner(), activateIn);
		
		/*
		 * 当移动时，效果消失
		 */
		IIntercepter moveIn = new Intercepter(){
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				invalid();
			}
		};
		recordIntercepter(getOwner().getMove(), moveIn);
		
		/*
		 * 当攻击时，效果消失
		 */
		IIntercepter attackIn = new Intercepter(){
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				invalid();
			}
		};
		recordIntercepter(getOwner().getAttack(), attackIn);
		
		/*
		 * 当使用技能时，效果消失
		 */
		IIntercepter conjureIn = new Intercepter(){
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				invalid();
			}
		};
		recordIntercepter(getOwner().getConjure(), conjureIn);
	}

}
