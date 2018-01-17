package org.cx.game.magic.skill;

import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IInterceptable;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.IRecover;
import org.cx.game.observer.Observable;
import org.cx.game.validator.IValidatable;

/**
 * 主动技能
 * @author chenxian
 *
 */
public interface IActiveSkill extends ISkill,IValidatable {

	/**
	 * 代号，用于command调用
	 * @return
	 */
	public String getCode();
	
	/**
	 * 消耗法力值
	 * @return
	 
	public Integer getConsume();
	
	public void setConsume(Integer power);*/
	
	public Integer getRange();
	
	/**
	 * 瞬发
	
	public static final Integer Velocity_Quick = 0;  */  
	
	/**
	 * 蓄力
	
	public static final Integer Velocity_Slow= 1; */    
	
	/**
	 * 是否需蓄力
	 * @return
	 
	public Integer getVelocity(); */
	
	/**
	 * 技能冷却周期
	 * @return
	 */
	public Integer getCooldown();

	public void setCooldown(Integer cooldown);
	
	/**
	 * 剩余冷却回合
	 * @return
	 */
	public Integer getCooldownRemain();
	
	/**
	 * 是否可以执行
	 * @return
	 */
	public Boolean getAllow();
	
	public void setAllow(Boolean allow);
	
	/**
	 * 使用技能
	 * @param objects
	 */
	public void useSkill(Object...objects) throws RuleValidatorException;
}
