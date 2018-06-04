package org.cx.game.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.corps.CorpsFactory;
import org.cx.game.corps.Hero;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.widget.AbstractPlace;
import org.cx.game.widget.HoneycombGround;
import org.cx.game.widget.building.IBuilding;
import org.cx.game.widget.building.IOption;
import org.cx.game.widget.building.ReviveOption;
import org.cx.game.widget.treasure.ITreasure;

public class StartState extends AbstractPlayState {
	
	@Override
	public void deploy() {
		// TODO Auto-generated method stub
		context.setPlayState(context.getDeployState());
		context.deploy();
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		//比赛才开始怎么可能结束
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub		
		provision();
		
		//NotifyInfo info = new NotifyInfo(NotifyInfo.Ground_LoadMap,context.getGround());
		//notifyObservers(info);    //通知观察者
		
		context.switchControl();
		
		deploy();
	}
	
	/**
	 * 游戏开始前的准备工作
	 */
	private void provision(){
		Map<String,Object> map = new HashMap<String,Object>();
		Context cont = (Context) context;
		HoneycombGround ground = (HoneycombGround) context.getGround();
		Map<Integer, Integer> landformMap = ground.getLandformMap();
		Map<String, Integer> landform = new HashMap<String, Integer>();
		for(Integer i : landformMap.keySet())       //加载地形
			landform.put(i.toString(), landformMap.get(i));
		
		Map<String, ITreasure> treasureMap = new HashMap<String, ITreasure>();
		for(Integer i : ground.getTreasureMap().keySet())       //加载物品
			treasureMap.put(i.toString(), ground.getTreasureMap().get(i));
		
		for(String data : ground.getBuildingData()){            //加载建筑
			XmlConfigureHelper.map_buildingData_building(data, cont);
		}
		
		for(IBuilding building : ground.getBuildingIsTroop().keySet()){
			Integer troop = ground.getBuildingIsTroop().get(building);
			IPlayer player = cont.getPlayer(troop);
			if(null!=player)
				ground.captureBuilding(player, building);
		}
		
		map.put("landform", landform);
		map.put("buildingList", ground.getBuildingList());
		map.put("treasure", treasureMap);
		NotifyInfo info = new NotifyInfo(NotifyInfo.Context_Start,map);
		super.notifyObservers(info);
		
		for(String data : ground.getNpcData()){     //加载NPC
			XmlConfigureHelper.map_npcData_npc(data, cont);
		}
		
		/*
		 * 英雄登场
		 */
		for(IPlayer p : cont.getPlayerList()){
			Player player = (Player) p;
			Integer troop = player.getTroop();
			List<Integer> list = ground.getEntranceList(troop);
			for(int i=0;i<player.getHeroList().size();i++){
				Hero hero = (Hero) player.getHeroList().get(i);
				Integer position = list.get(i);
				hero.call(ground.getPlace(position), 1);
			}
		}
	}
}
