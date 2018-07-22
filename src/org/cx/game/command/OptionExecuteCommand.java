package org.cx.game.command;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.OptionAllowBufferValidator;
import org.cx.game.widget.AbstractOption;

public class OptionExecuteCommand extends InteriorCommand {

	public OptionExecuteCommand(AbstractPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
		addValidator(new OptionAllowBufferValidator(buffer));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		AbstractOption option = buffer.getOption();
		option.execute(new Object[]{parameter});
		
		buffer.clear();
	}

}
