package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;

public class CorpsConjureableValidator extends CorpsActivateValidator {

	private Corps corps = null;
	
	public CorpsConjureableValidator(Corps corps) {
		super(corps);
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		if(corps.getConjure().getConjureable()){
			ret = true;
		}else{
			ret = false;
			addMessage(I18n.getMessage(CorpsConjureableValidator.class.getName()));
		}
		
		return ret; 
	}

}
