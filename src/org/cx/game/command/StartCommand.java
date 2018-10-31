package org.cx.game.command;

import org.cx.game.core.AbstractContext;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.ContextStatusValidator;

public class StartCommand extends InteriorCommand {
	
	public StartCommand(AbstractPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
		
		addValidator(new ContextStatusValidator(context, AbstractContext.Status_Ready));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		context.start();
	}
}
