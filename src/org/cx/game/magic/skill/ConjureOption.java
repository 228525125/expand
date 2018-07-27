package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.Execute;
import org.cx.game.action.IAction;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.validator.CorpsAttackableValidator;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Place;

public class ConjureOption extends AbstractOption {
	
	private String name = null;
	
	public ConjureOption(ActiveSkill skill) {
		// TODO Auto-generated constructor stub
		setOwner(skill);
		setSpacingWait(skill.getCooldown());
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name){
			name = super.getName();
			name += getOwner().getName();
		}
		return name;
	}
	
	@Override
	public List<Integer> getExecuteRange(AbstractGround ground) {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		ActiveSkill as = (ActiveSkill) getOwner();
		AbstractCorps corps = as.getOwner();
		Integer position = corps.getPosition();
		positionList = ground.areaForDistance(position, as.getRange(), AbstractGround.Contain);
		return positionList;
	}
	
	@Override
	public ActiveSkill getOwner() {
		// TODO Auto-generated method stub
		return (ActiveSkill) super.getOwner();
	}

	@Override
	protected AbstractPlayer getOwnerPlayer() {
		// TODO Auto-generated method stub
		return getOwner().getOwner().getPlayer();
	}
	
	private Execute execute = null;
	
	@Override
	public Execute getExecute() {
		// TODO Auto-generated method stub
		if(null==this.execute){
			Execute execute = new ConjureOptionExecute();
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}
	
	@Override
	public void execute(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		addValidator(new CorpsAttackableValidator((Corps) getOwner().getOwner()));
		
		super.execute(objects);
	}
	
	class ConjureOptionExecute extends Execute implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			ActiveSkill skill = getOwner().getOwner();
			Corps corps = (Corps) skill.getOwner();
			
			corps.conjure(skill, objects);
		}
		
		@Override
		public ConjureOption getOwner() {
			// TODO Auto-generated method stub
			return (ConjureOption) super.getOwner();
		}
	}

}
