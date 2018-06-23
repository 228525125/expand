package org.cx.game.core;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.corps.CorpsFactory;
import org.cx.game.corps.Hero;

public abstract class AbstractHost {
	
	private List<IPlayerE> playerList = new ArrayList<IPlayerE>();
	private List<Integer> troopOfPlayerList = new ArrayList<Integer>();
	
	/**
	 * 加入游戏主机
	 * @param account 玩家帐号
	 */
	public void playerJoinGame(String account) {
		Integer troop = getUsableTroop();
		IPlayerE player = new Player(troop, account); 
		this.playerList.add(player);
		this.troopOfPlayerList.add(troop);
	}
	
	/**
	 * 玩家退出游戏主机
	 * @param troop 游戏中的编号
	 */
	public void playerQuitGame(String account) {
		IPlayer player = queryPlayerForName(account);
		this.troopOfPlayerList.remove(player.getTroop());
		this.playerList.remove(player);
	}
	
	/**
	 * 修改玩家阵营
	 * @param troop 阵营编号
	 * @param player
	 */
	public void setTroopOfPlayer(Integer troop, String account) {
		IPlayer player = queryPlayerForName(account);
		player.setTroop(troop);
	}
	
	/**
	 * 玩家选择英雄 (目前只支持一位英雄)
	 * @param heroId 英雄编号
	 * @param player
	 */
	public void setHeroOfPlayer(Integer heroId, String account) {
		IPlayerE player = queryPlayerForName(account);
		Hero hero = (Hero) CorpsFactory.getInstance(heroId, player);
		player.addHero(hero);
	}
	
	List<IPlayerE> getPlayerList() {
		return playerList;
	}
	
	List<Integer> getTroopOfPlayerList() {
		return troopOfPlayerList;
	}
	
	
	public IPlayerE queryPlayerForName(String account) {
		// TODO Auto-generated method stub
		for(IPlayerE player : this.playerList){
			if(account.equals(player.getName()))
				return player;
		}
		return null;
	}
	
	abstract public Integer getUsableTroop();
	abstract public void ready();
}
