package org.cx.game.command;

import org.cx.game.core.IPlayer;
import org.cx.game.exception.ValidatorException;

public class StartCommand extends InteriorCommand {

	public StartCommand(IPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		context.start();
	}
}
