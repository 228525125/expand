package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.widget.building.IBuilding;

/**
 * 当招募时，验证招募人数是否超出可招募人数
 * @author chenxian
 *
 */
public class CallNopValidator extends Validator {
	
	private Corps callUnit = null;
	private Integer nop = 0;
	private IBuilding town = null;

	public CallNopValidator(Corps callUnit, Integer nop, IBuilding town) {
		// TODO Auto-generated constructor stub
		this.callUnit = callUnit;
		this.nop = nop;
		this.town = town;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		return super.validate();
	}
}
