package org.cx.game.command;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.exception.CommandValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.tools.Debug;
import org.cx.game.validator.CorpsAttackAtkBufferValidator;
import org.cx.game.validator.CorpsAttackRangeValidator;
import org.cx.game.validator.OperatePowerValidator;
import org.cx.game.validator.TauntAtTheTimeOfAttackBufferValidator;
import org.cx.game.validator.CorpsAttackableBufferValidator;
import org.cx.game.validator.SelectGroundBufferValidator;
import org.cx.game.validator.SelectCorpsNotHideValidator;

public class AttackCommand extends InteriorCommand {

	public AttackCommand(AbstractPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
		addValidator(new SelectGroundBufferValidator(buffer));
		addValidator(new CorpsAttackableBufferValidator(buffer));
		addValidator(new CorpsAttackAtkBufferValidator(buffer));
		
		
	}
	
	@Override
	public void setParameter(Object parameter) {
		// TODO Auto-generated method stub
		super.setParameter(parameter);
		addValidator(new TauntAtTheTimeOfAttackBufferValidator(buffer, (Corps) parameter));
		addValidator(new CorpsAttackRangeValidator((Corps) buffer.getCorps(),(Corps) parameter));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Corps attack = (Corps) buffer.getCorps();
		Corps attacked = (Corps) parameter;
		
		doValidator(new OperatePowerValidator(attack.getPlayer()));
		doValidator(new SelectCorpsNotHideValidator(attacked));
		if(hasError())
			throw new CommandValidatorException(getErrors().getMessage());
		
		attack.attack(attacked);
	}
}
