package org.cx.game.command;

import org.cx.game.core.IPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.IActiveSkill;
import org.cx.game.magic.skill.ISkill;
import org.cx.game.exception.ValidatorException;
import org.cx.game.tools.Debug;
import org.cx.game.validator.ActiveSkillCooldownValidator;
import org.cx.game.validator.AttackableValidator;
import org.cx.game.validator.ParameterTypeValidator;
import org.cx.game.validator.SelectActiveSkillValidator;
import org.cx.game.validator.SelectGroundValidator;
import org.cx.game.validator.SelectCorpsValidator;
import org.cx.game.validator.SelectSkillValidator;

/**
 * 使用技能
 * @author chenxian
 *
 */
public class ConjureCommand extends InteriorCommand {

	public ConjureCommand(IPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
		addValidator(new SelectGroundValidator(buffer));
		addValidator(new ActiveSkillCooldownValidator(buffer));
		addValidator(new AttackableValidator(buffer));
	}

	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Corps corps = (Corps) buffer.getCorps();		
		IActiveSkill skill = (IActiveSkill) buffer.getSkill();
		corps.conjure(skill, new Object[]{parameter});
	}
	
}
