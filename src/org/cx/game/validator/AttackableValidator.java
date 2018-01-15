package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.tools.I18n;

/**
 * 验证是否可以行动
 * @author chenxian
 *
 */
public class AttackableValidator extends SelectCorpsValidator {

	public AttackableValidator(CommandBuffer buffer) {
		super(buffer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		if(ret){
			if(getCorps().getAttack().getAttackable()){
				ret = true;
			}else{
				addMessage(I18n.getMessage(AttackableValidator.class.getName()));
				ret = false;
			}
		}
		
		return ret;
	}

}
