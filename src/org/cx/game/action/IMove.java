package org.cx.game.action;

import java.util.List;

import org.cx.game.corps.Corps;

public interface IMove extends IAction{
	
	public static final Integer Consume = 1; //单格移动消耗
	
	public static final Integer Energy_Max = 20;      //最大精力，表示可移动到任意位置
	
	public static final Integer Energy_Min = 0;       //最小精力，表示不可移动
	
	public static final Integer TurnoverRatio = 60; //资源转换率
	
	/**
	 * 单格移动消耗
	 * @return
	 */
	public Integer getConsume();
	
	/**
	 * 移动状态
	 * @return
	 */
	public Boolean getMoveable();
	
	public void setMoveable(Boolean moveable);
	
	/**
	 * 移动方式
	 * @return
	 */
	public Integer getType(); 
	
	public void setType(Integer type);
	
	/**
	 * 精力，与移动范围相关
	 * @return
	 */
	public Integer getEnergy();

	public void setEnergy(Integer energy);
	
	/**
	 * 逃离成功率
	 * @return
	 */
	public Integer getFlee();
	
	public void setFlee(Integer fleeChance);
	
	/**
	 * 隐身状态
	 * @return
	 */
	public Boolean getHide();
	
	public void setHide(Boolean hide);
	
	/**
	 * 朝向
	 * @return
	 */
	public Integer getDirection();
	
	public void setDirection(Integer direction);
	
	/**
	 * 移动路径
	 * @return
	 */
	public List<Integer> getMovePath();
	
	/**
	 * 增加路径
	 * @param position 
	 */
	public void addMovePath(Integer position);
	
	public Corps getOwner();

}
