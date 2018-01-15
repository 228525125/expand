package org.cx.game.command;

import org.cx.game.core.IPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.CorpsActivateValidator;

public class GuardCommand extends InteriorCommand {

	public GuardCommand(IPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
		
		addValidator(new CorpsActivateValidator(buffer));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Corps corps = (Corps) buffer.getCorps();
		corps.activate(false);
	}
}
