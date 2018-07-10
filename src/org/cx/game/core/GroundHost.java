package org.cx.game.core;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.GroundFactory;

public class GroundHost extends AbstractHost {

	private AbstractGround ground = null;
	
	private List<AbstractPlayer> playerList = new ArrayList<AbstractPlayer>();
	private List<Integer> troopOfPlayerList = new ArrayList<Integer>();
	
	public GroundHost(Integer mapId, String account) {
		// TODO Auto-generated constructor stub
		ground = GroundFactory.getInstance(mapId);
		playerJoinGame(account);
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
	 * 游戏准备就绪
	 */
	public void ready() {
		AbstractContext context = new Context(this.ground);
		
		for(AbstractPlayer player : this.playerList){
			context.addPlayer(player);
		}
	}
}
