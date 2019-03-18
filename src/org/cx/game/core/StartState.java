package org.cx.game.core;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

public class StartState extends AbstractPlayState {
	
	@Override
	public void deploy() {
		// TODO Auto-generated method stub
		getContext().setPlayState(new DeployState());
		getContext().deploy();
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
		map.put("ground", getContext().getGround());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Context_Start,map);
		super.notifyObservers(info);
		
		getContext().switchControl();
		
		deploy();
	}
	
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return (Context) super.getContext();
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
