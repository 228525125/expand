package org.cx.game.rule;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Corps.CorpsAddBuffAction;
import org.cx.game.magic.buff.IBuff;
import org.cx.game.magic.skill.IPassiveSkill;
import org.cx.game.magic.skill.ISkill;

public class CorpsPropertyUpdateAddBuffRule extends AbstractRule implements IRule {

	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return CorpsAddBuffAction.class;
	}
	
	@Override
	public CorpsAddBuffAction getOwner() {
		// TODO Auto-generated method stub
		return (CorpsAddBuffAction) super.getOwner();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Corps corps = getOwner().getOwner();
		CorpsPropertyUpdateUpgradeRule.updateExtraAtk(corps);
		CorpsPropertyUpdateUpgradeRule.updateExtraDef(corps);
	}
}
