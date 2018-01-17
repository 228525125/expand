package org.cx.game.magic.skill;

import org.cx.game.intercepter.IInterceptable;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.IRecover;
import org.cx.game.observer.Observable;

/**
 * 被动技能
 * @author chenxian
 *
 */
public interface IPassiveSkill extends ISkill,IIntercepter {

	/**
	 * 激活
	 */
	public void activate();
	
	/**
	 * 失效
	 */
	public void invalid();
	
	/**
	 * 被动技能增加的攻击力，在extraAtk里面体现
	 * @return
	 */
	public Integer getAtk();
	
	/**
	 * 被动技能增加的防御力，在extraAtk里面体现
	 * @return
	 */
	public Integer getDef();
}
