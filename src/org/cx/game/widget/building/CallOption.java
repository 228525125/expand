package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IAction;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.CorpsFactory;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.I18n;
import org.cx.game.validator.CorpsCallConsumeValidator;
import org.cx.game.validator.CorpsCallNopLimitValidator;
import org.cx.game.validator.CorpsCallRangeValidator;
import org.cx.game.validator.CallUnitEqualValidator;
import org.cx.game.validator.OperatePowerValidator;
import org.cx.game.validator.RationLimitOfPlaceValidator;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Ground;
import org.cx.game.widget.Place;

public class CallOption extends AbstractOption {

	private Integer corpsType = 0;
	private String name = null;
	
	public CallOption(CallBuilding building) {
		// TODO Auto-generated constructor stub
		setOwner(building);
		this.corpsType = building.getCorpsType();
		
		//setParameterTypeValidator(new Class[]{IPlace.class}, new String[]{"empty"}, new Object[]{true});
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name){
			name = super.getName();
			name += I18n.getMessage(Corps.class, this.corpsType, "name");
		}
		return name;
	}
	
	@Override
	public AbstractBuilding getOwner() {
		// TODO Auto-generated method stub
		return (AbstractBuilding) super.getOwner();
	}
	
	@Override
	protected AbstractControlQueue getControlQueue() {
		// TODO Auto-generated method stub
		return getOwner().getPlace().getOwner().getQueue();
	}
	
	@Override
	public List<Integer> getExecuteRange() {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		Ground ground = (Ground) getOwner().getPlace().getOwner();
		positionList = ground.areaForDistance(getOwner().getPlace().getPosition(), 1, AbstractGround.Contain);
		positionList.retainAll(ground.queryPositionList(true));
		return positionList;
	}
	
	private Execute execute = null;

	public Execute getExecute() {
		if(null==this.execute){
			Execute execute = new OptionCallExecute();
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}
	
	@Override
	public void execute(Object...objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		Place place = (Place) objects[0];
		AbstractCorps corps = CorpsFactory.getInstance(corpsType);
		corps.setPlayer(getOwner().getPlayer());
		
		if(null!=place.getCorps())            //如果是补充兵源，就判断招募的兵源是否一致
			addValidator(new CallUnitEqualValidator(place.getCorps(), corpsType));
		
		addValidator(new CorpsCallConsumeValidator((Corps) corps, getNumber()));
		addValidator(new CorpsCallRangeValidator((AbstractBuilding) getOwner(), place));
		addValidator(new RationLimitOfPlaceValidator((Corps) corps, getNumber()));
		//验证单个队伍人口上限
		
		addValidator(new CorpsCallNopLimitValidator(getNumber(), (CallBuilding) getOwner()));
		
		super.execute(place,corps);		
	}
	
	@Override
	public void setSpacingWait(Integer spacing) {
		// TODO Auto-generated method stub
		super.setSpacingWait(spacing);
	}
	
	@Override
	public void setNumber(Integer number) {
		// TODO Auto-generated method stub
		super.setNumber(number);
	}
	
	class OptionCallExecute extends Execute implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			Place place = (Place) objects[0];
			Corps corps = (Corps) objects[1];
			
			corps.call(place, getNumber());
		}
	}
}
