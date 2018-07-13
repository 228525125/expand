package org.cx.game.action;

import java.util.Map;

import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.Util;
import org.cx.game.widget.treasure.EmpiricValue;
import org.cx.game.widget.treasure.SkillCount;

public class HeroUpgrade extends CorpsUpgrade implements IAction {
	
	private SkillCount skillCount = new SkillCount(1);            //技能点

	public HeroUpgrade(Map<Integer, String> requirement) {
		super(requirement);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 技能点
	 * @return
	 */
	public SkillCount getSkillCount() {
		return skillCount;
	}
	
	public void setSkillCount(Integer funType, SkillCount sc) {
		this.skillCount = (SkillCount) Util.operating(funType, this.skillCount, sc);
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		super.action(objects);
		
		/*
		 * 英雄每升一级，增加1个技能点；
		 */
		SkillCount sc = new SkillCount(1);
		setSkillCount(Util.Sum, sc);
	}
}
