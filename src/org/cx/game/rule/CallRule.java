package org.cx.game.rule;

import org.cx.game.action.Call;
import org.cx.game.core.Player;
import org.cx.game.corps.Corps;
import org.cx.game.corps.Hero;
import org.cx.game.corps.Corps;
import org.cx.game.tools.Util;
import org.cx.game.widget.Ground;
import org.cx.game.widget.Place;
import org.cx.game.widget.treasure.Mineral;
import org.cx.game.widget.treasure.Resource;

/**
 * 处理补充兵源的情况
 * @author chenxian
 *
 */
public class CallRule extends AbstractRule {

	private Boolean isInvoke = true;
	
	@Override
	public Boolean isInvoke() {
		// TODO Auto-generated method stub
		return isInvoke;
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Corps corps = getOwner().getOwner();
		Player player = (Player) corps.getPlayer();
		player.getCorpsList().add(corps);
	}
	
	/**
	 * 这里与after区分开，如果是补充兵源after将不会被调用
	 */
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		Call call = getOwner();
		Corps owner = call.getOwner();
		
		/*
		 * 扣减资源
		 */
		Player player = owner.getPlayer();
		Mineral consume = call.getConsume();		
		
		this.isInvoke = true;
	}
	
	@Override
	public Call getOwner() {
		// TODO Auto-generated method stub
		return (Call) super.getOwner();
	}
	
	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return Call.class;
	}
}
