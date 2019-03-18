package org.cx.game.validator;

import org.cx.game.tools.I18n;
import org.cx.game.widget.Ground;
import org.cx.game.widget.Place;
import org.cx.game.widget.Place;

/**
 * 选择的位置是否为空
 * @author chenxian
 *
 */
public class SelectPlaceEmptyValidator extends Validator {

	private Boolean empty = false;
	private Place place = null;
	
	public SelectPlaceEmptyValidator(Place place, Boolean empty) {
		// TODO Auto-generated constructor stub
		this.empty = empty;
		this.place = place;
	}
	
	public SelectPlaceEmptyValidator(Ground ground, Integer position, Boolean empty) {
		// TODO Auto-generated constructor stub
		this.empty = empty;
		this.place = ground.getPlace(position);
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		if(ret){
			if(null!=place && place.isEmpty().equals(empty))
				ret = true;
			else{
				addMessage(I18n.getMessage(SelectPlaceEmptyValidator.class.getName()));
				ret = false;
			}
		}
		
		return ret;
	}
}
