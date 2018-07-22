package org.cx.game.rule;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.corps.AbstractCorps.CorpsRemoveBuffAction;

public class CorpsPropertyUpdateRemoveBuffRule extends AbstractRule {

	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return CorpsRemoveBuffAction.class;
	}
	
	@Override
	public CorpsRemoveBuffAction getOwner() {
		// TODO Auto-generated method stub
		return (CorpsRemoveBuffAction) super.getOwner();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Corps corps = (Corps) getOwner().getOwner();
		
		CorpsPropertyUpdateUpgradeRule.updateExtraAtk(corps);
		CorpsPropertyUpdateUpgradeRule.updateExtraDef(corps);
	}

}
