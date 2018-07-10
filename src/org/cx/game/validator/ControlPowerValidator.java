package org.cx.game.validator;

import org.cx.game.command.Command;
import org.cx.game.core.AbstractContext;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.tools.I18n;

/**
 * 执行命令前，验证是否具有控制权
 * @author chenxian
 *
 */
public class ControlPowerValidator extends Validator {

	private AbstractPlayer player = null;
	private Command command = null;
	
	public ControlPowerValidator(AbstractPlayer player, Command command) {
		// TODO Auto-generated constructor stub
		this.player = player;
		this.command = command;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		AbstractContext context = player.getContext();
		if(player.equals(context.getControlPlayer()))
			return true;
		else{
			addMessage(I18n.getMessage(ControlPowerValidator.class.getName()));
			return false;
		}
	}
}
