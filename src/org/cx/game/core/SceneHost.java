package org.cx.game.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.cx.game.action.Execute;
import org.cx.game.action.IAction;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.corps.CorpsFactory;
import org.cx.game.corps.PlacementOption;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.I18n;
import org.cx.game.tools.Util;
import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.validator.CorpsCallRangeValidator;
import org.cx.game.validator.CorpsPlacementRangeValidator;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Ground;
import org.cx.game.widget.GroundFactory;
import org.cx.game.widget.HoneycombGround;
import org.cx.game.widget.Place;
import org.cx.game.widget.Scene;
import org.cx.game.widget.building.AbstractBuilding;
import org.cx.game.widget.building.CallOption.OptionCallExecute;

public class SceneHost extends AbstractHost {

	private Scene scene = null;
	private Map<Integer, String> corpsDataMap = new HashMap<Integer, String>();
	private Map<Integer, Integer> troopStatusMap = new HashMap<Integer, Integer>();
	
	public SceneHost(Integer mapId, String account, Integer troop, String playNo) {
		// TODO Auto-generated constructor stub
		super(playNo);
		
		scene = (Scene) GroundFactory.getInstance(mapId);
		
		playerJoinGame(account, troop);
		
		setStatus(Status_WaitJoin);
	}
	
	/**
	 * 选择自己的军队
	 * @param corpsData 样式："兵种，等级，数量，阵营"，也可以："[兵种，等级，数量，阵营],[...]"
	 * @param account
	 */
	public void setCorpsDataOfTroop(Integer troop, String corpsData) {
		this.corpsDataMap.put(troop, corpsData);
	}
	
	/*public List<AbstractOption> getCorpsDataOfTroop(Integer troop) {
		List<AbstractOption> list = new ArrayList<AbstractOption>();
		String data = this.corpsDataMap.get(troop);
		List<String> corpsDataList = XmlConfigureHelper.convertList(data);
		for(String corpsData : corpsDataList){
			String [] datas = corpsData.split(",");
			Integer type = Integer.valueOf(datas[0]);
			Integer level = Integer.valueOf(datas[1]);
			Integer nop = Integer.valueOf(datas[2]);
			AbstractOption option = new PlacementOption(type, level, nop, troop, this);
			list.add(option);
		}
		
		return list;
	}*/
	
	public String getCorpsDataOfTroop(Integer troop) {
		return this.corpsDataMap.get(troop);
	}
	
	
	@Override
	public Ground getGround() {
		// TODO Auto-generated method stub
		return this.scene;
	}

	@Override
	public void ready() {
		// TODO Auto-generated method stub
		super.ready();
		
		getContext().setStatus(AbstractContext.Status_Prepare);
	}
	
	public void deploy(AbstractPlayer player) {
		Integer troop = player.getTroop();
		String data = this.corpsDataMap.get(troop);
		List<Corps> corpsList = XmlConfigureHelper.convertCorpsListByString(data);
		
		List<Integer> entranceList = getGround().getEntranceList(troop);
		List<Integer> indexList = Util.generateRandomNumber(entranceList.size()-1);
		
		
		for(int i=0;i<corpsList.size();i++){
			Integer position = entranceList.get(indexList.get(i));
			Corps corps = corpsList.get(i);
			corps.setPlayer(player);
			corps.setGround(getGround());
			corps.setPosition(position);
			player.getCorpsList().add(corps);
			
			getGround().placementCorps(position, corps);
			
			AbstractOption option = new PlacementOption(corps);
			corps.addOption(option);
		}
	}
	
	public void go(AbstractPlayer player) {
		this.troopStatusMap.put(player.getTroop(), Status_WaitStart);
		
		List<AbstractCorps> corpsList = player.getCorpsList();
		for(AbstractCorps corps : corpsList){
			for(AbstractOption option : corps.getOptionByClass(PlacementOption.class)){
				corps.removeOption(option);
			}
		}
		
		if(isStatus(Status_WaitStart)){
			getContext().setStatus(AbstractContext.Status_Ready);
		}
	}
	
	private Boolean isStatus(Integer status) {
		Boolean ret = true;
		
		List<AbstractPlayer> playerList = getPlayerList();
		for(AbstractPlayer player : playerList){
			if(!status.equals(this.troopStatusMap.get(player.getTroop()))){
				ret = false;
				break;
			}
		}
		
		return ret;
	}
}
