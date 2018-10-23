package org.cx.game.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cx.game.action.Merge;
import org.cx.game.core.Context;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.core.Player;
import org.cx.game.corps.Corps;
import org.cx.game.corps.CorpsFactory;
import org.cx.game.widget.Area;
import org.cx.game.widget.Ground;
import org.cx.game.widget.HoneycombGround;
import org.cx.game.widget.building.AbstractBuilding;
import org.cx.game.widget.building.BuildingFactory;
import org.cx.game.widget.building.SpatialBuilding;
 
public class XmlConfigureHelper {
	
	/**
	 * 将map中npcData数据转换为npc对象，并放置到ground中
	 * @param data 样式："兵种，等级，数量，阵营"，还允许"[兵种，等级，数量，阵营];[...]"
	 * @param context
	 */
	public static void map_corpsData_corps(String data, Ground ground){
		Corps corps = convertCorpsByString(data);

		Integer troop = corps.getTroop();
		Integer entrance = ground.getUsableEntrance(troop);
		ground.placementCorps(entrance, corps); //放置到战场
	}
	
	/**
	 * 将String转换为Corps+teammateList
	 * @param data 样式："兵种，等级，数量，阵营"，还允许"[兵种，等级，数量，阵营],[...]"
	 * @param context
	 * @return
	 */
	public static Corps convertCorpsByString(String data) {
		List<String> dataList = convertList(data);
		return convertCorps(dataList);
	}
	
	/**
	 * 将String转换为CorpsList
	 * @param data 样式："兵种，等级，数量，阵营"，还允许"[兵种，等级，数量，阵营],[...]"
	 * @return
	 */
	public static List<Corps> convertCorpsListByString(String data) {
		List<Corps> corpsList = new ArrayList<Corps>();
		List<String> dataList = convertList(data);
		for(int i=0;i<dataList.size();i++) {
			Corps corps = convertCorps(dataList.get(i));
			corpsList.add(corps);
		}
		
		return corpsList;
	}
	
	private static Corps convertCorps(List<String> dataList) {
		List<Corps> list = new ArrayList<Corps>();
		for(int i=0;i<dataList.size();i++) {
			Corps corps = convertCorps(dataList.get(i));
			list.add(corps);
		}
		
		return mergeCorpsList(list);
	}
	
	/**
	 * 将String转换为Corps
	 * @param data 对单个corps的描述
	 * @param context
	 * @return
	 */
	private static Corps convertCorps(String data) {
		String [] datas = data.split(",");
		Integer corpsID = Integer.valueOf(datas[0]);
		Integer level = Integer.valueOf(datas[1]);
		Integer nop = Integer.valueOf(datas[2]);
		Integer troop = Integer.valueOf(datas[3]);
		
		Corps corps = (Corps) CorpsFactory.getInstance(corpsID);
		corps.setTroop(troop);
		corps.getCall().setNop(nop);            //设置数量
		corps.getUpgrade().setLevel(level);     //设置等级
		
		return corps;
	}
	
	/**
	 * 合并corpsList为一个Corps
	 * @param list
	 * @return
	 */
	public static Corps mergeCorpsList(List<Corps> list) {
		Corps leader = getLeader(list);
		list.remove(leader);
		
		for(Corps corps : list) {
			/*
			 * 这里不能使用corps.merge(leader)，因为corps还未与ground关联，
			 * 下面的 写法可避免关联
			 */
			leader.addCorpsToTeammateList(corps);
			((Merge) corps.getMerge()).setLeader(leader);
		}
		
		return leader;
	}
	
	/**
	 * 从list中选出级别最高的一个corps
	 * @param list
	 * @return
	 */
	private static Corps getLeader(List<Corps> list) {
		if(!list.isEmpty()){
			Collections.sort(list, new CorpsGradeComparator());
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 如果String描述了多个Corps，则将其拆分后以List的形式返回，如果String描述
	 * 只包含单个Corps，则返回只包含一个String的List
	 * @param data 样式："兵种，等级，数量，阵营"，还允许"[兵种，等级，数量，阵营],[...]"
	 * @return
	 */
	public static List<String> convertList(String data) {
		List<String> list = new ArrayList<String>();
		if(-1 != data.indexOf(";")) {
			String [] array = data.split(";");
			for(String d : array) {
				list.add(d.substring(1, d.length()-1));
			}
		}else{
			list.add(data);
		}
		
		return list;
	}
	
	public static final Integer NeutralTroop = 0;     //中立
	
	/**
	 * map中buildingData数据转换为building对象，并放置到ground中
	 * @param data
	 * @param context
	 */
	public static void map_buildingData_building(String data, Ground ground){
		Integer position = Integer.valueOf(data.split(",")[0]);
		Integer buildingId = Integer.valueOf(data.split(",")[1]);
		Integer troop = Integer.valueOf(data.split(",")[2]);
			
		AbstractBuilding building = BuildingFactory.getInstance(buildingId);
		ground.placementBuilding(position, building);
		
		ground.getBuildingIsTroop().put(building, troop);
	}
	
	public static void area_spatialNodeData_map(String data, Area area){
		String[] datas = data.split(",");
		Integer nodeId = Integer.valueOf(datas[0]);
		SpatialBuilding spatialBuilding = (SpatialBuilding) area.getSpatialBuilding(nodeId);
		for(int i=1;i<datas.length;i++){
			SpatialBuilding sb = (SpatialBuilding) area.getSpatialBuilding(Integer.valueOf(datas[i]));
			spatialBuilding.addSpatialBuilding(sb);
		}
	}
	
	public static void main(String[] args) {
		String str = "[10190001,1,1,1];[10100001,1,1,1];[10100002,1,1,1]";
		String [] array = str.split(";");
		String ret = array[0].substring(1, array[0].length()-1);
		System.out.println(ret);
	}
}
