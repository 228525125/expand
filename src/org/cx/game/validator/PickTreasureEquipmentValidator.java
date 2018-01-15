package org.cx.game.validator;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Hero;
import org.cx.game.tools.I18n;
import org.cx.game.widget.Place;
import org.cx.game.widget.treasure.TreasureEquipment;

/**
 * 只有英雄才能拾取装备
 * @author chenxian
 *
 */
public class PickTreasureEquipmentValidator extends SelectPlaceExistTreasureValidator {

	private Corps corps = null;
	
	public PickTreasureEquipmentValidator(Corps corps, Place place) {
		super(place);
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		if(ret){
			if (getTreasure() instanceof TreasureEquipment) {
				if (!(this.corps instanceof Hero)) {
					ret = false;
					addMessage(I18n.getMessage(PickTreasureEquipmentValidator.class.getName()));
				}
			}
		}
		
		return ret;
	}
}
