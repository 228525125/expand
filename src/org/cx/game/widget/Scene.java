package org.cx.game.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CommonIdentifier;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.widget.treasure.Treasure;

/**
 * 战斗场景
 * @author chenxian
 * 
 */
public class Scene extends HoneycombGround {

	private SceneControlQueue queue = new SceneControlQueue();
	
	private List<AbstractCorps> deadCorpsList = new ArrayList<AbstractCorps>();
	
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
			AbstractCorps corps = corpsList1.get(i);
			placementCorps(entranceList1.get(i), corps);
		}
		
		for(int i=0;i<entranceList2.size() && i<corpsList2.size();i++) {
			AbstractCorps corps = corpsList2.get(i);
			placementCorps(entranceList2.get(i), corps);
		}
	}*/
	
	//----------------------- Corps -----------------------
	
	@Override
	public Boolean removeCorps(AbstractCorps corps) {
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
	 * 进入指定位置的墓地
	 * @param corps 
	 */
	public void inCemetery(AbstractCorps corps, Integer position) {
		// TODO Auto-generated method stub
		AbstractPlace place = getPlace(position);
		//place.out();
		place.inCemetery(corps);
		
		//this.livingCorpsList.remove(corps);
		this.deadCorpsList.add(corps);
	}
	
	/**
	 * 将战场上的corps丢进墓地,例如Corps在战场上死亡
	 * @param corps
	 */
	public void inCemetery(AbstractCorps corps) {
		// TODO Auto-generated method stub
		AbstractPlace place = getPlace(corps.getPosition());
		if(removeCorps(corps)){
			place.inCemetery(corps);
			this.deadCorpsList.add(corps);
		}
	}
	
	/**
	 * 移出墓地，例如英雄复活
	 * @param corps
	 */
	public void outCemetery(AbstractCorps corps) {
		// TODO Auto-generated method stub
		AbstractPlace place = getPlace(corps.getPosition());
		place.outCemetery(corps);
		
		this.deadCorpsList.remove(corps);
	}
	
	/**
	 * 战场上死亡的corps，但要试图通过getDeadCorpsList()调用方法来改变this.deadCorpsList；
	 * @return
	 */
	public List<AbstractCorps> getDeadCorpsList() {
		// TODO Auto-generated method stub
		List<AbstractCorps> retList = new ArrayList<AbstractCorps>();
		retList.addAll(this.deadCorpsList);
		return retList;
	} 
	
	/**
	 * 获取战场上所有生物
	 * @param player
	 * @return
	 */
	public List<AbstractCorps> getCorpsList(Integer status) {
		// TODO Auto-generated method stub
		List<AbstractCorps> ret = new ArrayList<AbstractCorps>();
		
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
	public List<AbstractCorps> getCorpsList(AbstractPlayer player, Integer status) {
		// TODO Auto-generated method stub
		List<AbstractCorps> ret = new ArrayList<AbstractCorps>();
		
		if(CommonIdentifier.Death_Status_Live.equals(status)){
			for(AbstractCorps corps : getCorpsList()){
				if(player.equals(corps.getPlayer()))
					ret.add(corps);
			}
		}
		
		if(CommonIdentifier.Death_Status_Death.equals(status)){
			for(AbstractCorps corps : getDeadCorpsList()){
				if(player.equals(corps.getPlayer()))
					ret.add(corps);
			}
		}
		
		return ret;
	}
	
	@Override
	public List<Integer> queryRange(AbstractCorps corps, String action) {
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
