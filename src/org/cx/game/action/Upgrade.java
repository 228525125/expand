package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.treasure.Resource;
import org.cx.game.widget.treasure.ResourceFactory;

public class Upgrade extends AbstractAction implements IAction {

	private Integer level = 0;
	private Integer levelLimit = 1;
	private Map<Integer, String> requirement = new HashMap<Integer, String>();
	
	public Upgrade(Map<Integer, String> requirement) {
		// TODO Auto-generated constructor stub
		this.requirement = requirement;
	}
	
	public Integer getLevel() {
		// TODO Auto-generated method stub
		return this.level;
	}

	public void setLevel(Integer level) {
		// TODO Auto-generated method stub
		if(!level.equals(this.level)){
			this.level = level;
		}
	}
	
	public Integer getLevelLimit() {
		return levelLimit;
	}

	public void setLevelLimit(Integer levelLimit) {
		if(!levelLimit.equals(this.levelLimit)){
			this.levelLimit = levelLimit;
		}
	}
	
	public Resource getRequirement() {
		// TODO Auto-generated method stub
		String resString = this.requirement.get(Integer.valueOf(getLevel()+1));
		if(null!=resString)
			return ResourceFactory.getInstance(resString);
		else
			return null;
	}

	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		Integer level = getLevel();
		level += 1;
		setLevel(level);
	}
}
