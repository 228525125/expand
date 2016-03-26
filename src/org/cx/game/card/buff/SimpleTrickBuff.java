package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;

public class SimpleTrickBuff extends SimpleBuff {

	private Integer damageScale = 0;
	private Integer energyDownScale = 0;
	private Integer speedDownScale = 0;
	private Integer atkDownScale = 0;
	private Integer defDownScale = 0;
	
	/**
	 * 
	 * @param bout
	 * @param style
	 * @param type
	 * @param func
	 * @param damageScale            每回合造成的伤害比例，以总生命为基数
	 * @param energyDownScale        精力下降比例，以总精力为基数
	 * @param speedDownScale         攻击速度下降比例，以总攻击速度为基数
	 * @param atkDownScale           攻击力下降比例，以当前攻击力为基数
	 * @param defDownScale           防御力下降比例，以当前防御力为基数，ImmuneDamageRatio = 当前ImmuneDamageRatio + defDownScale；
	 * @param life
	 */
	public SimpleTrickBuff(Integer bout, Integer style, Integer type,
			Integer func, Integer damageScale, Integer energyDownScale, Integer speedDownScale, Integer atkDownScale, Integer defDownScale, LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
		this.damageScale = damageScale;
		this.energyDownScale = energyDownScale;
		this.speedDownScale = speedDownScale;
		this.atkDownScale = atkDownScale;
		this.defDownScale = defDownScale;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		if(0!=this.damageScale){
			Integer damage = getOwner().getHp()*damageScale/100;
			getOwner().getDeath().magicToHp(-damage);
		}
		
		if(0!=this.energyDownScale){
			Integer energy = getOwner().getEnergy();
			Integer energyDownValue = energy*energyDownScale/100;
			addToKeepEnergy(-energyDownValue);
		}
		
		if(0!=this.speedDownScale){
			Integer speed  = getOwner().getSpeedChance();
			Integer speedDownValue = speed*speedDownScale/100;
			addToKeepSpeedChance(-speedDownValue);
		}
		
		if(0!=this.atkDownScale){
			Integer atk = getOwner().getAttack().getAtk();
			Integer atkDownValue = atk*atkDownScale/100;
			addToKeepAtk(-atkDownValue);
		}
		
		if(0!=this.defDownScale)
			addToKeepImmuneDamageRatio(defDownScale);           //免伤比同为一种比例，因此直接计算
		
		super.affect(objects);
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		affect();
	}

}
