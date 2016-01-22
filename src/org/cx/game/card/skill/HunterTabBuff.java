package org.cx.game.card.skill;

import org.cx.game.action.IAttack;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Buff;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

/**
 * 猎人印记
 * @author chenxian
 *
 */
public class HunterTabBuff extends Buff {

	private Integer damageScale;
	
	/**
	 * 
	 * @param bout
	 * @param style
	 * @param type
	 * @param damageScale 远程伤害增加百分比，计算的时候：100+damageScale
	 * @param life
	 */
	public HunterTabBuff(Integer bout, Integer style, Integer type, Integer func,
			Integer damageScale, LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
		this.damageScale = damageScale;
	}

	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		if(getOwner().getHide())
			getOwner().setHide(false);
	}
	
	private Boolean isFar = false;
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		/* 
		 * 远程伤害则增加
		 */
		IIntercepter attackedIn = new Intercepter(){
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				LifeCard life = (LifeCard) ((Object[]) args[0])[0];
				if(IAttack.Mode_Far.equals(life.getAttack().getMode()))
					isFar = true;
			}
		};
		recordIntercepter(getOwner().getAttacked(), attackedIn);
		
		IIntercepter attackToDamageIn = new Intercepter("attackToDamage"){
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				if(isFar){
					Integer hp = (Integer) args[0];
					args[0] = hp*(100+damageScale);
					isFar = false;
				}
			}
		};
		recordIntercepter(getOwner().getDeath(), attackToDamageIn);
		
		/*
		 * 反隐藏
		 */
		IIntercepter hideIn = new Intercepter("setHide"){
			
			private Boolean invoke = true;
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				Boolean hide = (Boolean) args[0];
				if(hide)
					invoke = false;
			}
			
			@Override
			public Boolean isInvoke() {
				// TODO Auto-generated method stub
				return invoke;
			}
		};
		recordIntercepter(getOwner(), hideIn);
		
		affect();
	}

}
