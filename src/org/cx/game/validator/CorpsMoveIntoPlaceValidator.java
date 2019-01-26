package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.widget.Place;

public class CorpsMoveIntoPlaceValidator extends Validator {

	private Place place = null;
	private Corps corps = null;
	
	public CorpsMoveIntoPlaceValidator(Corps corps, Place place) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
		this.place = place;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(this.corps.getMove().getHide() || this.place.isEmpty())
			return true;
		else{
			addMessage(I18n.getMessage(CorpsMoveIntoPlaceValidator.class.getName()));
			return false;
		}
	}
}
