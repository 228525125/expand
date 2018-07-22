package org.cx.game.command;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.CorpsAttackableBufferValidator;
import org.cx.game.validator.CorpsMoveEnergyValidator;
import org.cx.game.validator.TauntAtTheTimeOfMoveBufferValidator;
import org.cx.game.validator.CorpsPickRangeValidator;
import org.cx.game.validator.CorpsPickTreasureEquipmentValidator;
import org.cx.game.validator.SelectCorpsBufferValidator;
import org.cx.game.validator.SelectGroundBufferValidator;
import org.cx.game.validator.SelectPlaceExistTreasureValidator;
import org.cx.game.widget.Place;
import org.cx.game.widget.treasure.Treasure;

public class PickCommand extends InteriorCommand {

	public PickCommand(AbstractPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
		addValidator(new SelectGroundBufferValidator(buffer));
		addValidator(new SelectCorpsBufferValidator(buffer));
		addValidator(new CorpsMoveEnergyValidator((Corps) buffer.getCorps()));
		addValidator(new CorpsAttackableBufferValidator(buffer));
		addValidator(new TauntAtTheTimeOfMoveBufferValidator(buffer));
	}
	
	@Override
	public void setParameter(Object parameter) {
		// TODO Auto-generated method stub
		super.setParameter(parameter);
		addValidator(new SelectPlaceExistTreasureValidator((Place) parameter));
		addValidator(new CorpsPickTreasureEquipmentValidator((Corps) buffer.getCorps(), (Place) parameter));
		addValidator(new CorpsPickRangeValidator((Corps) buffer.getCorps(), (Place) parameter));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Corps corps = (Corps) buffer.getCorps();
		Place place = (Place) parameter;
		Treasure treasure = place.getTreasure();
		corps.pick(treasure);
	}
}
