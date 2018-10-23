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
	
	public AreaHost(Integer areaId, String account, Integer troop, String playNo) {
		// TODO Auto-generated constructor stub
		super(playNo);
		
		this.area = AreaFactory.getInstance(areaId);
		playerJoinGame(account, troop);
	}
	
	@Override
	public Ground getGround() {
		// TODO Auto-generated method stub
		return (Ground) this.area.getStartingPoint();
	}
	
	@Override
	public void ready() {
		// TODO Auto-generated method stub
		super.ready();
		
		getContext().setStatus(AbstractContext.Status_Start);
	}
	
	@Override
	public void start(AbstractPlayer player) {
		// TODO Auto-generated method stub
		
	}
}
