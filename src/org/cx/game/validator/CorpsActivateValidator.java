package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.tools.I18n;

/**
 * 判断随从是否为激活状态
 * @author chenxian
 *
 */
public class CorpsActivateValidator extends SelectCorpsValidator {
	
	public CorpsActivateValidator(CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		super(buffer);
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		if(ret){
			if(getCorps().getActivate().getActivation()){
				ret = true;
			}else{
				addMessage(I18n.getMessage(CorpsActivateValidator.class.getName()));
				ret = false;
			}
		}
		
		return ret;
	}
}
