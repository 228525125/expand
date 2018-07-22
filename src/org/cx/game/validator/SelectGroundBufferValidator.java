package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.tools.I18n;

public class SelectGroundBufferValidator extends Validator {

	private CommandBuffer buffer;
	
	public SelectGroundBufferValidator(CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(null!=buffer.getGround())
			return true;
		else{
			addMessage(I18n.getMessage(SelectGroundBufferValidator.class.getName()));
			return false;
		}
	}
}
