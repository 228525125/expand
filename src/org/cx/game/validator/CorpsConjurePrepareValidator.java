package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.tools.I18n;
import org.cx.game.tools.Util;

/**
 * 蓄力的验证，当没有蓄力不足，且moveable为false时，无法通过；
 * @author chenxian
 *
 */
public class CorpsConjurePrepareValidator extends CorpsMoveableValidator {

	private ActiveSkill skill = null;
	
	public CorpsConjurePrepareValidator(ActiveSkill skill) {
		super((Corps) skill.getOwner());
		// TODO Auto-generated constructor stub
		this.skill = skill;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		Corps corps = (Corps) this.skill.getOwner();
		
		if(!ret){
			if(skill.getPrepare()<=corps.getGrow().getPower()){
				ret = true;
				
				/*
				 * 如果power满足技能需求，则通过并扣减power
				 */
				corps.getGrow().setPower(Util.Sub, skill.getPrepare());
			}else{
				addMessage(I18n.getMessage(CorpsConjurePrepareValidator.class.getName()));
				ret = false;
			}
		}

		return ret; 
	}
}
