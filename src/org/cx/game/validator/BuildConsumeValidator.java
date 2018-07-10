package org.cx.game.validator;

import java.util.Map;
import java.util.Map.Entry;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.tools.I18n;
import org.cx.game.widget.building.AbstractBuilding;
import org.cx.game.widget.treasure.Resource;

/**
 * 验证建造资源是否足够
 * @author chenxian
 *
 */
public class BuildConsumeValidator extends Validator {
	
	private AbstractBuilding building = null;
	
	public BuildConsumeValidator(AbstractBuilding building) {
		// TODO Auto-generated constructor stub
		this.building = building;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true;
		
		AbstractPlayer player = building.getPlayer();
		Resource consume = building.getConsume();
		Resource res = player.getResource();
		
		if(res.absoluteLessThan(consume)){
			ret = false;
			addMessage(I18n.getMessage(BuildConsumeValidator.class.getName()));
		}
			
		return ret;
	}

}
