package org.cx.game.core;

import java.util.List;

import org.cx.game.corps.AbstractCorps;

public interface IPlayerE extends IPlayer {
	
	@Override
	public IContextE getContext();
	
	/**
	 * 淘汰出局
	 */
	public void dieOut();
	
	/**
	 * 英雄编号
	 * @return
	 */
	public List<Integer> getHeroIDList();
	
	public void addHeroID(Integer ID);
	
	/**
	 * 玩家拥有的英雄
	 * @return
	 */
	public List<AbstractCorps> getHeroList();
	
	public void addHero(AbstractCorps hero);
}
