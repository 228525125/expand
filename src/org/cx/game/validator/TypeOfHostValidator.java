package org.cx.game.validator;

import org.cx.game.core.AbstractHost;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;

public class TypeOfHostValidator extends Validator {

	private Class clazz = null;
	private AbstractHost host = null;
	
	public TypeOfHostValidator(Class clazz, AbstractHost host) {
		// TODO Auto-generated constructor stub
		this.clazz = clazz;
		this.host = host;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(this.host.getClass().equals(this.clazz)){
			return true;
		}else{
			addMessage(I18n.getMessage(this));
			return false;
		}
	}
}
