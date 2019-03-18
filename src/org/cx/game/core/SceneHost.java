package org.cx.game.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Corps;
import org.cx.game.corps.PlacementOption;
import org.cx.game.tools.Util;
import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Ground;
import org.cx.game.widget.Scene;
import org.cx.game.widget.SceneFactory;

public class SceneHost extends AbstractHost {

	private Scene scene = null;
	private Map<Integer, String> corpsDataMap = new HashMap<Integer, String>();
	private Map<Integer, Integer> troopStatusMap = new HashMap<Integer, Integer>();
	
	public SceneHost(String hostName, String playNo, String account, Integer mapId) {
		// TODO Auto-generated constructor stub
		super(hostName, playNo);
		
		scene = SceneFactory.getInstance(mapId);
		
		playerJoinGame(account, 1);
		
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
		
		getContext().setStatus(Context.Status_Prepare);
	}
	
	public void deploy(Integer troop) {
		Player player = getPlayer(troop);
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
	
	public void go(Integer troop) {
		Player player = getPlayer(troop);
		this.troopStatusMap.put(player.getTroop(), Status_WaitStart);
		
		List<Corps> corpsList = player.getCorpsList();
		for(Corps corps : corpsList){
			for(AbstractOption option : corps.getOptionByClass(PlacementOption.class)){
				corps.removeOption(option);
			}
			getGround().getQueue().add(corps);
		}
		
		if(isStatus(Status_WaitStart)){
			getContext().setStatus(Context.Status_Ready);
		}
	}
	
	private Boolean isStatus(Integer status) {
		Boolean ret = true;
		
		List<Player> playerList = getPlayerList();
		for(Player player : playerList){
			if(!status.equals(this.troopStatusMap.get(player.getTroop()))){
				ret = false;
				break;
			}
		}
		
		return ret;
	}
}
