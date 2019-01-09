package org.cx.game.validator;

import org.cx.game.tools.I18n;
import org.cx.game.widget.AbstractOption;

public class OptionExecuteRangeValidator extends Validator {

	private Integer position = null;
	private AbstractOption option = null;
	
	public OptionExecuteRangeValidator(Integer position, AbstractOption option) {
		// TODO Auto-generated constructor stub
		this.position = position;
		this.option = option;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(option.getExecuteRange().contains(position)){
			return true;
		}else{
			addMessage(I18n.getMessage(OptionExecuteRangeValidator.class.getName()));
			return false;
		}
	}

}
