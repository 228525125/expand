package org.cx.game.policy.formula;

import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.AttackLockBuff;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;

public class NotLockFormula extends Validator implements IFormula {

	private Corps corps = null;
	
	public NotLockFormula(Corps corps) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = false;
		
		if(!this.corps.containsBuff(AttackLockBuff.class)){
			ret = true;
		}else{
			addMessage(I18n.getMessage(this));
			ret = false;
		}
		
		return ret;
	}

}
