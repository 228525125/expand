package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.widget.Place;

public class CorpsMoveRangeValidator extends Validator {

	private Corps corps;
	private Integer position;
	
	public CorpsMoveRangeValidator(Corps corps, Integer position) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
		this.position = position;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		
		Integer curPosition = corps.getPosition();
		Integer step = corps.getPlayer().getContext().getGround().distance(curPosition, position);
		Integer energy = corps.getMove().getEnergy();
		Integer consume = corps.getMove().getConsume();
		Integer range = energy/consume;         // 145/50 = 2;整数默认情况符合游戏规则
		
		if(range>=step)
			return true;
		else{
			addMessage(I18n.getMessage(CorpsMoveRangeValidator.class.getName()));
			return false;
		}
	}
}
