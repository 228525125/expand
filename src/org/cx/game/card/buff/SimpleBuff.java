package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.Util;

/**
 * 能力值可能发生改变的Buff
 * @author chenxian
 *
 */
public abstract class SimpleBuff extends Buff {

	private Integer keepAtkNewValue = 0;
	private Integer keepAtkOldValue = 0;
	private Integer keepRangeNewValue = 0;
	private Integer keepRangeOldValue = 0;
	private Integer keepSpeedChanceNewValue = 0;
	private Integer keepSpeedChanceOldValue = 0;
	private Integer keepEnergyNewValue = 0;
	private Integer keepEnergyOldValue = 0;
	
	public SimpleBuff(Integer bout, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		if(0!=keepAtkNewValue){
			getOwner().getAttack().addToAtk(keepAtkNewValue - keepAtkOldValue);
			keepAtkOldValue = keepAtkNewValue;
		}
		
		if(0!=keepRangeNewValue){
			getOwner().getAttack().addToRange(keepRangeNewValue - keepRangeOldValue);
			keepRangeOldValue = keepRangeNewValue;
		}
		
		if(0!=keepSpeedChanceNewValue){
			getOwner().getAttack().addToSpeedChance(keepSpeedChanceNewValue - keepSpeedChanceOldValue);
			keepSpeedChanceOldValue = keepSpeedChanceNewValue;
		}
		
		if(0!=keepEnergyNewValue){
			getOwner().getMove().addToEnergy(keepEnergyNewValue - keepEnergyOldValue);
			keepEnergyOldValue = keepEnergyNewValue;
		}
	}
	
	@Override
	public void invalid() {
		// TODO Auto-generated method stub
		super.invalid();
		
		if(0!=keepAtkOldValue){
			getOwner().getAttack().addToAtk(-keepAtkOldValue);
			addToKeepAtk(0);
		}
		
		if(0!=keepRangeOldValue){
			getOwner().getAttack().addToRange(-keepRangeOldValue);
			addToKeepRange(0);
		}

		if(0!=keepSpeedChanceOldValue){
			getOwner().getAttack().addToSpeedChance(-keepSpeedChanceOldValue);
			addToKeepSpeedChance(0);
		}
		
		if(0!=keepEnergyOldValue){
			getOwner().getMove().addToEnergy(-keepEnergyOldValue);
			addToKeepEnergy(0);
		}
	}
	
	public void addToKeepAtk(Integer keepAtk) {
		this.keepAtkNewValue = keepAtk;
	}
	
	public void addToKeepRange(Integer keepRange){
		this.keepRangeNewValue = keepRange;
	}

	public void addToKeepSpeedChance(Integer keepSpeedChance) {
		this.keepSpeedChanceNewValue = keepSpeedChance;
	}

	public void addToKeepEnergy(Integer keepEnergy) {
		this.keepEnergyNewValue = keepEnergy;
	}
}
