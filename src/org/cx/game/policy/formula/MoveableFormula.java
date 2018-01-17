package org.cx.game.policy.formula;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;

public class MoveableFormula extends Validator implements IFormula {

	private Corps corps = null;
	
	public MoveableFormula(Corps corps) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = false;
		
		if(this.corps.getMove().getMoveable())
			ret = true;
		else{
			addMessage(I18n.getMessage(this));
			ret = false;
		}
		
		return ret;
	}
}
