package org.cx.game.command;

import org.cx.game.core.IPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.SelectCorpsValidator;

public class ChuckCommand extends InteriorCommand {

	public ChuckCommand(IPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
		addValidator(new SelectCorpsValidator(buffer));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Corps corps = buffer.getCorps();
		corps.chuck();
	}
}
