package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;

public class CorpsMoveableValidator extends CorpsActivateValidator {

	private Corps corps = null;
	
	public CorpsMoveableValidator(Corps corps) {
		// TODO Auto-generated constructor stub
		super(corps);
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		if(ret){
			if(this.corps.getMove().getMoveable()){
				ret = true;
			}else{
				addMessage(I18n.getMessage(CorpsMoveableValidator.class.getName()));
				ret = false;
			}
		}
		return ret;
	}
}
