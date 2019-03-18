package org.cx.game.rule;

import org.cx.game.intercepter.AbstractIntercepter;

/**
 * 游戏规则
 * 这里需要说明一下，哪些情况会用到rule，游戏的逻辑本应放在相应的方法中，例如action方法，
 * 但一些逻辑超出了action方法所能理解的范围，比如，攻击时会附带锁效果，但attack对象并不
 * 知道攻击时会有什么buff产生，这个buff会随着游戏的改进而变化，因此这种buff效果就最好放
 * 如rule中，这也符合将易变化的部分隔离起来的原则；
 * @author chenxian
 *
 */
public abstract class AbstractRule extends AbstractIntercepter {

	private Object owner = null;
	
	/**
	 * 需要添加规则的对象的接口；目前只有Action实现了IInterceptable接口；
	 * @return 接口类型
	 */
	public abstract Class getInterceptable();
	
	public void setOwner(Object owner) {
		this.owner = owner;
	}

	public Object getOwner() {
		return this.owner;
	}
}
