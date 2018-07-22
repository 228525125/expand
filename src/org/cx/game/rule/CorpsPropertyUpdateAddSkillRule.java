package org.cx.game.rule;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.corps.AbstractCorps.CorpsAddSkillAction;

public class CorpsPropertyUpdateAddSkillRule extends AbstractRule {

	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return CorpsAddSkillAction.class;
	}
	
	@Override
	public CorpsAddSkillAction getOwner() {
		// TODO Auto-generated method stub
		return (CorpsAddSkillAction) super.getOwner();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Corps corps = (Corps) getOwner().getOwner();
		CorpsPropertyUpdateUpgradeRule.updateExtraAtk(corps);
		CorpsPropertyUpdateUpgradeRule.updateExtraDef(corps);
	}

}
