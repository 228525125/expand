package org.cx.game.rule;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IAction;
import org.cx.game.intercepter.IInterceptable;

/**
 * 一整套规则；
 * @author chenxian
 *
 */
public class RuleGroup {

	public final static Integer RuleGroup_System = 10500001;
	
	private List<AbstractRule> ruleList = new ArrayList<AbstractRule>();
	
	public void setRuleList(List<AbstractRule> ruleList) {
		this.ruleList = ruleList;
	}
	
	public void bindingRule(IInterceptable interceptable){
		Class clazz = interceptable.getClass();
		
		for(AbstractRule rule : ruleList){
			if(rule.getInterceptable().equals(clazz)){
				interceptable.addIntercepter(rule);
				rule.setOwner(interceptable);
			}
		}

		this.ruleList.clear();          //清除多余规则，释放内存
	}
}
