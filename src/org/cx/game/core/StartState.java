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
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.JsonHelper;
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
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ground", context.getGround());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Context_Start,map);
		super.notifyObservers(info);
		
		context.switchControl();
		
		deploy();
	}
	
	/**
	 * 游戏开始前的准备工作
	 */
	private void provision(){
		Context cont = (Context) context;
		HoneycombGround ground = (HoneycombGround) context.getGround();
		
		for(IBuilding building : ground.getBuildingIsTroop().keySet()){
			Integer troop = ground.getBuildingIsTroop().get(building);
			IPlayer player = cont.getPlayer(troop);
			if(null!=player)
				ground.captureBuilding(player, building);
		}
		
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
				ground.placementCorps(position, hero);
			}
		}
	}
}
