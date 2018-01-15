package org.cx.game.command;

import org.cx.game.core.IPlayer;
import org.cx.game.exception.CommandValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.tools.Debug;

public class DoneCommand extends InteriorCommand {

	public DoneCommand(IPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		context.done();
	}
}
