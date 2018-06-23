package org.cx.game.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.core.Context;
import org.cx.game.core.IPlayer;
import org.cx.game.core.IPlayerE;
import org.cx.game.exception.CommandValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.JsonHelper;
import org.cx.game.widget.HoneycombGround;

public class ReloadCommand extends InteriorCommand {

	private IPlayer player = null;
	
	public ReloadCommand(IPlayer player) {
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
		
		Map<String, Integer> landform = JsonHelper.convertForGroundLandform(ground.getLandformMap());
		
		map.put("landform", landform);
		map.put("buildingList", ground.getBuildingList());
		map.put("treasureList", ground.getTreasureList());
		map.put("corpsList", ground.getLivingCorpsList());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Ground_LoadMap,map);
		super.notifyObservers(info);
	}
}
