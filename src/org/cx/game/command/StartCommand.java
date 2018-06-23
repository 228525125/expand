package org.cx.game.command;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.core.IPlayer;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

public class StartCommand extends InteriorCommand {

	public StartCommand(IPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Context_Start,map);
		super.notifyObservers(info);
		
		context.start();
	}
}
