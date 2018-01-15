package org.cx.game.validator;

import org.cx.game.action.IPick;
import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.widget.IGround;
import org.cx.game.widget.Place;

/**
 * 验证拾取距离
 * @author chenxian
 *
 */
public class PickRangeValidator extends Validator {

	private Place place = null;
	private Corps corps = null;
	
	public PickRangeValidator(Corps corps, Place place) {
		// TODO Auto-generated constructor stub
		this.place = place;
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true;
		IGround ground = this.corps.getPlayer().getContext().getGround();
		Integer distance = ground.distance(this.corps.getPosition(), this.place.getPosition());
		if(IPick.Pick_Range_Defautl<distance){
			ret = false;
			addMessage(I18n.getMessage(PickRangeValidator.class.getName()));
		}
	
		return ret;
	}
}
