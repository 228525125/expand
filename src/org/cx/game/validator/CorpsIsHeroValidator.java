package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Hero;
import org.cx.game.tools.I18n;

public class CorpsIsHeroValidator extends Validator {

	private Corps corps = null;
	
	public CorpsIsHeroValidator(Corps corps) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if (this.corps instanceof Hero) {
			return true;
		}else{
			addMessage(I18n.getMessage(CorpsIsHeroValidator.class.getName()));
			return false;
		}
		
	}
}
