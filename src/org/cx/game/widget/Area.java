package org.cx.game.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.widget.building.IBuilding;
import org.cx.game.widget.building.SpatialBuilding;

public class Area {
	
	private Integer id = null;
	private String imagePath = "";
	private Integer startingPoint = null;
	
	private List<SpatialBuilding> spatialBuildingList = new ArrayList<SpatialBuilding>();
	private List<IGround> groundList = new ArrayList<IGround>();
	private Map<Integer, IGround> idGroundMap = new HashMap<Integer, IGround>();
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
	
	public IGround getGround(Integer mapId) {
		return idGroundMap.get(mapId);
	}
	
	public List<IGround> getGroundList() {
		return groundList;
	}
	
	public void setAtlas(List<Integer> atlas) {
		for(Integer mapId : atlas){
			IGround ground = GroundFactory.getInstance(mapId);
			this.groundList.add(ground);
			this.idGroundMap.put(mapId, ground);
			
			HoneycombGround hg = (HoneycombGround) ground;
			hg.setArea(this);
			
			for(IBuilding building : hg.getBuildingList(SpatialBuilding.class)){
				this.spatialBuildingList.add((SpatialBuilding) building);
			}
		}
	}
	
	public List<SpatialBuilding> getSpatialBuildingList() {
		return this.spatialBuildingList;
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
	
	public IGround getStartingPoint() {
		return getGround(this.startingPoint);
	}
	
	public void setSpatialNodeData(List<String> spatialNodeData) {
		this.spatialNodeData = spatialNodeData;
	}
	
	public SpatialBuilding getSpatialBuilding(Integer typeId) {
		for(SpatialBuilding building : getSpatialBuildingList()){
			if(typeId.equals(building.getType()))
				return building;
		}
		return null;
	}
}
