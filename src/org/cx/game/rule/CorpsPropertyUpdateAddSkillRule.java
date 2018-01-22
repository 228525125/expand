package org.cx.game.rule;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Corps.CorpsAddSkillAction;

public class CorpsPropertyUpdateAddSkillRule extends AbstractRule implements
		IRule {

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
		Corps corps = getOwner().getOwner();
		CorpsPropertyUpdateUpgradeRule.updateExtraAtk(corps);
		CorpsPropertyUpdateUpgradeRule.updateExtraDef(corps);
	}

}
