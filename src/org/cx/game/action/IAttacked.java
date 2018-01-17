package org.cx.game.action;

import org.cx.game.corps.Corps;


public interface IAttacked extends IAction {
	
	public Corps getOwner();
	
	/**
	 * 是否可以反击
	 * @return
	 */
	public Boolean getFightBack();
	
	public void setFightBack(Boolean fightBack);
	
	/**
	 * 真实防御力 = 初始防御力 + 等级防御力 + 地形防御力 + 额外防御力
	 * @return
	 */
	public Integer getDef();
	
	public void setDef(Integer def);
	
	/**
	 * 更新防御力
	 */
	public void updateDef();
	
	/**
	 * 地形防御力
	 * @return
	 */
	public Integer getLandformDef();

	public void setLandformDef(Integer landformDef);
	
	/**
	 * 装备防御力
	 * @return
	 */
	public Integer getArmourDef();
	
	public void setArmourDef(Integer armourDef);
	
	public void updateArmourDef();
	
	/**
	 * 额外防御力，包括：等级防御力，buff防御力，skill防御力
	 * @return
	 */
	public Integer getExtraDef();
	
	public void setExtraDef(Integer extraDef);
	
	/**
	 * 更新包括：等级防御力，buff防御力，skill防御力
	 */
	public void updateExtraDef();

}
