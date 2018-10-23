package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.tools.I18n;

/**
 * 判断是否可移动
 * @author chenxian
 * 
 */
public class CorpsMoveableBufferValidator extends CorpsActivateBufferValidator {

	public CorpsMoveableBufferValidator(CommandBuffer buffer) {
		super(buffer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		if(ret){
			if(getCorps().getMove().getMoveable()){
				ret = true;
			}else{
				addMessage(I18n.getMessage(CorpsMoveableBufferValidator.class.getName()));
				ret = false;
			}
		}
		return ret;
	}

}
