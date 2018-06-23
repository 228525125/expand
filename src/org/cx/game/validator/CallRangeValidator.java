package org.cx.game.validator;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.I18n;
import org.cx.game.widget.IGround;
import org.cx.game.widget.Place;
import org.cx.game.widget.building.IBuilding;

/**
 * 判断call位置是否超出范围
 * @author chenxian
 *
 */
public class CallRangeValidator extends SelectBuildingTypeValidator {

	private Place place;
	private IBuilding buildingCall = null;
	
	public CallRangeValidator(IBuilding buildingCall, Place place) {
		// TODO Auto-generated constructor stub
		super(buildingCall, new Integer[]{
				CommonIdentifierE.Building_Chengshi
				, CommonIdentifierE.Building_Ganglou
				, CommonIdentifierE.Building_Jianta
				, CommonIdentifierE.Building_Bingying
				, CommonIdentifierE.Building_Shijiuta
				, CommonIdentifierE.Building_Siyuan
				, CommonIdentifierE.Building_Xunlianchang});
		this.buildingCall = buildingCall;
		this.place = place;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		if(ret){
			IGround ground = buildingCall.getPlayer().getContext().getGround();
			if(Integer.valueOf(1).equals(ground.distance(buildingCall.getPlace().getPosition(), place.getPosition())))
				ret = true;
			else{
				addMessage(I18n.getMessage(CallRangeValidator.class.getName()));
				ret = false;
			}
		}
		
		return ret;
	}
}
