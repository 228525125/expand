package org.cx.game.command;

import org.cx.game.core.IPlayer;
import org.cx.game.exception.ValidatorException;
import org.cx.game.tools.Util;
import org.cx.game.validator.IntegerTypeValidator;
import org.cx.game.widget.IGround;

public class SwitchCommand extends InteriorCommand {

	public SwitchCommand(IPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setParameter(Object parameter) {
		// TODO Auto-generated method stub
		super.setParameter(parameter);
		
		addValidator(new IntegerTypeValidator(parameter));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Integer mapId = Integer.valueOf(Util.filterNumber(parameter.toString())); 
		IGround ground = context.getGround(mapId);
		context.setGround(ground);
	}
}
