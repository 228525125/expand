package org.cx.game.command;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.HoneycombGround;

public class ReloadCommand extends InteriorCommand {

	private AbstractPlayer player = null;
	
	public ReloadCommand(AbstractPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
		
		this.player = player;
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Map<String,Object> map = new HashMap<String,Object>();
		HoneycombGround ground = (HoneycombGround) context.getGround();

		map.put("ground", ground);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Ground_LoadMap,map);
		super.notifyObservers(info);
	}
}
