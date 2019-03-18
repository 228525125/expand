package org.cx.game.widget.treasure;

import org.cx.game.action.ActionProxyHelper;
import org.cx.game.action.IAction;
import org.cx.game.corps.Corps;
import org.cx.game.corps.ICorps;
import org.cx.game.tools.I18n;

/**
 * 物品
 * @author chenxian
 *
 */
public abstract class Treasure {

	private String name = null;
	private Integer position = null;
	
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name)
			name = I18n.getMessage(this, "name");
		return name;
	}
	
	/**
	 * 在地图上的位置
	 * @return
	 */
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
	
	public abstract IAction getPicked();

	/**
	 * 被捡起来
	 */
	public void picked(Corps corps) {
		// TODO Auto-generated method stub
		IAction action = new ActionProxyHelper(getPicked());
		action.action(corps);
	}

}
