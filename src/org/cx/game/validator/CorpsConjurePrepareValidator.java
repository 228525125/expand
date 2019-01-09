package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.tools.I18n;

/**
 * 蓄力的验证，当技能允许蓄力，且corps已经满足蓄力条件，则通过验证；
 * @author chenxian
 *
 */
public class CorpsConjurePrepareValidator extends Validator {

	private ActiveSkill skill = null;
	
	public CorpsConjurePrepareValidator(ActiveSkill skill) {
		// TODO Auto-generated constructor stub
		this.skill = skill;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		Corps corps = (Corps) this.skill.getOwner();
		if(skill.getPrepare()<=corps.getGrow().getPower()){
			ret = true;
		}else{
			addMessage(I18n.getMessage(CorpsConjurePrepareValidator.class.getName()));
			ret = false;
		}
		
		return ret; 
	}
}
