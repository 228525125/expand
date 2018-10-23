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
public class TauntAtTheTimeOfAttackBufferValidator extends SelectCorpsBufferValidator {

	private Corps attacked = null;
	
	public TauntAtTheTimeOfAttackBufferValidator(CommandBuffer buffer, Corps attacked) {
		super(buffer);
		// TODO Auto-generated constructor stub
		this.attacked = attacked;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		Boolean taunt = true;
		if(getCorps().containsBuff(TauntBuff.class)){
			for(AbstractBuff buff : getCorps().getBuff(TauntBuff.class)){
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
