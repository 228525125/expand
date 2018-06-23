package org.cx.game.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.widget.building.IBuilding;
import org.cx.game.widget.building.SpatialBuilding;

public class Area {
	
	private Integer id = null;
	private String imagePath = "";
	private Integer startingPoint = null;
	
	private List<SpatialBuilding> spatialNodeList = new ArrayList<SpatialBuilding>();
	private List<IGround> groundList = new ArrayList<IGround>();
	private Map<Integer, IGround> idGroundMap = new HashMap<Integer, IGround>();
	private List<Integer> troopList = new ArrayList<Integer>();         //阵营
	
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
		for(SpatialBuilding spatialBuilding : getSpatialNodeList()){
			for(Integer typeId : spatialBuilding.getSpatialBuildingTypeList()){
				spatialBuilding.getSpatialBuildingList().add(getSpatialNode(typeId));
			}
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
			
			((HoneycombGround) ground).setArea(this);
		}
	}
	
	public List<SpatialBuilding> getSpatialNodeList() {
		return this.spatialNodeList;
	}
	
	public SpatialBuilding getSpatialNode(Integer typeId) {
		for(SpatialBuilding building : getSpatialNodeList()){
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
	
	public IGround getStartingPoint() {
		return getGround(this.startingPoint);
	}
}
