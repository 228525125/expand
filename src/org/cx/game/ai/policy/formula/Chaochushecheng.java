package org.cx.game.ai.policy.formula;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;
import org.cx.game.widget.IGround;

public class Chaochushecheng extends Validator {

	private Corps corps = null;
	private Integer targetPosition = null;
	
	public Chaochushecheng(Integer targetPosition, Corps attack) {
		// TODO Auto-generated constructor stub
		this.corps = attack;
		this.targetPosition = targetPosition;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = false;
		
		IGround ground = this.corps.getGround();
		Integer distance = ground.distance(this.corps.getPosition(), targetPosition);
		Integer range = this.corps.getAttackRange();
		
		if(distance<range){
			addMessage(I18n.getMessage(this));
		}else{
			ret = true;
		}
		
		return ret;
	}
}
