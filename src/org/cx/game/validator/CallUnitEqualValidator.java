package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;

/**
 * 当补充兵源时，验证兵源与军队兵种的一致性；
 * @author chenxian
 *
 */
public class CallUnitEqualValidator extends Validator {

	private Corps unit = null;
	private Corps call = null;
	
	public CallUnitEqualValidator(Corps unit, Corps call) {
		// TODO Auto-generated constructor stub
		this.unit = unit;
		this.call = call;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(unit.getType().equals(call.getType()))
			return true;
		else{
			addMessage(I18n.getMessage(CallUnitEqualValidator.class.getName()));
			return false;
		}
	}
}
