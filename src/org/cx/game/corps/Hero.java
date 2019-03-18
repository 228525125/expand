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
}
