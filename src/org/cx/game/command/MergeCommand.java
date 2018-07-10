package org.cx.game.command;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.corps.Hero;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.CorpsIsHeroValidator;
import org.cx.game.validator.MoveRangeValidator;

public class MergeCommand extends InteriorCommand {

	public MergeCommand(AbstractPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setParameter(Object parameter) {
		// TODO Auto-generated method stub
		super.setParameter(parameter);
		
		addValidator(new CorpsIsHeroValidator((Corps) parameter));
		addValidator(new MoveRangeValidator((Corps) buffer.getCorps(), ((Corps) parameter).getPosition()));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Corps corps = (Corps) buffer.getCorps();
		Hero hero = (Hero) parameter;
		
		corps.merge(hero);
	}
}
