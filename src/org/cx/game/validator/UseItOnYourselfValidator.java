package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;

/**
 * 对自己action的验证
 * @author chenxian
 *
 */
public class UseItOnYourselfValidator extends Validator {

	private Boolean enable = false;
	private AbstractCorps owner = null;
	private AbstractCorps corps = null;
	
	public UseItOnYourselfValidator(Boolean enable, AbstractCorps owner, AbstractCorps corps) {
		// TODO Auto-generated constructor stub
		this.enable  =enable;
		this.owner = owner;
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		if(ret){
			if(this.enable.equals(this.owner.equals(this.corps))){
				ret = true;
			}else{
				ret = false;
				addMessage(I18n.getMessage(UseItOnYourselfValidator.class.getName()));
			}
		}
		
		return ret;
	}
}
