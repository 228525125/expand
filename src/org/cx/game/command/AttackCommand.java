package org.cx.game.command;

import org.cx.game.core.IPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.exception.CommandValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.tools.Debug;
import org.cx.game.validator.AttackAtkValidator;
import org.cx.game.validator.AttackRangeValidator;
import org.cx.game.validator.AttackTauntValidator;
import org.cx.game.validator.AttackableValidator;
import org.cx.game.validator.SelectGroundValidator;
import org.cx.game.validator.SelectCorpsNotHideValidator;

public class AttackCommand extends InteriorCommand {

	public AttackCommand(IPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
		addValidator(new SelectGroundValidator(buffer));
		addValidator(new AttackableValidator(buffer));
		addValidator(new AttackAtkValidator(buffer));
	}
	
	@Override
	public void setParameter(Object parameter) {
		// TODO Auto-generated method stub
		super.setParameter(parameter);
		addValidator(new AttackTauntValidator(buffer, (Corps) parameter));
		addValidator(new AttackRangeValidator((Corps) buffer.getCorps(),(Corps) parameter));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Corps attack = (Corps) buffer.getCorps();
		Corps attacked = (Corps) parameter;
		
		doValidator(new SelectCorpsNotHideValidator(attacked));
		if(hasError())
			throw new CommandValidatorException(getErrors().getMessage());
		
		attack.attack(attacked);
		if(!Debug.isDebug)
			player.getContext().done();   //结束本回合
	}
}
