package org.cx.game.command;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.exception.ValidatorException;

public class AutoCommand extends InteriorCommand {

	public AutoCommand(AbstractPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();

		player.automation();
	}
}
