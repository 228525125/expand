package org.cx.game.ai.policy.formula;

import java.util.List;

import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.IBuff;
import org.cx.game.magic.buff.AttackLockBuff;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;

public class LockFormula extends Validator {
	
	private Corps corps = null;
	private List<Corps> lockerList = null;

	public LockFormula(Corps corps) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = false;
		
		if(this.corps.containsBuff(AttackLockBuff.class)){
			ret = true;
			List<IBuff> buffList = this.corps.getBuff(AttackLockBuff.class);
			for(IBuff buff : buffList){
				AttackLockBuff alb = (AttackLockBuff) buff;
				this.lockerList.add(alb.getLocker());
			}
		}else{
			addMessage(I18n.getMessage(this));
			ret = false;
		}
		
		return ret;
	}
	
	public List<Corps> getLockerList(){
		return this.lockerList;
	}
}
