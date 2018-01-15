package org.cx.game.command;

import org.cx.game.core.IPlayer;
import org.cx.game.magic.skill.IActiveSkill;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.CorpsActivateValidator;
import org.cx.game.validator.SelectActiveSkillValidator;

public class UseSkillCommand extends InteriorCommand {

	public UseSkillCommand(IPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
		addValidator(new SelectActiveSkillValidator(buffer));    //判断
		addValidator(new CorpsActivateValidator(buffer));     //判断是否选择了corps，并且是否处于激活状态		
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		IActiveSkill skill = (IActiveSkill) buffer.getSkill();
		skill.useSkill(parameter);
	}

}
