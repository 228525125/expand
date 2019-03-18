package org.cx.game.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.widget.Ground;

public abstract class AbstractHost implements IHost {
	
	public final static Integer Status_WaitJoin = 1;           
	public final static Integer Status_WaitReady = 2;
	public final static Integer Status_WaitDeploy = 3;
	protected final static Integer Status_WaitStart = 4;
	//protected final static Integer Status_Playing = 5;
	//protected final static Integer STATUS_Finish = 6;
	

	private String playNo = null;
	private String name = null;
	private Integer status = Status_WaitJoin;
	
	private Context context = null;
	
	private List<Player> playerList = new ArrayList<Player>();
	private Map<Integer, Player> troopPlayerMap = new HashMap<Integer, Player>();
	private Map<String, Integer> accountTroopMap = new HashMap<String, Integer>();
	
	public AbstractHost(String hostName, String playNo) {
		// TODO Auto-generated constructor stub
		this.name = hostName;
		this.playNo = playNo;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * 加入游戏主机
	 * @param account 玩家帐号
	 */
	public void playerJoinGame(String account, Integer troop) {
		Player player = new Player(troop, account, this); 
		this.playerList.add(player);
		this.troopPlayerMap.put(troop, player);
		this.accountTroopMap.put(account, troop);
	}
	
	/**
	 * 玩家退出游戏主机
	 * @param troop 游戏中的编号
	 */
	public void playerQuitGame(String account) {
		Integer troop = getTroopForAccount(account);
		Player player = getPlayer(troop);
		
		this.troopPlayerMap.remove(troop);
		this.accountTroopMap.remove(account);
		this.playerList.remove(player);
	}
	
	public Integer getTroopForAccount(String account) {
		return this.accountTroopMap.get(account);
	}
	
	/**
	 * 返回一个还没有被占用的阵营
	 * @return
	 */
	public Integer getUsableTroop() {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(getGround().getTroopList());
		list.removeAll(this.troopPlayerMap.keySet());
		if(list.isEmpty())
			return null;
		else
			return list.get(0);
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getPlayNo() {
		return playNo;
	}
	
	public Context getContext() {
		return context;
	}
	
	/**
	 * 修改玩家阵营
	 * @param troop 阵营编号
	 * @param player
	 */
	public void setTroopOfPlayer(String account, Integer troop) {
		Integer tp = getTroopForAccount(account);
		if(null!=tp){
			Player player =this.troopPlayerMap.get(tp);
			this.troopPlayerMap.remove(tp);
			player.setTroop(troop);
			
			this.troopPlayerMap.put(troop, player);
			this.accountTroopMap.put(account, troop);
		}
	}
	
	List<Player> getPlayerList() {
		return playerList;
	}
	
	public Player getPlayer(Integer troop) {
		return this.troopPlayerMap.get(troop);
	}
	
	/**
	 * 建议放在子类方法的最后
	 */
	public void ready() {
		
		setStatus(Status_WaitDeploy);
		
		this.context = new Context(this.playNo, getGround());
		
		for(Player player : getPlayerList()){
			context.addPlayer(player);
		}
		
		/*for(AbstractBuilding building : getGround().getBuildingIsTroop().keySet()){
			Integer troop = getGround().getBuildingIsTroop().get(building);
			AbstractPlayer player = getContext().getPlayer(troop);
			if(null!=player)
				getGround().captureBuilding(player, building);
		}*/
		
		/*for(AbstractCorps corps : getGround().getLivingCorpsList()){
			Integer troop = corps.getTroop();
			AbstractPlayer player = getContext().getPlayer(troop);
			if(null!=player){                  //阵营
				corps.setPlayer(player);
			}else{                             //中立
				player = new Player(troop, Player.Neutral);
				player.setIsComputer(true);
				corps.setPlayer(player);
				getContext().addPlayer(player);
			}
		}*/
	}
	
	abstract public Ground getGround();
	
}
