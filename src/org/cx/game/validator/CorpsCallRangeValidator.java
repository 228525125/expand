package org.cx.game.validator;

import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.I18n;
import org.cx.game.widget.Ground;
import org.cx.game.widget.Place;
import org.cx.game.widget.building.AbstractBuilding;

/**
 * 判断call位置是否超出范围
 * @author chenxian
 * 
 */
public class CorpsCallRangeValidator extends SelectBuildingTypeValidator {

	private Place place;
	private AbstractBuilding buildingCall = null;
	
	public CorpsCallRangeValidator(AbstractBuilding buildingCall, Place place) {
		// TODO Auto-generated constructor stub
		super(buildingCall, CommonIdentifierE.Building_Call);
		this.buildingCall = buildingCall;
		this.place = place;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		if(ret){
			Ground ground = buildingCall.getPlayer().getContext().getGround();
			if(Integer.valueOf(1).equals(ground.distance(buildingCall.getPlace().getPosition(), place.getPosition())))
				ret = true;
			else{
				addMessage(I18n.getMessage(CorpsCallRangeValidator.class.getName()));
				ret = false;
			}
		}
		
		return ret;
	}
}
