package org.cx.game.command;

import org.cx.game.core.IPlayer;
import org.cx.game.magic.skill.ISkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.UpgradeConsumeValidator;
import org.cx.game.validator.UpgradeObjectValidator;

public class UpgradeCommand extends InteriorCommand {

	public UpgradeCommand(IPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
		addValidator(new UpgradeObjectValidator(buffer));
	}

	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		if(null!=buffer.getSkill()){
			ISkill skill = buffer.getSkill();
			
			addValidator(new UpgradeConsumeValidator(skill.getUpgrade(), skill.getOwner().getPlayer()));
			
			doValidator();
			if(hasError())
				throw new RuleValidatorException(getErrors().getMessage());
			
			skill.upgrade();
		}
	}
}
