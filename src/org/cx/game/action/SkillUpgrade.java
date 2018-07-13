package org.cx.game.action;

import java.util.HashMap;

import org.cx.game.corps.Hero;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.skill.AbstractSkill;
import org.cx.game.tools.Util;
import org.cx.game.widget.treasure.SkillCount;

public class SkillUpgrade extends Upgrade implements IAction {
	
	public SkillUpgrade() {
		// TODO Auto-generated constructor stub		
		super(new HashMap<Integer, String>());
	}
	
	@Override
	public AbstractSkill getOwner() {
		// TODO Auto-generated method stub
		return (AbstractSkill) super.getOwner();
	}

	@Override
	public SkillCount getRequirement() {
		// TODO Auto-generated method stub
		SkillCount sc = new SkillCount(1);
		return sc;
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		super.action(objects);
		
		/*
		 * 扣除技能点
		 */
		Hero hero = (Hero) getOwner().getOwner();
		HeroUpgrade up = (HeroUpgrade) hero.getUpgrade();
		up.setSkillCount(Util.Sub, getRequirement());
	}
}
