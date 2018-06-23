package org.cx.game.core;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.corps.CorpsFactory;
import org.cx.game.corps.Hero;
import org.cx.game.widget.GroundFactory;
import org.cx.game.widget.IGroundE;

public class GroundHost extends AbstractHost {

	private IGroundE ground = null;
	
	private List<IPlayerE> playerList = new ArrayList<IPlayerE>();
	private List<Integer> troopOfPlayerList = new ArrayList<Integer>();
	
	public GroundHost(Integer mapId, String account) {
		// TODO Auto-generated constructor stub
		ground = (IGroundE) GroundFactory.getInstance(mapId);
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
		IContext context = new Context(this.ground);
		
		for(IPlayer player : this.playerList){
			context.addPlayer(player);
		}
	}
}
