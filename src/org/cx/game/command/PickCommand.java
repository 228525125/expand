package org.cx.game.command;

import org.cx.game.core.IPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.AttackableValidator;
import org.cx.game.validator.MoveEnergyValidator;
import org.cx.game.validator.MoveTauntValidator;
import org.cx.game.validator.PickRangeValidator;
import org.cx.game.validator.PickTreasureEquipmentValidator;
import org.cx.game.validator.SelectCorpsValidator;
import org.cx.game.validator.SelectGroundValidator;
import org.cx.game.validator.SelectPlaceExistTreasureValidator;
import org.cx.game.widget.Place;
import org.cx.game.widget.treasure.ITreasure;

public class PickCommand extends InteriorCommand {

	public PickCommand(IPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
		addValidator(new SelectGroundValidator(buffer));
		addValidator(new SelectCorpsValidator(buffer));
		addValidator(new AttackableValidator(buffer));
		addValidator(new MoveEnergyValidator(buffer));
		addValidator(new MoveTauntValidator(buffer));
	}
	
	@Override
	public void setParameter(Object parameter) {
		// TODO Auto-generated method stub
		super.setParameter(parameter);
		addValidator(new SelectPlaceExistTreasureValidator((Place) parameter));
		addValidator(new PickTreasureEquipmentValidator((Corps) buffer.getCorps(), (Place) parameter));
		addValidator(new PickRangeValidator((Corps) buffer.getCorps(), (Place) parameter));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Corps corps = (Corps) buffer.getCorps();
		Place place = (Place) parameter;
		ITreasure treasure = place.getTreasure();
		corps.pick(treasure);
	}
}
