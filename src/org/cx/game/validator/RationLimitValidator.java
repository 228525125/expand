package org.cx.game.validator;

import org.cx.game.core.IPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;

/**
 * 验证人口上限
 * @author chenxian
 *
 */
public class RationLimitValidator extends Validator {
	
	private Corps corps = null;
	private Integer nop = 0;

	public RationLimitValidator(Corps corps, Integer nop) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
		this.nop = nop;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		IPlayer player = corps.getPlayer();
		Integer ration = player.getRationLimit()-player.getRation();
		if(ration>=corps.getRation()*this.nop)
			return true;
		else{
			addMessage(I18n.getMessage(RationLimitValidator.class.getName()));
			return false;
		}
	}
}
