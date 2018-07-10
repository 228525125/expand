package org.cx.game.validator;

import java.util.Arrays;
import java.util.List;

import org.cx.game.tools.I18n;
import org.cx.game.widget.building.AbstractBuilding;

/**
 * 验证建筑类型
 * @author chenxian
 *
 */
public class SelectBuildingTypeValidator extends Validator {
	
	private Integer type = null;
	private AbstractBuilding building = null;

	public SelectBuildingTypeValidator(AbstractBuilding building, Integer type) {
		// TODO Auto-generated constructor stub
		this.building = building;
		this.type = type;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(type.equals(this.building.getCategory()))
			return true;
		else{
			addMessage(I18n.getMessage(SelectBuildingTypeValidator.class.getName()));
			return false;
		}
	}
}
