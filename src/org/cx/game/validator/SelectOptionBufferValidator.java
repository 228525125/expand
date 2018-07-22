package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.tools.I18n;
import org.cx.game.widget.AbstractOption;

public class SelectOptionBufferValidator extends Validator {

	protected CommandBuffer buffer = null;
	private AbstractOption option = null;
	
	public SelectOptionBufferValidator(CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(null!=buffer.getOption()){
			this.option = buffer.getOption();
			return true;
		}else{
			addMessage(I18n.getMessage(SelectOptionBufferValidator.class.getName()));
			return false;
		}
	}
	
	protected AbstractOption getOption(){
		return this.option;
	}
}
