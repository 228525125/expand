package org.cx.game.validator;

import java.util.List;

import org.cx.game.tools.I18n;
import org.cx.game.widget.Place;

public class CorpsPlacementRangeValidator extends Validator {

	private Integer position = null;
	private List<Integer> entranceList = null;
	
	public CorpsPlacementRangeValidator(List<Integer> entranceList, Integer position) {
		// TODO Auto-generated constructor stub
		this.entranceList = entranceList;
		this.position = position;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(entranceList.contains(position)){
			return true;
		}else{
			addMessage(I18n.getMessage(CorpsPlacementRangeValidator.class.getName()));
			return false;
		}
	}
}
