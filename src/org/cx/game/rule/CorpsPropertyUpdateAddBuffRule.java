package org.cx.game.rule;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.corps.AbstractCorps.CorpsAddBuffAction;

public class CorpsPropertyUpdateAddBuffRule extends AbstractRule {

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
		Corps corps = (Corps) getOwner().getOwner();
		CorpsPropertyUpdateUpgradeRule.updateExtraAtk(corps);
		CorpsPropertyUpdateUpgradeRule.updateExtraDef(corps);
	}
}
