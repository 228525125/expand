package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.Execute;
import org.cx.game.action.IAction;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.I18n;
import org.cx.game.validator.CorpsAttackableValidator;
import org.cx.game.validator.CorpsConjurePrepareValidator;
import org.cx.game.validator.FriendOrEnemyValidator;
import org.cx.game.validator.IValidator;
import org.cx.game.validator.OptionExecuteRangeValidator;
import org.cx.game.validator.ParameterTypeValidator;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Place;

import com.sun.imageio.plugins.common.I18N;

/**
 * 施法，代替Conjure功能
 * @author admin
 *
 */
public class ConjureToPlaceOption extends AbstractOption {
	
	private String name = null;
	
	public ConjureToPlaceOption(ActiveSkill skill) {
		// TODO Auto-generated constructor stub
		setOwner(skill);
		setSpacingWait(skill.getCooldown());
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name){
			name = I18n.getMessage(ConjureToPlaceOption.class, "name");
			name += getOwner().getName();
		}
		return name;
	}
	
	@Override
	public List<Integer> getExecuteRange() {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		ActiveSkill as = (ActiveSkill) getOwner();
		AbstractCorps corps = as.getOwner();
		AbstractGround ground = corps.getGround();
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
	public void setOwner(Object owner) {
		// TODO Auto-generated method stub
		super.setOwner(owner);
		
		ActiveSkill skill = (ActiveSkill) owner;
		addValidator(new CorpsAttackableValidator((Corps)skill.getOwner()));
		addValidator(new CorpsConjurePrepareValidator(skill));
	}
	
	@Override
	protected AbstractControlQueue getControlQueue() {
		// TODO Auto-generated method stub
		return getOwner().getOwner().getGround().getQueue();
	}
	
	@Override
	public void execute(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		/*
		 * 验证参数
		 */
		IValidator validator = new ParameterTypeValidator(objects,new Class[]{Place.class});
		doValidator(validator);
		
		/*
		 * 验证范围
		 */
		Place place = (Place) objects[0];
		doValidator(new OptionExecuteRangeValidator(place.getPosition(), this));
		
		super.execute(objects);
	}
	
	private Execute execute = null;
	
	@Override
	public Execute getExecute() {
		// TODO Auto-generated method stub
		if(null==this.execute){
			Execute execute = new ConjureToPlaceOptionExecute();
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}
	
	public class ConjureToPlaceOptionExecute extends Execute implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			ActiveSkill skill = getOwner().getOwner();
			Corps corps = (Corps) skill.getOwner();
			
			corps.conjure(skill, objects);
		}
		
		@Override
		public ConjureToPlaceOption getOwner() {
			// TODO Auto-generated method stub
			return (ConjureToPlaceOption) super.getOwner();
		}
	}

}
