package org.cx.game.core;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.corps.CorpsFactory;
import org.cx.game.corps.Hero;
import org.cx.game.widget.GroundFactory;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IGroundE;

public class Host {

	private IGroundE ground = null;
	
	private List<IPlayerE> playerList = new ArrayList<IPlayerE>();
	private List<Integer> troopOfPlayerList = new ArrayList<Integer>();
	
	public Host(Integer mapId, String account) {
		// TODO Auto-generated constructor stub
		ground = (IGroundE) GroundFactory.getInstance("test");        //硬编码
		playerJoinGame(account);
	}
	
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
	 * 
	 * @return 返回一个没有被玩家占用的阵营编号，如果都被占用了，则返回null
	 */
	public Integer getUsableTroop() {
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(this.ground.getTroopList());
		list.removeAll(this.troopOfPlayerList);
		if(list.isEmpty())
			return null;
		else
			return list.get(0);
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
	
	/**
	 * 游戏准备就绪
	 */
	public void ready() {
		IContext context = new Context(this.ground);
		
		for(IPlayer player : this.playerList){
			context.addPlayer(player);
		}
	}
	
	public IPlayerE queryPlayerForName(String account) {
		// TODO Auto-generated method stub
		for(IPlayerE player : this.playerList){
			if(account.equals(player.getName()))
				return player;
		}
		return null;
	}
}
