package org.cx.game.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.widget.building.AbstractBuilding;
import org.cx.game.widget.building.SpatialBuilding;
 
public class Area {
	
	private Integer id = null;
	private String imagePath = "";
	private Integer startingPoint = null;
	
	private List<AbstractBuilding> spatialBuildingList = new ArrayList<AbstractBuilding>();
	private List<Ground> groundList = new ArrayList<Ground>();
	private Map<Integer, Ground> idGroundMap = new HashMap<Integer, Ground>();
	private List<Integer> troopList = new ArrayList<Integer>();         //阵营
	
	private List<String> spatialNodeData = new ArrayList<String>();
	
	public Area(Integer id, String imagePath) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.imagePath = imagePath;
	}
	
	/**
	 * 该方法在对象被创建后调用
	 */
	public void afterConstruct() {
		/*
		 * 建立传送站之间的关联
		 */
		for(String data : this.spatialNodeData){
			XmlConfigureHelper.area_spatialNodeData_map(data, this);
		}
	}
	
	public Ground getGround(Integer mapId) {
		return idGroundMap.get(mapId);
	}
	
	public List<Ground> getGroundList() {
		return groundList;
	}
	
	public void setAtlas(List<Integer> atlas) {
		
		for(Integer mapId : atlas){
			Ground ground = GroundFactory.getInstance(mapId);
			this.groundList.add(ground);
			this.idGroundMap.put(mapId, ground);
			
			ground.setArea(this);
			
			
		}
		
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
	
	public void addSpatialBuilding(AbstractBuilding spatialBuilding) {
		this.spatialBuildingList.add(spatialBuilding);
	}
	
	public List<AbstractBuilding> getSpatialBuildingList() {
		return this.spatialBuildingList;
	}
	
	public AbstractBuilding getSpatialBuilding(Integer typeId) {
		for(AbstractBuilding building : getSpatialBuildingList()){
			if(typeId.equals(building.getType()))
				return building;
		}
		return null;
	}
	
	public List<Integer> getTroopList() {
		return this.troopList;
	}
	
	public void setTroopList(List<Integer> troopList) {
		this.troopList = troopList;
	}
	
	public void setStartingPoint(Integer mapId) {
		this.startingPoint = mapId;
	}
	
	public Ground getStartingPoint() {
		return getGround(this.startingPoint);
	}
}
