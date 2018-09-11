package org.cx.game.core;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.corps.CorpsFactory;
import org.cx.game.corps.Hero;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.Area;
import org.cx.game.widget.AreaFactory;
import org.cx.game.widget.Ground;

/**
 * 用于创建一个区域的主机，剧情模式中使用
 * @author chenxian
 *
 */
public class AreaHost extends AbstractHost {
	
	private Area area = null;
	
	public AreaHost(Integer areaId, String account, Integer troop) {
		// TODO Auto-generated constructor stub
		this.area = AreaFactory.getInstance(areaId);
		playerJoinGame(account, troop);
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
	
	@Override
	public Ground getGround() {
		// TODO Auto-generated method stub
		return (Ground) this.area.getStartingPoint();
	}
}
