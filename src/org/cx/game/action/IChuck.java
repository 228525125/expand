package org.cx.game.action;

import org.cx.game.corps.Corps;

public interface IChuck extends IAction {

	@Override
	public Corps getOwner();
}
