package org.cx.game.validator;

import org.cx.game.tools.I18n;
import org.cx.game.widget.AbstractOption;

/**
 * 验证选项是否能被执行
 * @author chenxian
 * 
 */
public class OptionAllowBufferValidator extends Validator {

	private AbstractOption option = null;
	
	public OptionAllowBufferValidator(AbstractOption option) {
		// TODO Auto-generated constructor stub
		this.option = option;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		
		if(!AbstractOption.Status_Executable.equals(option.getStatus())){
			addMessage(I18n.getMessage(OptionAllowBufferValidator.class.getName()));
			return false;
		}
		
		return true;
	}
}
