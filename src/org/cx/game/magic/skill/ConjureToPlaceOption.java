package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.IAction;
import org.cx.game.core.Player;
import org.cx.game.corps.Corps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.I18n;
import org.cx.game.validator.CorpsAttackableValidator;
import org.cx.game.validator.CorpsConjurePrepareValidator;
import org.cx.game.validator.FriendOrEnemyValidator;
import org.cx.game.validator.IValidator;
import org.cx.game.validator.OptionExecuteRangeValidator;
import org.cx.game.validator.ParameterTypeValidator;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.Ground;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Place;

import com.sun.imageio.plugins.common.I18N;

/**
 * 施法，代替Conjure功能
 * @author admin
 *
 */
public class ConjureToPlaceOption extends ConjureOption {
	
	public ConjureToPlaceOption(ActiveSkill skill) {
		// TODO Auto-generated constructor stub
		super(skill);
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
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("corps", corps);
			map.put("option", skill);
			map.put("position", corps.getPosition());
			NotifyInfo info = new NotifyInfo(CommonIdentifierE.Option_Executed,map);
			notifyObservers(info);
		}
		
		@Override
		public ConjureToPlaceOption getOwner() {
			// TODO Auto-generated method stub
			return (ConjureToPlaceOption) super.getOwner();
		}
	}

}
