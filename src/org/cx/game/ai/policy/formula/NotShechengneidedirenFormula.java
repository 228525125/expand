package org.cx.game.ai.policy.formula;

import java.util.List;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;

/**
 * 攻击范围内没有敌人
 * @author chenxian
 *
 */
public class NotShechengneidedirenFormula extends Validator {

	private Corps corps = null;
	
	public NotShechengneidedirenFormula(Corps corps) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		/*
		 * 搜索攻击范围内的敌人
		 */
		List<Corps> enemyList = ShechengneidedirenFormula.searchEnemy(this.corps);
		
		Boolean ret = false;
		if(!enemyList.isEmpty()){
			addMessage(I18n.getMessage(this));
		}else{
			ret = true;
		}
		
		return ret;
	}
}
