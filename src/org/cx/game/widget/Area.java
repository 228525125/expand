package org.cx.game.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.widget.building.AbstractBuilding;
import org.cx.game.widget.building.SpatialBuilding;
 
public class Area extends AbstractArea {
	
	private List<String> spatialNodeData = new ArrayList<String>();
	
	public Area(Integer id, String imagePath) {
		// TODO Auto-generated constructor stub
		super(id, imagePath);
	}
	
	/**
	 * 该方法在对象被创建后调用
	 */
	@Override
	public void afterConstruct() {
		/*
		 * 建立传送站之间的关联
		 */
		for(String data : this.spatialNodeData){
			XmlConfigureHelper.area_spatialNodeData_map(data, this);
		}
	}
	
	@Override
	public void setAtlas(List<Integer> atlas) {
		super.setAtlas(atlas);
		
		for(Integer mapId : atlas){
			Ground ground = (Ground) getGround(mapId);

			for(AbstractBuilding building : ground.getBuildingList(SpatialBuilding.class)){
				addSpatialBuilding(building);
			}
		}
	}
	
	public void setSpatialNodeData(List<String> spatialNodeData) {
		this.spatialNodeData = spatialNodeData;
	}
	
	@Override
	public void setTroopList(List<Integer> troopList) {
		// TODO Auto-generated method stub
		super.setTroopList(troopList);
	}
	
	@Override
	public void setStartingPoint(Integer mapId) {
		// TODO Auto-generated method stub
		super.setStartingPoint(mapId);
	}
}
