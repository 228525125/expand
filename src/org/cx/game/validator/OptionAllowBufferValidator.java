package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.tools.I18n;
import org.cx.game.widget.AbstractOption;

/**
 * 验证选项是否能被执行
 * @author chenxian
 * 
 */
public class OptionAllowBufferValidator extends SelectOptionBufferValidator {

	public OptionAllowBufferValidator(CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		super(buffer);
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		if(ret){
			ret = AbstractOption.Status_Executable.equals(getOption().getStatus());
			if(!ret){
				addMessage(I18n.getMessage(OptionAllowBufferValidator.class.getName()));
			}
		}else{
			addMessage(I18n.getMessage(OptionAllowBufferValidator.class.getName()));
		}
		
		return ret;
	}
}
