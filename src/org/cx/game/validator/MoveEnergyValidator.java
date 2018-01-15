package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.tools.I18n;

/**
 * 验证精力还有剩余
 * @author chenxian
 *
 */
public class MoveEnergyValidator extends SelectCorpsValidator {

	public MoveEnergyValidator(CommandBuffer buffer) {
		super(buffer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		if(ret){
			if(getCorps().getMove().getEnergy()<1){
				ret = false;
				addMessage(I18n.getMessage(MoveEnergyValidator.class.getName()));
			}
		}
		
		return ret;
	}

}
