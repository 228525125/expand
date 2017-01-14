package org.cx.game.validator;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.TauntBuff;
import org.cx.game.command.CommandBuffer;
import org.cx.game.tools.I18n;

/**
 * 执行攻击命令之前，验证是否受到嘲讽
 * @author chenxian
 *
 */
public class AttackTauntValidator extends SelectLifeCardValidator {

	private LifeCard attacked = null;
	
	public AttackTauntValidator(CommandBuffer buffer, LifeCard attacked) {
		super(buffer);
		// TODO Auto-generated constructor stub
		this.attacked = attacked;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		Boolean taunt = true;
		if(getLifeCard().containsBuff(TauntBuff.class)){
			TauntBuff buff = (TauntBuff) getLifeCard().getBuff(TauntBuff.class);
			taunt = attacked.equals(buff.getTaunter());
		}
		
		if(ret&&taunt){
			ret = true;
		}else{
			addMessage(I18n.getMessage(this));
			ret = false;
		}
		
		return ret;
	}

	
}
