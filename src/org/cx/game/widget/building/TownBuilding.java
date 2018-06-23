package org.cx.game.widget.building;

import java.util.List;
import java.util.Map;

import org.cx.game.core.IPlayer;

/**
 * 有内部建筑物的建筑物
 * @author chenxian
 *
 */
public class TownBuilding extends AbstractBuilding implements IBuilding {
	
	public TownBuilding(Integer buildingType) {
		super(buildingType);
		// TODO Auto-generated constructor stub
		setStatus(IBuilding.Building_Status_Complete);  //城镇在地图上已经形成
	}
	
	/**
	 * 内部建筑物的typeID，简化xml
	 * @param list
	 */
	public void setBuildings(List<Integer> buildings) {
		// TODO Auto-generated method stub
		for(Integer typeID : buildings){
			IBuilding building = BuildingFactory.getInstance(typeID);
			building.setOwner(this);
			getBuildings().add(building);
		}
	}

	@Override
	public void setPlayer(IPlayer player) {
		// TODO Auto-generated method stub
		super.setPlayer(player);
		
		for(IBuilding building : getBuildings()){
			building.setPlayer(player);
		}
	}
	
	@Override
	public void setUpgradeRequirement(Map<Integer, String> upgradeRequirement) {
		// TODO Auto-generated method stub
		super.setUpgradeRequirement(upgradeRequirement);
	}
}
