package org.cx.game.magic.skill;

import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.Place;

/**
 * 执行时验证技能对象是Corps
 * @author chenxian
 *
 */
public class ConjureToCorpsOption extends ConjureOption {
	
	public ConjureToCorpsOption(ActiveSkill skill) {
		super(skill);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		AbstractGround ground = getOwner().getOwner().getPlayer().getContext().getGround();
		setParameterTypeValidator(new Class[]{Place.class}, new String[]{"corps"}, ground.getLivingCorpsList().toArray());
		
		super.execute(objects);
	}

}
