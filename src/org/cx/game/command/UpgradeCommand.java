package org.cx.game.command;

import org.cx.game.action.Upgrade;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.magic.skill.AbstractSkill;
import org.cx.game.validator.UpgradeConsumeValidator;
import org.cx.game.validator.UpgradeObjectValidator;

public class UpgradeCommand extends InteriorCommand {

	public UpgradeCommand(AbstractPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
		addValidator(new UpgradeObjectValidator(buffer));
	}

	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		if(null!=buffer.getSkill()){
			AbstractSkill skill = buffer.getSkill();
			
			addValidator(new UpgradeConsumeValidator((Upgrade)skill.getUpgrade(), skill.getOwner().getPlayer()));
			
			doValidator();
			if(hasError())
				throw new RuleValidatorException(getErrors().getMessage());
			
			skill.upgrade();
		}
	}
}
