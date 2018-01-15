package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.tools.I18n;

public class UpgradeObjectValidator extends Validator {

	protected CommandBuffer buffer = null;
	
	public UpgradeObjectValidator(CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(null!=buffer.getSkill()){
			return true;
		}else{
			addMessage(I18n.getMessage(UpgradeObjectValidator.class.getName()));
			return false;
		}
	}
}
