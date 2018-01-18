package org.cx.game.action;

import java.util.HashMap;

import org.cx.game.corps.Hero;
import org.cx.game.magic.skill.ISkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.treasure.SkillCount;

public class UpgradeSkill extends Upgrade implements IAction {
	
	public UpgradeSkill() {
		// TODO Auto-generated constructor stub		
		super(new HashMap<Integer, String>());
	}

	@Override
	public SkillCount getRequirement() {
		// TODO Auto-generated method stub
		SkillCount sc = new SkillCount(-1);
		return sc;
	}
	
	@Override
	public ISkill getOwner() {
		// TODO Auto-generated method stub
		return (ISkill) super.getOwner();
	}
	
	@Override
	public void action(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.action(objects);
		
		/*
		 * 扣除技能点
		 */
		Hero hero = (Hero) getOwner().getOwner();
		UpgradeHero up = (UpgradeHero) hero.getUpgrade();
		up.addToSkillCount(getRequirement());
	}
}
