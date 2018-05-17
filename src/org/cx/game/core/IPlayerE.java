package org.cx.game.core;

import java.util.List;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;

public interface IPlayerE extends IPlayer {

	/**
	 * 获取所有随从
	 * @param status
	 * @return
	 */
	public List<Corps> getAttendantList(Integer status);
	
	@Override
	public IContextE getContext();
}
