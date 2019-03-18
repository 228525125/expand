package org.cx.game.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cx.game.core.Player;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CommonIdentifier;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.widget.treasure.Treasure;

/**
 * 战斗场景
 * 该场景增加了墓地，但墓地中的Corps不在Ground中；
 * @author chenxian
 * 
 */
public class Scene extends Ground {

	private SceneControlQueue queue = new SceneControlQueue();
	
	private List<Corps> deadCorpsList = new ArrayList<Corps>();
	
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
	
	/*public void setCorpsData(List<String> corpsData){
		List<Corps> corpsList1 = XmlConfigureHelper.convertCorpsListByString(corpsData.get(0));
		List<Corps> corpsList2 = XmlConfigureHelper.convertCorpsListByString(corpsData.get(1));
		List<Integer> entranceList1 = getEntranceList(corpsList1.get(0).getTroop());
		List<Integer> entranceList2 = getEntranceList(corpsList2.get(0).getTroop());
		
		for(int i=0;i<entranceList1.size() && i<corpsList1.size();i++) {
			Corps corps = corpsList1.get(i);
			placementCorps(entranceList1.get(i), corps);
		}
		
		for(int i=0;i<entranceList2.size() && i<corpsList2.size();i++) {
			Corps corps = corpsList2.get(i);
			placementCorps(entranceList2.get(i), corps);
		}
	}*/
	
	//----------------------- Corps -----------------------
	
	@Override
	public Boolean removeCorps(Corps corps) {
		// TODO Auto-generated method stub
		Boolean ret = super.removeCorps(corps);
		if(!ret){                                       //处理墓地的情况
			ret = this.deadCorpsList.remove(corps);
			if(ret){
				outCemetery(corps);
			}
		}
		return ret;
	}
	
	/**
	 * 进入指定位置的墓地，墓地的Corps并不在Ground中
	 * @param corps 
	 */
	public void inCemetery(Corps corps, Integer position) {
		// TODO Auto-generated method stub
		Place place = getPlace(position);
		place.inCemetery(corps);
		
		this.deadCorpsList.add(corps);
	}
	
	/**
	 * 将战场上的corps丢进墓地，例如Corps在战场上死亡，死亡后的Corps不在Ground中，只能通过getDeadCorpsList查找
	 * @param corps
	 */
	public void inCemetery(Corps corps) {
		// TODO Auto-generated method stub
		Place place = getPlace(corps.getPosition());
		if(removeCorps(corps)){
			place.inCemetery(corps);
			this.deadCorpsList.add(corps);
		}
	}
	
	/**
	 * 移出墓地，例如英雄复活
	 * @param corps
	 */
	public void outCemetery(Corps corps) {
		// TODO Auto-generated method stub
		Place place = getPlace(corps.getPosition());
		place.outCemetery(corps);
		
		this.deadCorpsList.remove(corps);
	}
	
	/**
	 * 战场上死亡的corps，但要试图通过getDeadCorpsList()调用方法来改变this.deadCorpsList；
	 * @return
	 */
	public List<Corps> getDeadCorpsList() {
		// TODO Auto-generated method stub
		List<Corps> retList = new ArrayList<Corps>();
		retList.addAll(this.deadCorpsList);
		return retList;
	} 
	
	/**
	 * 获取战场上所有生物
	 * @param player
	 * @return
	 */
	public List<Corps> getCorpsList(Integer status) {
		// TODO Auto-generated method stub
		List<Corps> ret = new ArrayList<Corps>();
		
		if(CommonIdentifier.Death_Status_Live.equals(status))
			ret.addAll(getCorpsList());
		
		if(CommonIdentifier.Death_Status_Death.equals(status))
			ret.addAll(getDeadCorpsList());
		
		return ret;
	}
	
	/**
	 * 获取某玩家战场上所有生物
	 * @param player
	 * @return
	 */
	public List<Corps> getCorpsList(Player player, Integer status) {
		// TODO Auto-generated method stub
		List<Corps> ret = new ArrayList<Corps>();
		
		if(CommonIdentifier.Death_Status_Live.equals(status)){
			for(Corps corps : getCorpsList()){
				if(player.equals(corps.getPlayer()))
					ret.add(corps);
			}
		}
		
		if(CommonIdentifier.Death_Status_Death.equals(status)){
			for(Corps corps : getDeadCorpsList()){
				if(player.equals(corps.getPlayer()))
					ret.add(corps);
			}
		}
		
		return ret;
	}
	
	@Override
	public List<Integer> queryRange(Corps corps, String action) {
		// TODO Auto-generated method stub
		List<Integer> positionList = super.queryRange(corps, action);
		Corps sc = (Corps) corps;
		
		if(CommonIdentifierE.Command_Query_Attack.equals(action)){
			Integer range = sc.getAttack().getRange();
			/*
			 * 这里要考虑远程单位射程切换为近战时的变化
			 * 当远程单位在战场上时，如果附近有敌方单位，则只能近身攻击
			 */
			if(CommonIdentifierE.Attack_Mode_Far.equals(sc.getAttack().getMode())){
				range = sc.isLock() ? 1 : range;
			}
			
			positionList = areaForDistance(corps.getPosition(), range, Contain);  //1：表示范围内的所有单元格，0：表示等于范围的单元格
			positionList.remove(corps.getPosition());
		}
		
		return positionList;
	}
}
