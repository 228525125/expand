package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.tools.I18n;

/**
 * 验证缓存的是否为ActiveSkill
 * @author chenxian
 * 
 */
public class SelectActiveSkillBufferValidator extends SelectSkillBufferValidator {

	public SelectActiveSkillBufferValidator(CommandBuffer buffer) {
		super(buffer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret =  super.validate();
		if(ret){
			if (getSkill() instanceof ActiveSkill) {
				ret = true;
			}else{
				addMessage(I18n.getMessage(SelectActiveSkillBufferValidator.class.getName()));
				ret = false;
			}
		}
		
		return ret;
	}

}
