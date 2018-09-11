package org.cx.game.core;

import java.util.ArrayList;
import java.util.List;

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
	
	private Boolean isReady = false;
	
	private AbstractContext context = null;
	
	private List<AbstractPlayer> playerList = new ArrayList<AbstractPlayer>();
	private List<Integer> troopOfPlayerList = new ArrayList<Integer>();
	
	/**
	 * 加入游戏主机
	 * @param account 玩家帐号
	 */
	public void playerJoinGame(String account, Integer troop) {
		Player player = new Player(troop, account); 
		this.playerList.add(player);
		this.troopOfPlayerList.add(troop);
		player.setHost(this);
	}
	
	/**
	 * 玩家退出游戏主机
	 * @param troop 游戏中的编号
	 */
	public void playerQuitGame(String account) {
		AbstractPlayer player = queryPlayerForName(account);
		this.troopOfPlayerList.remove(player.getTroop());
		this.playerList.remove(player);
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
	
	List<Integer> getTroopOfPlayerList() {
		return troopOfPlayerList;
	}
	
	
	public AbstractPlayer queryPlayerForName(String account) {
		// TODO Auto-generated method stub
		for(AbstractPlayer player : this.playerList){
			if(account.equals(player.getName()))
				return player;
		}
		return null;
	}
	
	public Boolean isReady() {
		return isReady;
	}
	
	/**
	 * 建议放在子类方法的最后
	 */
	public void ready() {
		this.isReady = true;
		
		this.context = new Context(getGround());
		
		for(AbstractPlayer player : getPlayerList()){
			context.addPlayer(player);
		}
		
		for(AbstractBuilding building : getGround().getBuildingIsTroop().keySet()){
			Integer troop = getGround().getBuildingIsTroop().get(building);
			AbstractPlayer player = getContext().getPlayer(troop);
			if(null!=player)
				getGround().captureBuilding(player, building);
		}
		
		for(AbstractCorps corps : getGround().getLivingCorpsList()){
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
		}
	}
	
	public AbstractContext getContext() {
		return context;
	}
	
	
	abstract public Ground getGround();
	abstract public Integer getUsableTroop();
}
