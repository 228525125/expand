package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.widget.building.AbstractBuilding;

public class SelectBuildingBufferValidator extends Validator {

	private CommandBuffer buffer = null;
	
	public SelectBuildingBufferValidator(CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true; 
		AbstractBuilding building = buffer.getBuilding();
		
		if(null!=building){
			ret = true;
		}else{
			addMessage(I18n.getMessage(SelectBuildingBufferValidator.class.getName()));
			ret = false;
		}
		
		return ret;
	}
}
