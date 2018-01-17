package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.tools.I18n;
import org.cx.game.widget.building.IOption;

/**
 * 验证选项是否能被执行
 * @author chenxian
 *
 */
public class OptionAllowValidator extends SelectOptionValidator {

	public OptionAllowValidator(CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		super(buffer);
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		if(ret){
			ret = getOption().getAllow();
			if(!ret){
				addMessage(I18n.getMessage(OptionAllowValidator.class.getName()));
			}
		}else{
			addMessage(I18n.getMessage(OptionAllowValidator.class.getName()));
		}
		
		return ret;
	}
}
