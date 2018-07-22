package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.magic.skill.AbstractSkill;
import org.cx.game.tools.I18n;

/**
 * 验证是否已缓存了skill
 * @author chenxian
 *
 */
public class SelectSkillBufferValidator extends Validator {

	private CommandBuffer buffer = null;
	private AbstractSkill skill = null;
	
	public SelectSkillBufferValidator(CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		skill = buffer.getSkill(); 
		if(null==skill){
			addMessage(I18n.getMessage(SelectSkillBufferValidator.class.getName()));
			return false;
		}else{
			return true;
		}
	}

	protected CommandBuffer getBuffer() {
		return buffer;
	}

	protected AbstractSkill getSkill() {
		return skill;
	}
	
	
}
