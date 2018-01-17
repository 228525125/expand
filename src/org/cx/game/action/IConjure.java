package org.cx.game.action;

import org.cx.game.corps.Corps;

/**
 * 施法
 * @author chenxian
 *
 */
public interface IConjure extends IAction {
	
	@Override
	public Corps getOwner();
}
