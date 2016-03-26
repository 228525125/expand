package org.cx.game.card.trick;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.FreezeBuff;
import org.cx.game.card.buff.SimpleTrickBuff;
import org.cx.game.card.magic.IMagic;
import org.cx.game.core.IPlayer;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IPlace;

public abstract class SimpleTrick extends Trick {
	
	private Integer effectBout = 1;
	private Integer damageScale = 0;
	private Integer boutDamageScale = 0;
	private Integer energyDownScale = 0;
	private Integer speedDownScale = 0;
	private Integer atkDownScale = 0;
	private Integer defDownScale = 0;
	
	/**
	 * 
	 * @param bout                陷阱持续回合
	 * @param touchNumberOfTimes  陷阱触发次数
	 * @param style               
	 * @param type
	 * @param func
	 * @param effectBout          触发效果持续回合
	 * @param damageScale         直接伤害比例，以总生命值为基数
	 * @param boutDamageScale     每回合伤害比例，以总生命值为基数
	 * @param energyDownScale     移动范围下降比例，以总精力值为基数
	 * @param speedDownScale      攻击速度下降比例，以总攻击速度为基数
	 * @param atkDownScale        攻击力下降比例，以当前攻击力为基数
	 * @param defDownScale        防御力下降比例，以当前防御力为基数
	 * @param place               陷阱位置
	 * @param player
	 */
	public SimpleTrick(Integer bout, Integer touchNumberOfTimes, Integer style, Integer type, Integer func, Integer effectBout, Integer damageScale, Integer boutDamageScale,
			Integer energyDownScale, Integer speedDownScale, Integer atkDownScale, Integer defDownScale, IPlace place, IPlayer player) {
		super(bout, touchNumberOfTimes, style, type, func, place, player);
		// TODO Auto-generated constructor stub
		this.effectBout = effectBout;
		this.damageScale = damageScale;
		this.boutDamageScale = boutDamageScale;
		this.energyDownScale = energyDownScale;
		this.speedDownScale = speedDownScale;
		this.atkDownScale = atkDownScale;
		this.defDownScale = defDownScale;
	}

	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		if(this.damageScale>0){
			Integer damage = life.getHp()*damageScale/100;
			if(IMagic.Style_physical.equals(getStyle()))
				life.getDeath().attackToDamage(-damage);
			else
				life.getDeath().magicToHp(-damage);
		}
		
		new SimpleTrickBuff(effectBout,getStyle(),getType(),getFunc(),this.boutDamageScale,this.energyDownScale,this.speedDownScale,this.atkDownScale,this.defDownScale,life).effect();
	}

	protected Integer getEffectBout() {
		return effectBout;
	}

	protected Integer getDamageScale() {
		return damageScale;
	}

	protected Integer getBoutDamageScale() {
		return boutDamageScale;
	}

	protected Integer getEnergyDownScale() {
		return energyDownScale;
	}

	protected Integer getSpeedDownScale() {
		return speedDownScale;
	}

	protected Integer getAtkDownScale() {
		return atkDownScale;
	}

	protected Integer getDefDownScale() {
		return defDownScale;
	}
	
}
