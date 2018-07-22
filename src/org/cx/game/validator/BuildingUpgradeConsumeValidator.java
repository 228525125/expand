package org.cx.game.validator;

import org.cx.game.action.Upgrade;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.tools.I18n;
import org.cx.game.tools.Util;
import org.cx.game.widget.treasure.Mineral;
import org.cx.game.widget.treasure.Resource;

public class BuildingUpgradeConsumeValidator extends Validator {

	private Upgrade upgrade = null;
	private AbstractPlayer player = null;
	
	public BuildingUpgradeConsumeValidator(Upgrade upgrade, AbstractPlayer player) {
		// TODO Auto-generated constructor stub
		this.upgrade = upgrade;
		this.player = player;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true;

		Mineral res = player.getMineral();
			
		if(Util.absoluteLessThan(res, upgrade.getRequirement())){
			ret = false;
			addMessage(I18n.getMessage(BuildingUpgradeConsumeValidator.class.getName()));
		}
		
		return ret;
	}
}
