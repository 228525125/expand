package org.cx.game.validator;

import org.cx.game.corps.Hero;
import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.widget.Place;
import org.cx.game.widget.treasure.EquipmentTreasure;

/**
 * 只有英雄才能拾取装备
 * @author chenxian
 *
 */
public class CorpsPickTreasureEquipmentValidator extends SelectPlaceExistTreasureValidator {

	private Corps corps = null;
	
	public CorpsPickTreasureEquipmentValidator(Corps corps, Place place) {
		super(place);
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		if(ret){
			if (getTreasure() instanceof EquipmentTreasure) {
				if (!(this.corps instanceof Hero)) {
					ret = false;
					addMessage(I18n.getMessage(CorpsPickTreasureEquipmentValidator.class.getName()));
				}
			}
		}
		
		return ret;
	}
}
