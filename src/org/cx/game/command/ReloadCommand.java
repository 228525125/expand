package org.cx.game.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.core.IPlayer;
import org.cx.game.exception.CommandValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;

public class ReloadCommand extends InteriorCommand {

	public ReloadCommand(IPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("god", player.getContext().getGround().toList());      //ground已被屏蔽
		list.add(map);
		
		NotifyInfo info = new NotifyInfo(NotifyInfo.Command_Reload,list); 
		super.notifyObservers(info);    //通知观察者
	}
}
