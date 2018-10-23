package org.cx.game.corps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cx.game.action.Upgrade;
import org.cx.game.action.HeroUpgrade;
import org.cx.game.magic.buff.AbstractBuff;
import org.cx.game.magic.skill.AbstractSkill;
import org.cx.game.tools.I18n;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.treasure.Mineral;
import org.cx.game.widget.treasure.Resource;
import org.cx.game.widget.treasure.Treasure;

public class Hero extends Corps {
	
	private String name = null;
	
	private Upgrade upgrade = null;
	
	private List<Treasure> treasures = new ArrayList<Treasure>();
	
	public Hero(Integer type) {
		super(type);
		// TODO Auto-generated constructor stub
		setHero(true);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name)
			name = I18n.getMessage(Corps.class, getType(), "name");
		return name;
	}
	
	/**
	 * 恢复，当英雄被复活时调用
	 */
	public void recover() {
		// TODO Auto-generated method stub
		clearBuff();            //首先执行是因为，Buff.invalid会影响死者属性
		
		getActivate().setActivation(false);
		
		getDeath().setHp(getHp());
		
		setHide(false);
		
		List<AbstractBuff> buffs = new ArrayList<AbstractBuff>();     //与自己相关的buff，不是自己发起的buff，例如AttackLockBuff
		buffs.addAll(getNexusBuffList());
		for(AbstractBuff buff : buffs){
			buff.invalid();
		}
	}
	
	public Upgrade getUpgrade() {
		if(null==upgrade){
			Upgrade upgrade = new HeroUpgrade(getUpgradeRequirement());
			upgrade.setLevel(getLevel());
			upgrade.setOwner(this);
			this.upgrade = upgrade;
		}
		return this.upgrade;
	}
	
	public List<Treasure> getTreasures() {
		return treasures;
	}
	
	public void addTreasure(Treasure treasure) {
		this.treasures.add(treasure);
	}
	
	public void removeTreasure(Treasure treasure) {
		this.treasures.remove(treasure);
	}

	@Override
	public void setAtk(Integer atk) {
		// TODO Auto-generated method stub
		super.setAtk(atk);
	}
	
	@Override
	public void setDef(Integer def) {
		// TODO Auto-generated method stub
		super.setDef(def);
	}
	
	@Override
	public void setHp(Integer hp) {
		// TODO Auto-generated method stub
		super.setHp(hp);
	}
	
	@Override
	public void setConsume(Mineral consume) {
		// TODO Auto-generated method stub
		super.setConsume(consume);
	}
	
	@Override
	public void setAttackRange(Integer attackRange) {
		// TODO Auto-generated method stub
		super.setAttackRange(attackRange);
	}
	
	@Override
	public void setAttackMode(Integer attackMode) {
		// TODO Auto-generated method stub
		super.setAttackMode(attackMode);
	}
	
	@Override
	public void setSpeed(Integer speed) {
		// TODO Auto-generated method stub
		super.setSpeed(speed);
	}
	
	@Override
	public void setEnergy(Integer energy) {
		// TODO Auto-generated method stub
		super.setEnergy(energy);
	}
	
	@Override
	public void setMoveType(Integer moveType) {
		// TODO Auto-generated method stub
		super.setMoveType(moveType);
	}
	
	@Override
	public void setStar(Integer star) {
		// TODO Auto-generated method stub
		super.setStar(star);
	}
	
	@Override
	public void setLockChance(Integer lockChance) {
		// TODO Auto-generated method stub
		super.setLockChance(lockChance);
	}
	
	@Override
	public void setFleeChance(Integer fleeChance) {
		// TODO Auto-generated method stub
		super.setFleeChance(fleeChance);
	}
	
	@Override
	public void setSkillList(List<AbstractSkill> skillList) {
		// TODO Auto-generated method stub
		super.setSkillList(skillList);
	}
	
	public void setLevel(Integer level){
		super.setLevel(level);
	}

	@Override
	public void setUpgradeRequirement(Map<Integer, String> upgradeRequirement) {
		// TODO Auto-generated method stub
		super.setUpgradeRequirement(upgradeRequirement);
	}
}
