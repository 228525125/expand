package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.ISkill;
import org.cx.game.tools.I18n;
import org.cx.game.widget.building.IOption;

public class QueryCommandValidator extends Validator {

	private String action = null;
	private Corps corps = null;
	private ISkill skill = null;
	private IOption option = null;
	
	public QueryCommandValidator(String action, CommandBuffer buffer) {
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
				addMessage(I18n.getMessage(QueryCommandValidator.class.getName()));
		}
		
		if("conjure".equals(action)){
			if(null!=skill)
				return true;
			else
				addMessage(I18n.getMessage(QueryCommandValidator.class.getName()));
		}
		
		if("execute".equals(action)){
			if(null!=option)
				return true;
			else
				addMessage(I18n.getMessage(QueryCommandValidator.class.getName()));
		}
		
		return false;
	}
}
