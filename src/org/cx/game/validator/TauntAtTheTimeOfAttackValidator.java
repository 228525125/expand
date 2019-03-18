package org.cx.game.validator;

import org.cx.game.magic.buff.AbstractBuff;
import org.cx.game.command.CommandBuffer;
import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.TauntBuff;
import org.cx.game.tools.I18n;

/**
 * 执行攻击命令之前，验证是否受到嘲讽
 * @author chenxian
 * 
 */
public class TauntAtTheTimeOfAttackValidator extends Validator {

	private Corps attacked = null;
	private Corps attack = null;
	
	public TauntAtTheTimeOfAttackValidator(Corps attack, Corps attacked) {
		// TODO Auto-generated constructor stub
		this.attack = attack;
		this.attacked = attacked;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		Boolean taunt = true;
		if(attack.containsBuff(TauntBuff.class)){
			for(AbstractBuff buff : attack.getBuff(TauntBuff.class)){
				TauntBuff tauntBuff = (TauntBuff) buff;
				taunt = attacked.equals(tauntBuff.getTaunter());
				break;
			}
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
