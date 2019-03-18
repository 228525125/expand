package org.cx.game.validator;

import org.cx.game.core.Context;
import org.cx.game.tools.I18n;

public class ContextStatusValidator extends Validator {

	private Integer status = 0;
	private Context context = null;
	
	public ContextStatusValidator(Context context, Integer status) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.status = status;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(this.context.getStatus().equals(status))
			return true;
		else{
			addMessage(I18n.getMessage(ContextStatusValidator.class.getName()));
			return false;
		}
	}
}
