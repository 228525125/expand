package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Buff;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.observer.NotifyInfo;

/**
 * 抵消伤害
 * @author chenxian
 *
 */
public class DamageResistBuff extends Buff {

	private Integer defendValue;      
	private Integer offsetScale;
	
	/**
	 * 
	 * @param bout
	 * @param defendValue 防御值，直接抵消伤害
	 * @param offsetScale 抵消比例
	 * @param life
	 */
	public DamageResistBuff(Integer bout, Integer style, Integer type, Integer func, Integer defendValue, Integer offsetScale, LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
		this.defendValue = defendValue;
		this.offsetScale = offsetScale;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Integer hp = (Integer) objects[0];
		if(0>hp){      //判断是否为伤害
			hp = Math.abs(hp);
			Integer offsetValue = hp*offsetScale/100;
			if(defendValue>=offsetValue){
				defendValue -= offsetValue;
				hp -= offsetValue;
			}else{
				offsetValue -= defendValue;
				hp -= offsetValue;
				defendValue = 0;
			}
		}		
	}

	@Override
	public void effect() {
		// TODO Auto-generated method stub
		IIntercepter hpIn =  new Intercepter("addTohp"){

			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				if(defendValue<=0)
					invalid();
			}

			@Override
			public void before(Object[] args) {
				affect(args);
			}
		};
		recordIntercepter(getOwner().getDeath(), hpIn);
		
		super.effect();
	}

}
