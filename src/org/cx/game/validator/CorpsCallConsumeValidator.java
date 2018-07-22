package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.tools.Util;
import org.cx.game.widget.treasure.Mineral;

public class CorpsCallConsumeValidator extends Validator {

	private Corps corps;
	private Integer number;
	
	public CorpsCallConsumeValidator(Corps corps, Integer number) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
		this.number = number;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true;

		Mineral res = corps.getPlayer().getMineral();
			
		if(Util.absoluteLessThan(res, Util.operating(Util.Mul, corps.getCall().getConsume(), number))){
			ret = false;
			addMessage(I18n.getMessage(CorpsCallConsumeValidator.class.getName()));
		}	
	
		return ret;
	}
}
