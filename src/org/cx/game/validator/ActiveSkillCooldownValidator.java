package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.tools.I18n;

/**
 * 验证技能是否冷却
 * @author chenxian
 *
 */
public class ActiveSkillCooldownValidator extends SelectActiveSkillValidator {

	public ActiveSkillCooldownValidator(CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		super(buffer);
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		if(ret){
			ActiveSkill as = (ActiveSkill) getSkill();
			if(as.getAllow()){
				ret = true;
			}else{
				addMessage(I18n.getMessage(ActiveSkillCooldownValidator.class.getName()));
				ret = false;
			}
		}
		
		return ret;
	}
}
