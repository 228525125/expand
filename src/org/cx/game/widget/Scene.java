package org.cx.game.widget;

import java.util.List;
import java.util.Map;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.widget.treasure.Treasure;

/**
 * 战斗场景
 * @author chenxian
 * 
 */
public class Scene extends HoneycombGround {

	private SceneControlQueue queue = new SceneControlQueue();
	
	public Scene(Integer id, String name, Integer xBorder, Integer yBorder,
			String imagePath) {
		super(id, name, xBorder, yBorder, imagePath);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public SceneControlQueue getQueue() {
		// TODO Auto-generated method stub
		return queue;
	}
	
	//----------------------- NPC ---------------------
	
	public void setCorpsData(List<String> corpsData){
		List<Corps> corpsList1 = XmlConfigureHelper.convertCorpsListByString(corpsData.get(0));
		List<Corps> corpsList2 = XmlConfigureHelper.convertCorpsListByString(corpsData.get(1));
		List<Integer> entranceList1 = getEntranceList(corpsList1.get(0).getTroop());
		List<Integer> entranceList2 = getEntranceList(corpsList2.get(0).getTroop());
		
		for(int i=0;i<entranceList1.size() && i<corpsList1.size();i++) {
			AbstractCorps corps = corpsList1.get(i);
			placementCorps(entranceList1.get(i), corps);
		}
		
		for(int i=0;i<entranceList2.size() && i<corpsList2.size();i++) {
			AbstractCorps corps = corpsList2.get(i);
			placementCorps(entranceList2.get(i), corps);
		}
	}
	
	@Override
	public Boolean removeCorps(AbstractCorps corps) {
		// TODO Auto-generated method stub
		getQueue().remove((Corps) corps);
		
		return super.removeCorps(corps);
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
