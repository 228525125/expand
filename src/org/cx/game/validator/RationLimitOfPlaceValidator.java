package org.cx.game.validator;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;

/**
 * 验证单个格子人口上限
 * @author chenxian
 *
 */ 
public class RationLimitOfPlaceValidator extends Validator {
	
	public static final Integer Ration = 100;
	
	private Corps corps = null;
	private Integer nop = 0;

	public RationLimitOfPlaceValidator(Corps corps, Integer nop) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
		this.nop = nop;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(Ration>=corps.getRation()*this.nop)
			return true;
		else{
			addMessage(I18n.getMessage(RationLimitOfPlaceValidator.class.getName()));
			return false;
		}
	}
}
