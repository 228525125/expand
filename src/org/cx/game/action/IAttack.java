package org.cx.game.action;

import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IInterceptable;
import org.cx.game.observer.Observable;

/**
 * 系统将攻击这个动作抽象出来的目的是不同的生物攻击方式不同，规则也不同，比如近身攻击、远程攻击等
 * @author chenxian
 *
 */
public interface IAttack extends IAction {
	
	public Integer getMode();
	
	public void setMode(Integer mode);
	
	public Integer getRange();

	/**
	 * 隐式使用，不涉及到模块以外的变更时使用；
	 * @param range
	 */
	public void setRange(Integer range);
	
	/**
	 * 真实攻击力 = 初始攻击力  + 等级攻击力 + 武器攻击力 + 地形攻击力 + 额外攻击力
	 * @return
	 */
	public Integer getAtk();
	
	public void setAtk(Integer atk);
	
	/**
	 * 刷新真实攻击力
	 */
	public void updateAtk();

	/**
	 * 额外攻击力，包括：等级攻击力，buff攻击力，skill攻击力
	 * @return
	 */
	public Integer getExtraAtk();

	public void setExtraAtk(Integer extraAtk);
	
	/**
	 * 更新包括：等级攻击力，buff攻击力，skill攻击力
	 */
	public void updateExtraAtk();
	
	public Integer getWeaponAtk();
	
	public void setWeaponAtk(Integer weaponAtk);
	
	public void updateWeaponAtk();
	
	/**
	 * 地形攻击力
	 * @return
	 */
	public Integer getLandformAtk();

	public void setLandformAtk(Integer landformAtk);
	
	/**
	 * 真实伤害 = 单位伤害 * 数量
	 * 请注意，伤害的改变只与数量有关；
	 * @return
	 */
	public Integer getDmg();
	
	public void setDmg(Integer dmg);
	
	/**
	 * 更新伤害值，例如数量发生变化时
	 */
	public void updateDmg();
	
	public Integer getLockChance();
	
	/**
	 * 隐式使用，不涉及到模块以外的变更时使用；
	 * @param lockChance
	 */
	public void setLockChance(Integer lockChance);
	
	/**
	 * 攻击方式，是否是反击
	 * @return
	 */
	public Boolean getCounterAttack();
	
	public void setCounterAttack(Boolean counterAttack);
	
	/**
	 * 是否能够攻击
	 * @return
	 */
	public Boolean getAttackable();
	
	public void setAttackable(Boolean attackable);
	
	/**
	 * 移动攻击，例如，骑兵
	 * @return
	 */
	public Boolean getMobile();
	
	public void setMobile(Boolean mobile);
	
	public IAttack clone();
	
	public Corps getOwner();
}
