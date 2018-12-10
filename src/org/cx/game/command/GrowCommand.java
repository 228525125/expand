package org.cx.game.command;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.CorpsAttackableBufferValidator;
import org.cx.game.validator.CorpsMoveableBufferValidator;

public class GrowCommand extends InteriorCommand {

	public GrowCommand(AbstractPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
		addValidator(new CorpsMoveableBufferValidator(buffer));
		addValidator(new CorpsAttackableBufferValidator(buffer));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Corps owner = (Corps) buffer.getCorps();
		owner.grow();
	}
}
