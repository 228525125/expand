package org.cx.game.magic.skill;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.action.IAction;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.validator.CorpsAttackableValidator;
import org.cx.game.validator.CorpsConjureableValidator;
import org.cx.game.validator.FriendOrEnemyValidator;
import org.cx.game.validator.IValidator;
import org.cx.game.validator.OptionExecuteRangeValidator;
import org.cx.game.validator.ParameterTypeValidator;
import org.cx.game.validator.UseItOnYourselfValidator;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.Place;

/**
 * 执行时验证技能对象是Corps
 * @author chenxian
 *
 */
public class ConjureToCorpsOption extends ConjureOption {
	
	public ConjureToCorpsOption(ActiveSkill skill) {
		// TODO Auto-generated constructor stub
		super(skill);
	}
	
	@Override
	public void execute(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub		
		
		/*
		 * 验证参数
		 */
		AbstractGround ground = getOwner().getOwner().getGround();
		IValidator validator = new ParameterTypeValidator(objects,new Class[]{Place.class},new String[]{"corps"},ground.getCorpsList().toArray());
		doValidator(validator);
		
		/*
		 * 验证范围
		 */
		Place place = (Place) objects[0];
		doValidator(new OptionExecuteRangeValidator(place.getPosition(), this));
		
		/*
		 * 验证敌友关系
		 */
		switch (getOwner().getUseItOnFriendOrFoeOrAll()) {
		case 1:
			doValidator(new FriendOrEnemyValidator(getOwner().getOwner(), place.getCorps(), true));
			break;
		
		case 2:
			doValidator(new FriendOrEnemyValidator(getOwner().getOwner(), place.getCorps(), false));
			break;
			
		default:
			break;
		}
		
		/*
		 * 验证技能是否允许对自己使用
		 */
		doValidator(new UseItOnYourselfValidator(getOwner().getUseItOnYouself(), getOwner().getOwner(), place.getCorps()));
		
		super.execute(objects);
	}
	
	private Execute execute = null;
	
	@Override
	public Execute getExecute() {
		// TODO Auto-generated method stub
		if(null==this.execute){
			Execute execute = new ConjureToCorpsOptionExecute();
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}

	class ConjureToCorpsOptionExecute extends Execute implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			ActiveSkill skill = getOwner().getOwner();
			Corps corps = (Corps) skill.getOwner();
			
			corps.conjure(skill, objects);
		}
		
		@Override
		public ConjureToCorpsOption getOwner() {
			// TODO Auto-generated method stub
			return (ConjureToCorpsOption) super.getOwner();
		}
	}
}
