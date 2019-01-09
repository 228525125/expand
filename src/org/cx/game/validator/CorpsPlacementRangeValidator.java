package org.cx.game.validator;

import java.util.List;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.tools.I18n;
import org.cx.game.widget.Place;

public class CorpsPlacementRangeValidator extends Validator {

	private Integer position = null;
	private AbstractCorps corps = null;
	
	public CorpsPlacementRangeValidator(AbstractCorps corps, Integer position) {
		// TODO Auto-generated constructor stub
		this.position = position;
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		List<Integer> entranceList = this.corps.getGround().getEntranceList(this.corps.getTroop());
		if(entranceList.contains(position)){
			return true;
		}else{
			addMessage(I18n.getMessage(CorpsPlacementRangeValidator.class.getName()));
			return false;
		}
	}
}
