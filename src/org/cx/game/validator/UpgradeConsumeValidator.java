package org.cx.game.validator;

import org.cx.game.action.IUpgrade;
import org.cx.game.core.IPlayer;
import org.cx.game.tools.I18n;
import org.cx.game.widget.treasure.IResource;

public class UpgradeConsumeValidator extends Validator {

	private IUpgrade upgrade = null;
	private IPlayer player = null;
	
	public UpgradeConsumeValidator(IUpgrade upgrade, IPlayer player) {
		// TODO Auto-generated constructor stub
		this.upgrade = upgrade;
		this.player = player;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true;

		IResource res = player.getResource();
			
		if(res.absoluteLessThan(upgrade.getRequirement())){
			ret = false;
			addMessage(I18n.getMessage(UpgradeConsumeValidator.class.getName()));
		}
		
		return ret;
	}
}
