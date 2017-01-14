package org.cx.game.validator;

import org.cx.game.card.buff.TauntBuff;
import org.cx.game.command.CommandBuffer;
import org.cx.game.tools.I18n;

/**
 * 执行移动命令之前，验证是否受到嘲讽
 * @author chenxian
 *
 */
public class MoveTauntValidator extends SelectLifeCardValidator {

	public MoveTauntValidator(CommandBuffer buffer) {
		super(buffer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		Boolean tauntValidate = true;
		if(getLifeCard().containsBuff(TauntBuff.class)){
			tauntValidate = false;
		}
		
		if(ret&&tauntValidate){
			ret = true;
		}else{
			addMessage(I18n.getMessage(this));
			ret = false;
		}
		
		return ret;
	}

}
