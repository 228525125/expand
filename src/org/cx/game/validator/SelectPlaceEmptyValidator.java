package org.cx.game.validator;

import org.cx.game.tools.I18n;
import org.cx.game.widget.IGround;
import org.cx.game.widget.Place;

public class SelectPlaceEmptyValidator extends Validator {

	private Boolean empty = false;
	private Place place = null;
	
	public SelectPlaceEmptyValidator(Place place, Boolean empty) {
		// TODO Auto-generated constructor stub
		this.empty = empty;
		this.place = place;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		if(ret){
			if(place.getEmpty().equals(empty))
				ret = true;
			else{
				addMessage(I18n.getMessage(SelectPlaceEmptyValidator.class.getName()));
				ret = false;
			}
		}
		
		return ret;
	}
}
