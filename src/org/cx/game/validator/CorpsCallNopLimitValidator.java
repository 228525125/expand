package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.widget.building.AbstractBuilding;
import org.cx.game.widget.building.CallBuilding;

/**
 * 当招募时，验证招募人数是否超出可招募人数
 * @author chenxian
 *
 */
public class CorpsCallNopLimitValidator extends Validator {
	
	private Integer nop = 0;
	private CallBuilding building = null;

	public CorpsCallNopLimitValidator(Integer nop, CallBuilding building) {
		// TODO Auto-generated constructor stub
		this.nop = nop;
		this.building = building;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true;
		
		if(nop>this.building.getNop()){
			addMessage(I18n.getMessage(CorpsCallNopLimitValidator.class.getName()));
			ret = false;
		}
		
		return ret;
	}
}
