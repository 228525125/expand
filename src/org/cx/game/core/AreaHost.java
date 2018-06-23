package org.cx.game.core;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.corps.CorpsFactory;
import org.cx.game.corps.Hero;
import org.cx.game.widget.Area;
import org.cx.game.widget.AreaFactory;
import org.cx.game.widget.IGround;

public class AreaHost extends AbstractHost {
	
	private Area area = null;
	
	public AreaHost(Integer areaId, String account) {
		// TODO Auto-generated constructor stub
		this.area = AreaFactory.getInstance(areaId);
		playerJoinGame(account);
	}
	
	/**
	 * 
	 * @return 返回一个没有被玩家占用的阵营编号，如果都被占用了，则返回null
	 */
	public Integer getUsableTroop() {
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(this.area.getTroopList());
		list.removeAll(getTroopOfPlayerList());
		if(list.isEmpty())
			return null;
		else
			return list.get(0);
	}
	
	/**
	 * 游戏准备就绪
	 */
	public void ready() {
		IGround ground = this.area.getStartingPoint();
		IContext context = new Context(ground);
		
		for(IPlayer player : getPlayerList()){
			context.addPlayer(player);
		}
	}
}
