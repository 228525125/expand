package org.cx.game.command;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.core.IPlayer;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;

public class ShowCommand extends InteriorCommand {

	public ShowCommand(IPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(CommandBuffer.PLAYER, buffer.getPlayer());
		
		if(null!=buffer.getGround())
			map.put(CommandBuffer.GROUND, buffer.getGround());
		
		if(null!=buffer.getPlace()){
			map.put(CommandBuffer.PLACE, buffer.getPlace());
			map.put("position", buffer.getPlace().getPosition());
		}
		
		if(null!=buffer.getBuilding()){
			map.put(CommandBuffer.BUILDING, buffer.getBuilding());
		}
		
		if(null!=buffer.getOption()){
			map.put(CommandBuffer.OPTION, buffer.getOption());
		}
		
		if(null!=buffer.getCemetery()){
			map.put(CommandBuffer.CEMETERY, buffer.getCemetery());
		}
		
		if(null!=buffer.getTrickList()){
			map.put(CommandBuffer.TRICKLIST, buffer.getTrickList());
		}
		
		if(null!=buffer.getCorps()){
			map.put("card", buffer.getCorps());
			map.put("position", buffer.getCorps().getPosition());
		}
		
		if(null!=buffer.getSkill())
			map.put(CommandBuffer.SKILL, buffer.getSkill());
		
		if(null!=buffer.getTrick())
			map.put(CommandBuffer.TRICK, buffer.getTrick());
		
		NotifyInfo info = new NotifyInfo(NotifyInfo.Command_Show,map); 
		super.notifyObservers(info);    //通知观察者
	}

}
