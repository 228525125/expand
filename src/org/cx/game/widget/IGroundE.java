package org.cx.game.widget;

import java.util.List;
import java.util.Map;

import org.cx.game.core.IPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.skill.ISkill;
import org.cx.game.widget.building.IBuilding;
import org.cx.game.widget.building.IOption;

public interface IGroundE extends IGround {

	/**
	 * 获取建筑的坐标
	 * @param player 玩家
	 * @return
	 */
	public List<Integer> getBuildingPosition(IPlayer player);
	
	/**
	 * 获取建筑的坐标
	 * @param player 玩家
	 * @param buildingType 建筑类型
	 * @return
	 */
	public List<Integer> getBuildingPosition(IPlayer player, Integer buildingType);
	
	/**
	 * 获取建筑的坐标
	 * @param player 玩家
	 * @param buildingType 建筑类型
	 * @param level 大于等于这个等级
	 * @return
	 */
	public List<Integer> getBuildingPosition(IPlayer player, Integer buildingType, Integer level);
	
	/**
	 * 根据坐标查找建筑
	 * @param position
	 * @return
	 */
	public IBuilding getBuilding(Integer position);
	
	/**
	 * 占领建筑
	 * @param position 位置
	 * @param player 玩家
	 */
	public void captureBuilding(Integer position, IPlayer player);
	
	/**
	 * 获取建筑物
	 * @param player 玩家
	 * @return
	 */
	public List<IBuilding> getBuildingList(IPlayer player);
	
	/**
	 * 获取建筑物
	 * @param type 建筑物类型
	 * @param player 玩家
	 * @return
	 */
	public List<IBuilding> getBuildingList(IPlayer player, Integer type);
	
	/**
	 * 查询corps操作范围
	 * @param corps
	 * @param action
	 * @return
	 */
	public List<Integer> queryRange(AbstractCorps corps, String action);
	
	/**
	 * 查询技能使用范围，现将查询逻辑交给ActiveSkill来完成
	 * @param skill
	 * @param action
	 * @return
	 */
	public List<Integer> queryRange(ISkill skill, String action);
	
	/**
	 * 查询选项使用范围
	 * @param option
	 * @param action
	 * @return
	 */
	public List<Integer> queryRange(IOption option, String action);
	
	/**
	 * 获取某玩家战场上所有生物
	 * @param player
	 * @return
	 */
	public List<Corps> getCorpsList(IPlayer player, Integer status);
	
	/**
	 * 获取战场上所有生物
	 * @param player
	 * @return
	 */
	public List<Corps> getCorpsList(Integer status);
	
	/**
	 * 获取指定范围内的随从
	 * @param stand
	 * @param step
	 * @param type
	 * @return
	 */
	public List<Corps> getCorpsList(Integer stand, Integer step, Integer type);
	
	/**
	 * 根据ID查找corps
	 * @param ids 需要查找的卡片编号，非playID
	 * @return
	 
	public List<Corps> listForID(List<Integer> ids);*/
	
	/**
	 * 根据player和ID查询corps
	 * @param player
	 * @param ids
	 * @return
	 
	public List<Corps> listForID(IPlayer player, List<Integer> ids);*/
	
	/**
	 * 移动到指定位置
	 * @param corps 生物
	 * @param position 指定位置
	 * @param type 移动类型
	 */
	public List<Integer> move(Corps corps, Integer position, Integer type) throws RuleValidatorException;
	
	/**
	 * 用于show
	 * @return 以map形式返回容器所有corps
	 
	public List toList();*/
	
	/**
	 * 设置地形
	 * @param landformMap 地形数据
	 */
	public void setLandformMap(Map<Integer, Integer> landformMap);
	
	/**
	 * 中立部队
	 * @return
	 */
	public IPlayer getNeutral();
	
	/**
	 * 空位置
	 * @return
	 */
	public List<Integer> getEmptyList();
}
