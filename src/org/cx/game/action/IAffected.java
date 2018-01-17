package org.cx.game.action;

import org.cx.game.corps.Corps;

public interface IAffected extends IAction {
		
	@Override
	public  Corps getOwner();
	
	/**
	 * 魔法伤害，当魔法造成伤害时，必须调用该方法
	 * @param harm 伤害值
	 */
	public void magicHarm(Integer harm);
}
