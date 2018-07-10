package org.cx.game.command;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Util;
import org.cx.game.validator.IntegerTypeValidator;
import org.cx.game.widget.AbstractGround;

public class SwitchCommand extends InteriorCommand {

	public SwitchCommand(AbstractPlayer player) {
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
		AbstractGround ground = context.getGround(mapId);
		context.setGround(ground);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ground", ground);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Command_Switch,map);
		super.notifyObservers(info);
	}
}
