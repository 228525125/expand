package org.cx.game.tools;

import org.cx.game.core.Context;
import org.cx.game.core.IPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.corps.CorpsFactory;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.AbstractPlace;
import org.cx.game.widget.HoneycombGround;

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
		Corps npc = (Corps) CorpsFactory.getInstance(corpsID, player);
		
		HoneycombGround ground = (HoneycombGround) context.getGround();
		AbstractPlace place = (AbstractPlace) ground.getPlace(position);
		
		try {
			npc.call(place, nop);
			npc.getUpgrade().setLevel(level);
		} catch (RuleValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * map中buildingData数据转换为building对象，并放置到ground中
	 * @param data
	 * @param context
	 */
	public static void map_buildingData_building(String data, Context context){
		Integer position = Integer.valueOf(data.split(",")[0]);
		Integer troop = Integer.valueOf(data.split(",")[2]);

		IPlayer player = context.getPlayer(troop);
		HoneycombGround ground = (HoneycombGround) context.getGround();
		
		ground.captureBuilding(position, player);
	}
}
