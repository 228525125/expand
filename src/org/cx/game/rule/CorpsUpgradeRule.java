package org.cx.game.rule;

import org.cx.game.action.CorpsUpgrade;

public class CorpsUpgradeRule extends AbstractRule {

	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return CorpsUpgrade.class;
	}
	
	@Override
	public CorpsUpgrade getOwner() {
		// TODO Auto-generated method stub
		return (CorpsUpgrade) super.getOwner();
	}

}
