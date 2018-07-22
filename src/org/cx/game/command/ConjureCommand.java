package org.cx.game.command;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.validator.ActiveSkillCooldownBufferValidator;
import org.cx.game.validator.CorpsAttackableBufferValidator;
import org.cx.game.validator.SelectGroundBufferValidator;

/**
 * 使用技能
 * @author chenxian
 *
 */
public class ConjureCommand extends InteriorCommand {

	public ConjureCommand(AbstractPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
		addValidator(new SelectGroundBufferValidator(buffer));
		addValidator(new ActiveSkillCooldownBufferValidator(buffer));
		addValidator(new CorpsAttackableBufferValidator(buffer));
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
