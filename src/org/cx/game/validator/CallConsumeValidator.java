package org.cx.game.validator;

import java.util.Map;
import java.util.Map.Entry;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.widget.treasure.IResource;

public class CallConsumeValidator extends Validator {

	private Corps corps;
	private Integer number;
	
	public CallConsumeValidator(Corps corps, Integer number) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
		this.number = number;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true;

		IResource res = corps.getPlayer().getResource();
			
		if(res.absoluteLessThan(corps.getCall().getConsume())){
			ret = false;
			addMessage(I18n.getMessage(CallConsumeValidator.class.getName()));
		}	
	
		return ret;
	}
}
