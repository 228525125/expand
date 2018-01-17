package org.cx.game.action;

import org.cx.game.widget.treasure.ITreasure;

public interface IPicked extends IAction {

	public ITreasure getOwner();
}
