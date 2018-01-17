package org.cx.game.action;

import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IInterceptable;
import org.cx.game.observer.Observable;
import org.cx.game.widget.treasure.IResource;

/**
 * 召唤
 * @author chenxian
 *
 */
public interface ICall extends IAction {
	
	/**
	 * 消耗资源
	 * @return 资源类型，资源数量
	 */
	public IResource getConsume();
	
	public void setConsume(IResource consume);
	
	/**
	 * 人口
	 * @return
	 */
	public Integer getRation();
	
	public void setRation(Integer ration);
	
	/**
	 * 人数
	 * @return
	 */
	public Integer getNop();
	
	public void setNop(Integer nop);
	
	public Corps getOwner();
}
