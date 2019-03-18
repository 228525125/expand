package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.TauntBuff;
import org.cx.game.tools.I18n;

/**
 * 执行移动命令之前，验证是否受到嘲讽
 * @author chenxian
 * 
 */
public class TauntAtTheTimeOfMoveValidator extends Validator {

	private Corps corps = null;
	
	public TauntAtTheTimeOfMoveValidator(Corps corps) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		if(corps.containsBuff(TauntBuff.class)){
			addMessage(I18n.getMessage(this));
			ret = false;
		}
		
		return ret;
	}

}
