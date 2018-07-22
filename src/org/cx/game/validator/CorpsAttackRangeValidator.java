package org.cx.game.validator;

import java.util.List;

import org.cx.game.corps.Corps;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.I18n;
import org.cx.game.widget.AbstractGround;

/**
 * 验证攻击距离
 * @author chenxian
 *
 */
public class CorpsAttackRangeValidator extends Validator {

	private Corps attack = null;
	private Corps attacked = null;
	
	public CorpsAttackRangeValidator(Corps attack, Corps attacked) {
		// TODO Auto-generated constructor stub
		this.attack = attack;
		this.attacked = attacked;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		AbstractGround ground = attack.getGround();
		List<Integer> ps = ground.queryRange(attack, CommonIdentifierE.Command_Query_Attack);
		if(ps.contains(attacked.getPosition())){
			return true;
		}else{
			addMessage(I18n.getMessage(CorpsAttackRangeValidator.class.getName()));
			return false;
		}
	}

	public void setAttacked(Corps attacked) {
		this.attacked = attacked;
	}
}
