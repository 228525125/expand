package org.cx.game.validator;

import org.cx.game.core.AbstractHost;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;

public class HostStatusValidator extends Validator {

	private Integer status = null;
	private AbstractHost host = null;
	
	public HostStatusValidator(Integer status, AbstractHost host) {
		// TODO Auto-generated constructor stub
		this.host = host;
		this.status = status;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(this.host.getStatus().equals(this.status)){
			return true;
		}else{
			addMessage(I18n.getMessage(this));
			return false;
		}
	}
}
