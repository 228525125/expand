package org.cx.game.command;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.validator.ActiveSkillCooldownValidator;
import org.cx.game.validator.AttackableValidator;
import org.cx.game.validator.SelectGroundValidator;

/**
 * 使用技能
 * @author chenxian
 *
 */
public class ConjureCommand extends InteriorCommand {

	public ConjureCommand(AbstractPlayer player) {
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
		ActiveSkill skill = (ActiveSkill) buffer.getSkill();
		corps.conjure(skill, new Object[]{parameter});
	}
	
}
