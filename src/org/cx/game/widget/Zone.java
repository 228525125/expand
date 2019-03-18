package org.cx.game.widget;

import java.util.List;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.widget.treasure.Treasure;

public class Zone extends Ground {

	private ZoneControlQueue queue = new ZoneControlQueue();
	
	public Zone(Integer id, String name, Integer xBorder, Integer yBorder,
			String imagePath) {
		super(id, name, xBorder, yBorder, imagePath);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ZoneControlQueue getQueue() {
		// TODO Auto-generated method stub
		return queue;
	}
	
	@Override
	public void setTroopList(List<Integer> troopList) {
		// TODO Auto-generated method stub
		super.setTroopList(troopList);
	}
	
	@Override
	public void setEntranceMap(Map<Integer, Integer> entranceMap) {
		// TODO Auto-generated method stub
		super.setEntranceMap(entranceMap);
	}
	
	//----------------------- Building ------------------------

	public void setBuildingData(List<String> buildings) {
		super.setBuildingData(buildings);
	}
	
	//----------------------- Landform ---------------------------
	
	public void setLandformMap(Map<Integer, Integer> landformMap) {
		// TODO Auto-generated method stub
		super.setLandformMap(landformMap);
	}
	
	//----------------------- TreasureMap ------------------------
	@Override
	public void setTreasureMap(Map<Integer, Treasure> treasureMap) {
		// TODO Auto-generated method stub
		super.setTreasureMap(treasureMap);
	}

}
