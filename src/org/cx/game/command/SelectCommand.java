package org.cx.game.command;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

public class SelectCommand extends InteriorCommand {
	
	public SelectCommand(AbstractPlayer player) {
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
		
		if(null!=buffer.getCorps()){
			map.put("card", buffer.getCorps());
			map.put("position", buffer.getCorps().getPosition());
		}
		
		if(null!=buffer.getSkill())
			map.put(CommandBuffer.SKILL, buffer.getSkill());
		
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Command_Select,map); 
		super.notifyObservers(info);    
	}
}
