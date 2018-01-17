package org.cx.game.action;

import org.cx.game.corps.Corps;

public interface IActivate extends IAction {

	public static final Integer ActivationConsume = 100;
	
	public Boolean getActivation();
	
	/**
	 * 隐式改变状态
	 * @param activate
	 */
	public void setActivation(Boolean activate);
	
	public Corps getOwner();
	
	/**
	 * 速度
	 * @return
	 */
	public Integer getSpeed();
	
	/**
	 * 隐式使用，不涉及到模块以外的变更时使用；
	 * @param speed
	 */
	public void setSpeed(Integer speed);
	
	public void addToSpeed(Integer speed);
	
	/**
	 * 活力值，活力值为100时，可以行动一次，活力值越多，行动次数越多
	 * @return
	 */
	public Integer getVigour();
	
	public void addToVigour(Integer vigour);
}
