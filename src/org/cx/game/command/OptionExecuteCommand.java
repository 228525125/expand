package org.cx.game.command;

import org.cx.game.core.IPlayer;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.OptionAllowValidator;
import org.cx.game.widget.building.IOption;

public class OptionExecuteCommand extends InteriorCommand {

	public OptionExecuteCommand(IPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
		addValidator(new OptionAllowValidator(buffer));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		IOption option = buffer.getOption();
		option.execute(new Object[]{parameter});
		
		buffer.clear();
	}

}