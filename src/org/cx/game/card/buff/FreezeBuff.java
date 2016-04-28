package org.cx.game.card.buff;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.SimpleBuff;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.I18n;
import org.cx.game.widget.IControlQueue;

/**
 * 冻结
 * @author chenxian
 *
 */
public class FreezeBuff extends SimpleBuff {
     
	private String name = null;
	private Integer energyDownScale;  
	private Integer speedDownScale;
	private Integer damage; 
	
	/**
	 * 
	 * @param bout 持续回合
	 * @param style 
	 * @param type
	 * @param damage 直接伤害
	 * @param energyScale 移动范围降低比例 基准100
	 * @param speedScale 攻击速度降低比例 基准100
	 * @param life
	 */
	public FreezeBuff(Integer bout, Integer damage, Integer energyScale, Integer speedScale, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.energyDownScale = energyScale;
		this.speedDownScale = speedScale;
		this.damage = damage;
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub		
		super.effect();
		
		affect();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		
		getOwner().getDeath().magicToHp(-damage);         //造成伤害
			
		Integer energy = getOwner().getMove().getEnergy();
		Integer energyDownValue = energy*energyDownScale/100;
		addToKeepEnergy(energyDownValue);
			
		Integer speed  = getOwner().getAttack().getSpeedChance();
		Integer speedDownValue = speed*speedDownScale/100;
		addToKeepSpeedChance(speedDownValue);
		
		super.affect(objects);
	}

}
