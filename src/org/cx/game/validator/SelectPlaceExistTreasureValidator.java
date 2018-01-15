package org.cx.game.validator;

import org.cx.game.tools.I18n;
import org.cx.game.widget.Place;
import org.cx.game.widget.treasure.ITreasure;

/**
 * 验证某个位置上是否有物品
 * @author chenxian
 *
 */
public class SelectPlaceExistTreasureValidator extends Validator {
	
	private Place place = null;
	private ITreasure treasure = null;

	public SelectPlaceExistTreasureValidator(Place place) {
		// TODO Auto-generated constructor stub
		this.place = place;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub		
		this.treasure = place.getTreasure();
		Boolean ret = null!=this.treasure;
		
		if(!ret){
			addMessage(I18n.getMessage(SelectPlaceExistTreasureValidator.class.getName()));
		}
		
		return ret;
	}
	
	protected ITreasure getTreasure(){
		return this.treasure;
	}
}
