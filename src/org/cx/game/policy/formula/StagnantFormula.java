package org.cx.game.policy.formula;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;

/**
 * 是否在原地
 * @author chenxian
 *
 */
public class StagnantFormula extends Validator implements IFormula {
	
	private Corps corps = null;
	private Integer originPosition = null;

	public StagnantFormula(Corps corps, Integer originPosition) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
		this.originPosition = originPosition;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = false;
		if(this.originPosition.equals(corps.getPosition())){
			ret = true;
		}else{
			ret = false;
			addMessage(I18n.getMessage(this));
		}
		return ret;
	}
}
