package org.cx.game.validator;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.tools.I18n;
import org.cx.game.tools.Util;
import org.cx.game.widget.building.AbstractBuilding;
import org.cx.game.widget.treasure.Mineral;

public class BuildingUpgradeConsumeValidator extends Validator {

	private AbstractBuilding building = null;
	private AbstractPlayer player = null;
	
	public BuildingUpgradeConsumeValidator(AbstractBuilding building) {
		// TODO Auto-generated constructor stub
		this.building = building;
		this.player = building.getPlayer();
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true;

		Mineral res = player.getMineral();
			
		if(Util.absoluteLessThan(res, building.getUpgrade().getRequirement())){
			ret = false;
			addMessage(I18n.getMessage(BuildingUpgradeConsumeValidator.class.getName()));
		}
		
		return ret;
	}
}
