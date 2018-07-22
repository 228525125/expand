package org.cx.game.validator;

import org.cx.game.action.Pick;
import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.Place;

/**
 * 验证拾取距离
 * @author chenxian
 *
 */
public class CorpsPickRangeValidator extends Validator {

	private Place place = null;
	private Corps corps = null;
	
	public CorpsPickRangeValidator(Corps corps, Place place) {
		// TODO Auto-generated constructor stub
		this.place = place;
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true;
		AbstractGround ground = this.corps.getPlayer().getContext().getGround();
		Integer distance = ground.distance(this.corps.getPosition(), this.place.getPosition());
		if(Pick.Pick_Range_Defautl<distance){
			ret = false;
			addMessage(I18n.getMessage(CorpsPickRangeValidator.class.getName()));
		}
	
		return ret;
	}
}
