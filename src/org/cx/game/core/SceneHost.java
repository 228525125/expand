package org.cx.game.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.Ground;
import org.cx.game.widget.GroundFactory;
import org.cx.game.widget.Scene;
import org.cx.game.widget.building.AbstractBuilding;

public class SceneHost extends AbstractHost {

	private Scene scene = null;
	private Map<Integer,String> corpsDataMap = new HashMap<Integer, String>();
	
	public SceneHost(Integer mapId, String account, Integer troop) {
		// TODO Auto-generated constructor stub
		scene = (Scene) GroundFactory.getInstance(mapId);
		
		playerJoinGame(account, troop);
	}
	
	/**
	 * 选择自己的军队
	 * @param corpsData 样式："兵种，等级，数量，阵营"，也可以："[兵种，等级，数量，阵营],[...]"
	 * @param account
	 */
	public void setCorpsDataOfTroop(Integer troop, String corpsData) {
		this.corpsDataMap.put(troop, corpsData);
	}
	
	@Override
	public Integer getUsableTroop() {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(getGround().getTroopList());
		list.removeAll(getTroopOfPlayerList());
		if(list.isEmpty())
			return null;
		else
			return list.get(0);
	}
	
	@Override
	public Ground getGround() {
		// TODO Auto-generated method stub
		return this.scene;
	}

	@Override
	public void ready() {
		// TODO Auto-generated method stub
		List<String> corpsDataList = new ArrayList<String>();
		corpsDataList.addAll(this.corpsDataMap.values());
		this.scene.setCorpsData(corpsDataList);          //这里需要验证
		
		super.ready();
	}

}
