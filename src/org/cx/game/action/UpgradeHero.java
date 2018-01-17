package org.cx.game.action;

import java.util.Map;

import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.treasure.EmpiricValue;
import org.cx.game.widget.treasure.SkillCount;

public class UpgradeHero extends UpgradeCorps implements IUpgrade {

	public UpgradeHero(Map<Integer, String> requirement) {
		super(requirement);
		// TODO Auto-generated constructor stub
	}

	private SkillCount skillCount = new SkillCount(1);            //技能点
	
	/**
	 * 技能点
	 * @return
	 */
	public SkillCount getSkillCount() {
		return skillCount;
	}
	
	public void addToSkillCount(SkillCount sc) {
		// TODO Auto-generated method stub
		if(!sc.isEmpty())
			this.skillCount.add(sc);
	}
	
	@Override
	public void action(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.action(objects);
		
		/*
		 * 英雄每升一级，增加1个技能点；
		 */
		SkillCount sc = new SkillCount(1);
		addToSkillCount(sc);
	}
}
