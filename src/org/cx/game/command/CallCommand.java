package org.cx.game.command;

import org.cx.game.core.IPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.SelectGroundValidator;
import org.cx.game.validator.SelectCorpsValidator;
import org.cx.game.validator.SelectPlaceEmptyValidator;
import org.cx.game.widget.Place;

/**
 * 暂停使用，已被CallOption替代
 * @author chenxian
 *
 */
@Deprecated
public class CallCommand extends InteriorCommand {

	public CallCommand(IPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
		addValidator(new SelectCorpsValidator(buffer));
	}
	
	@Override
	public void setParameter(Object parameter) {
		// TODO Auto-generated method stub
		super.setParameter(parameter);
		addValidator(new SelectPlaceEmptyValidator((Place) parameter,true));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Place place = (Place) parameter;
		Corps corps = (Corps) buffer.getCorps();
		//corps.call(place);
		buffer.clear();
	}
}
