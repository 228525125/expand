package org.cx.game.command.check;

import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.CorpsAttackRangeValidator;
import org.cx.game.validator.CorpsAttackableValidator;
import org.cx.game.validator.FriendOrEnemyValidator;
import org.cx.game.validator.SelectCorpsNotHideValidator;
import org.cx.game.validator.SelectPlaceEmptyValidator;
import org.cx.game.widget.Ground;

public class AttackCheck extends Check {
	
	@Override
	public void check(Event event) throws ValidatorException {
		// TODO Auto-generated method stub
		CheckEvent ce = (CheckEvent) event;
		
		Corps attack = (Corps) ce.getSource();
			
		Integer position = Integer.valueOf(ce.getArguments()[0].toString());      //在Call中验证过了
		Ground ground = (Ground) attack.getGround();
			
		doValidator(new SelectPlaceEmptyValidator(ground, position, false));      //攻击的位置上是否有敌人
			
		Corps attacked = (Corps) ground.getCorps(position);                       
			
		doValidator(new CorpsAttackableValidator(attack));                        //本回合是否可攻击
		
		doValidator(new CorpsAttackRangeValidator(attack, attacked));             //是否超出攻击范围
		doValidator(new FriendOrEnemyValidator(attack, attacked, false));         //分清敌友
	}
	
	@Override
	public Class [] getParameterTypes() {
		return new Class[] {Integer.class};
	}
	
	@Override
	public Class getCallerType() {
		// TODO Auto-generated method stub
		return Corps.class;
	}
	
	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return "attack";
	}
}
