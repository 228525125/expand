package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.tools.I18n;
import org.cx.game.widget.building.IOption;

public class SelectOptionValidator extends Validator {

	protected CommandBuffer buffer = null;
	private IOption option = null;
	
	public SelectOptionValidator(CommandBuffer buffer) {
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
			addMessage(I18n.getMessage(SelectOptionValidator.class.getName()));
			return false;
		}
	}
	
	protected IOption getOption(){
		return this.option;
	}
}
