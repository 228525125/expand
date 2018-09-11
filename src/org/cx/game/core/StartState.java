package org.cx.game.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.Death;
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
import org.cx.game.widget.Ground;
import org.cx.game.widget.HoneycombGround;
import org.cx.game.widget.SceneFactory;
import org.cx.game.widget.building.AbstractBuilding;
import org.cx.game.widget.building.ReviveOption;
import org.cx.game.widget.treasure.Treasure;

public class StartState extends AbstractPlayState {
	
	@Override
	public void deploy() {
		// TODO Auto-generated method stub
		context.setPlayState(new DeployState());
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
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ground", context.getGround());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Context_Start,map);
		super.notifyObservers(info);
		
		context.switchControl();
		
		deploy();
	}
	
	/**
	 * 游戏开始前的准备工作
	 
	private void provision(){
		Context cont = (Context) context;
		HoneycombGround ground = (HoneycombGround) context.getGround();
		
		for(AbstractBuilding building : ground.getBuildingIsTroop().keySet()){
			Integer troop = ground.getBuildingIsTroop().get(building);
			AbstractPlayer player = cont.getPlayer(troop);
			if(null!=player)
				ground.captureBuilding(player, building);
		}
		
		for(AbstractCorps corps : ground.getLivingCorpsList()){
			Integer troop = corps.getTroop();
			AbstractPlayer player = cont.getPlayer(troop);
			if(null!=player){                  //阵营
				corps.setPlayer(player);
			}else{                             //中立
				player = new Player(troop, Player.Neutral);
				player.setIsComputer(true);
				corps.setPlayer(player);
				context.addPlayer(player);
			}
		}
		
		/*
		 * 英雄登场
		 
		for(AbstractPlayer p : cont.getPlayerList()){
			Player player = (Player) p;
			Integer troop = player.getTroop();
			List<Integer> list = ground.getEntranceList(troop);
			for(int i=0;i<player.getHeroList().size();i++){
				Hero hero = (Hero) player.getHeroList().get(i);
				Integer position = list.get(i);
				ground.placementCorps(position, hero);
				
				player.getCorpsList().add(hero);
			}
		}
	}*/
}
