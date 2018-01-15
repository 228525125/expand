package org.cx.game.validator;

import java.util.Arrays;
import java.util.List;

import org.cx.game.tools.I18n;
import org.cx.game.widget.building.IBuilding;

/**
 * 验证建筑类型
 * @author chenxian
 *
 */
public class SelectBuildingTypeValidator extends Validator {
	
	private List<Integer> buildingTypes = null;
	private IBuilding building = null;

	public SelectBuildingTypeValidator(IBuilding building, Integer [] buildingTypes) {
		// TODO Auto-generated constructor stub
		this.building = building;
		this.buildingTypes = Arrays.asList(buildingTypes);
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(buildingTypes.contains(building.getType()))
			return true;
		else{
			addMessage(I18n.getMessage(SelectBuildingTypeValidator.class.getName()));
			return false;
		}
	}
}
