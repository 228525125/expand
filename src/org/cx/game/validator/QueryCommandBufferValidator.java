package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.magic.skill.AbstractSkill;
import org.cx.game.tools.I18n;
import org.cx.game.widget.AbstractOption;

public class QueryCommandBufferValidator extends Validator {

	private String action = null;
	private AbstractCorps corps = null;
	private AbstractSkill skill = null;
	private AbstractOption option = null;
	
	public QueryCommandBufferValidator(String action, CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		this.action = action;
		this.corps = buffer.getCorps();
		this.skill = buffer.getSkill();
		this.option = buffer.getOption();
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if("attack".equals(action) || "move".equals(action) || "pick".equals(action)){
			if(null!=corps.getGround())
				return true;
			else
				addMessage(I18n.getMessage(QueryCommandBufferValidator.class.getName()));
		}
		
		if("conjure".equals(action)){
			if(null!=skill)
				return true;
			else
				addMessage(I18n.getMessage(QueryCommandBufferValidator.class.getName()));
		}
		
		if("execute".equals(action)){
			if(null!=option)
				return true;
			else
				addMessage(I18n.getMessage(QueryCommandBufferValidator.class.getName()));
		}
		
		return false;
	}
}