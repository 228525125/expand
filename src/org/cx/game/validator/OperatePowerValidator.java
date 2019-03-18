package org.cx.game.validator;

import org.cx.game.core.Context;
import org.cx.game.core.Player;
import org.cx.game.tools.I18n;

/**
 * 执行命令前，验证是否具有操作权
 * @author chenxian
 *
 */ 
public class OperatePowerValidator extends Validator {

	private Player operatedPlayer = null;
	
	/**
	 * 
	 * @param operated 被操作对象的player
	 */
	public OperatePowerValidator(Player operated) {
		// TODO Auto-generated constructor stub
		this.operatedPlayer = operated;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Context context = operatedPlayer.getContext();
		if(operatedPlayer.equals(context.getControlPlayer()))
			return true;
		else{
			addMessage(I18n.getMessage(OperatePowerValidator.class.getName()));
			return false;
		}
	}
}
