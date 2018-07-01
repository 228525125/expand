package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;

import com.sun.corba.se.spi.copyobject.CopierManager;

/**
 * 验证精力还有剩余
 * @author chenxian
 *
 */
public class MoveEnergyValidator extends Validator {
	
	private Corps corps = null;

	public MoveEnergyValidator(Corps corps) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		if(ret){
			if(this.corps.getMove().getEnergy()<1){
				ret = false;
				addMessage(I18n.getMessage(MoveEnergyValidator.class.getName()));
			}
		}
		
		return ret;
	}

}
