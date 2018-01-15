package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.widget.Place;

public class MoveRangeValidator extends Validator {

	private Corps corps;
	private Place place;
	
	public MoveRangeValidator(Corps corps, Place place) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
		this.place = place;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		
		Integer curPosition = corps.getPosition();
		Integer step = corps.getPlayer().getContext().getGround().distance(curPosition, place.getPosition());
		Integer energy = corps.getMove().getEnergy();
		Integer consume = corps.getMove().getConsume();
		Integer range = energy/consume;         // 145/50 = 2;整数默认情况符合游戏规则
		
		if(range>=step)
			return true;
		else{
			addMessage(I18n.getMessage(MoveRangeValidator.class.getName()));
			return false;
		}
	}
}
