package org.cx.game.command;

import org.cx.game.exception.ValidatorException;

public class BackCommand extends WithCacheCommand {
	
	public BackCommand(CommandBuffer buffer) {
		super(buffer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		buffer.backspace();
	}

}
