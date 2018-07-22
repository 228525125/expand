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
	private Integer callCorpsType = null;
	
	public CallUnitEqualValidator(Corps unit, Integer callCorpsType) {
		// TODO Auto-generated constructor stub
		this.unit = unit;
		this.callCorpsType = callCorpsType;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(unit.getType().equals(this.callCorpsType))
			return true;
		else{
			addMessage(I18n.getMessage(CallUnitEqualValidator.class.getName()));
			return false;
		}
	}
}
