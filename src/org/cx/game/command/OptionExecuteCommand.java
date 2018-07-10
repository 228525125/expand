package org.cx.game.command;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.OptionAllowValidator;
import org.cx.game.widget.building.AbstractOption;

public class OptionExecuteCommand extends InteriorCommand {

	public OptionExecuteCommand(AbstractPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
		addValidator(new OptionAllowValidator(buffer));
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
