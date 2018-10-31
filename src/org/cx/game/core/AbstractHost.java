package org.cx.game.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.CorpsFactory;
import org.cx.game.corps.Hero;
import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.Ground;
import org.cx.game.widget.GroundFactory;
import org.cx.game.widget.Scene;
import org.cx.game.widget.building.AbstractBuilding;

public abstract class AbstractHost {
	
	public final static Integer Status_WaitJoin = 1;           
	public final static Integer Status_WaitReady = 2;
	public final static Integer Status_WaitDeploy = 3;
	//public final static Integer Status_CompleteDeploy = 5;
	protected final static Integer Status_WaitStart = 4;

	private String playNo = null;
	private Integer status = Status_WaitJoin;
	
	private AbstractContext context = null;
	
	private List<AbstractPlayer> playerList = new ArrayList<AbstractPlayer>();
	private Map<Integer, AbstractPlayer> troopPlayerMap = new HashMap<Integer, AbstractPlayer>();
	
	public AbstractHost(String playNo) {
		// TODO Auto-generated constructor stub
		this.playNo = playNo;
	}
	
	/**
	 * 加入游戏主机
	 * @param account 玩家帐号
	 */
	public void playerJoinGame(String account, Integer troop) {
		Player player = new Player(troop, account); 
		this.playerList.add(player);
		this.troopPlayerMap.put(troop, player);
		player.setHost(this);
	}
	
	/**
	 * 玩家退出游戏主机
	 * @param troop 游戏中的编号
	 */
	public void playerQuitGame(String account) {
		AbstractPlayer player = queryPlayerForName(account);
		this.troopPlayerMap.remove(player.getTroop());
		this.playerList.remove(player);
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
	
	public AbstractPlayer queryPlayerForName(String account) {
		// TODO Auto-generated method stub
		for(AbstractPlayer player : this.playerList){
			if(account.equals(player.getName()))
				return player;
		}
		return null;
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
	
	public AbstractContext getContext() {
		return context;
	}
	
	/**
	 * 修改玩家阵营
	 * @param troop 阵营编号
	 * @param player
	 */
	public void setTroopOfPlayer(Integer troop, String account) {
		AbstractPlayer player = queryPlayerForName(account);
		player.setTroop(troop);
	}
	
	List<AbstractPlayer> getPlayerList() {
		return playerList;
	}
	
	public AbstractPlayer getPlayer(Integer troop) {
		return this.troopPlayerMap.get(troop);
	}
	
	/**
	 * 建议放在子类方法的最后
	 */
	public void ready() {
		
		setStatus(Status_WaitDeploy);
		
		this.context = new Context(this.playNo, getGround());
		
		for(AbstractPlayer player : getPlayerList()){
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
	
	abstract public void go(AbstractPlayer player);
	
	abstract public Ground getGround();
	
}
