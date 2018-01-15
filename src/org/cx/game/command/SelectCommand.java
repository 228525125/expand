package org.cx.game.command;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.core.IPlayer;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;

public class SelectCommand extends InteriorCommand {
	
	public SelectCommand(IPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		buffer.set(parameter);
		
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
			map.put("position", buffer.getCemetery().getOwner().getPosition());
		}
		
		if(null!=buffer.getTrickList()){
			map.put(CommandBuffer.TRICKLIST, buffer.getTrickList());
			map.put("position", buffer.getTrickList().getOwner().getPosition());
		}
		
		if(null!=buffer.getCorps()){
			map.put("card", buffer.getCorps());
			map.put("position", buffer.getCorps().getPosition());
		}
		
		if(null!=buffer.getSkill())
			map.put(CommandBuffer.SKILL, buffer.getSkill());
		
		if(null!=buffer.getTrick())
			map.put(CommandBuffer.TRICK, buffer.getTrick());
		
		NotifyInfo info = new NotifyInfo(NotifyInfo.Command_Select,map); 
		super.notifyObservers(info);    
	}
}
