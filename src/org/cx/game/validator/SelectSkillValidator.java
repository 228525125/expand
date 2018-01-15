package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.magic.skill.ISkill;
import org.cx.game.tools.I18n;

/**
 * 验证是否已缓存了skill
 * @author chenxian
 *
 */
public class SelectSkillValidator extends Validator {

	private CommandBuffer buffer = null;
	private ISkill skill = null;
	
	public SelectSkillValidator(CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		skill = buffer.getSkill(); 
		if(null==skill){
			addMessage(I18n.getMessage(SelectSkillValidator.class.getName()));
			return false;
		}else{
			return true;
		}
	}

	protected CommandBuffer getBuffer() {
		return buffer;
	}

	protected ISkill getSkill() {
		return skill;
	}
	
	
}
