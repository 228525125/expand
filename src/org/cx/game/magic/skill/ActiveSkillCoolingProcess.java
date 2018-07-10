package org.cx.game.magic.skill;

import org.cx.game.widget.building.AbstractProcess;

public class ActiveSkillCoolingProcess extends AbstractProcess {

	public ActiveSkillCoolingProcess(Integer waitBout, ActiveSkill skill) {
		super(waitBout, skill.getOwner().getPlayer(), skill);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		if(Integer.valueOf(0).equals(getRemainBout())){
			invalid();
			((ActiveSkill) getOwner()).setAllow(true);
		}
	}

}
