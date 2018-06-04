package org.cx.game.tools;

import org.cx.game.core.Context;
import org.cx.game.core.IContextE;
import org.cx.game.core.IPlayer;
import org.cx.game.core.Player;
import org.cx.game.corps.Corps;
import org.cx.game.corps.CorpsFactory;
import org.cx.game.widget.HoneycombGround;
import org.cx.game.widget.building.BuildingFactory;
import org.cx.game.widget.building.IBuilding;

public class XmlConfigureHelper {
	
	/**
	 * 将map中npcData数据转换为npc对象，并放置到ground中
	 * @param data
	 * @param context
	 */
	public static void map_npcData_npc(String data, Context context){
		String [] datas = data.split(",");
		Integer position = Integer.valueOf(datas[0]);
		Integer corpsID = Integer.valueOf(datas[1]);
		Integer level = Integer.valueOf(datas[2]);
		Integer nop = Integer.valueOf(datas[3]);
		Integer troop = Integer.valueOf(datas[4]);
		
		IPlayer player = context.getPlayer(troop);
		if(null==player){                                   //如果非阵营玩家（中立部队），则自动生成一个player
			player = new Player(troop, Player.Neutral);
			player.setComputer(true);
			context.addPlayer(player);
		}
		Corps npc = (Corps) CorpsFactory.getInstance(corpsID, player);
		
		HoneycombGround ground = (HoneycombGround) context.getGround();
		
		//npc.getCall().setNop(nop);            //设置数量
		npc.getUpgrade().setLevel(level);     //设置等级
		npc.call(ground.getPlace(position), nop);
		//ground.placementCorps(position, npc); //放置到战场
	}
	
	public static final Integer NeutralTroop = 0;     //中立
	
	/**
	 * map中buildingData数据转换为building对象，并放置到ground中
	 * @param data
	 * @param context
	 */
	public static void map_buildingData_building(String data, Context context){
		HoneycombGround ground = (HoneycombGround) context.getGround();
		
		Integer position = Integer.valueOf(data.split(",")[0]);
		Integer buildingType = Integer.valueOf(data.split(",")[1]);
		Integer troop = Integer.valueOf(data.split(",")[2]);
			
		IBuilding building = BuildingFactory.getInstance(buildingType);
		ground.placementBuilding(position, building);
		
		ground.getBuildingIsTroop().put(building, troop);
	}
}
