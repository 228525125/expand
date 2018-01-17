package org.cx.game.magic.skill;

import org.cx.game.widget.building.AbstractProcess;
import org.cx.game.widget.building.IOption;
import org.cx.game.widget.building.ProcessOptionSpacing;

public class ProcessActiveSkillCooling extends AbstractProcess {

	public ProcessActiveSkillCooling(Integer waitBout, IActiveSkill skill) {
		super(waitBout, skill.getOwner().getPlayer(), skill);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public IActiveSkill getOwner() {
		// TODO Auto-generated method stub
		return (IActiveSkill) super.getOwner();
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		if(Integer.valueOf(0).equals(getRemainBout())){
			invalid();
			getOwner().setAllow(true);
		}
	}

}
