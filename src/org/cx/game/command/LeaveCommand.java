package org.cx.game.command;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.CorpsMoveableValidator;
import org.cx.game.validator.MoveRangeValidator;
import org.cx.game.validator.SelectPlaceEmptyValidator;
import org.cx.game.widget.Place;

public class LeaveCommand extends InteriorCommand {

	public LeaveCommand(AbstractPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
		
		addValidator(new CorpsMoveableValidator(buffer));
	}
	
	@Override
	public void setParameter(Object parameter) {
		// TODO Auto-generated method stub
		super.setParameter(parameter);
		
		addValidator(new SelectPlaceEmptyValidator((Place) parameter, true));
		addValidator(new MoveRangeValidator((Corps) buffer.getCorps(), ((Place) parameter).getPosition()));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Corps corps = (Corps) buffer.getCorps();
		Place place = (Place) parameter;
		
		corps.leave(place);
	}
}
