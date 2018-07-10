package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.Execute;
import org.cx.game.action.IAction;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.CorpsFactory;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.I18n;
import org.cx.game.validator.CallConsumeValidator;
import org.cx.game.validator.CallNopValidator;
import org.cx.game.validator.CallRangeValidator;
import org.cx.game.validator.CallUnitEqualValidator;
import org.cx.game.validator.RationLimitValidator;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.Place;
import org.cx.game.widget.building.BuildOption.OptionBuildExecute;

public class CallOption extends AbstractOption {

	private Integer corpsID = 0;
	private String name = null;
	
	public CallOption(Integer corpsId) {
		// TODO Auto-generated constructor stub
		this.corpsID = corpsId;
		
		//setParameterTypeValidator(new Class[]{IPlace.class}, new String[]{"empty"}, new Object[]{true});
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name){
			name = super.getName();
			name += I18n.getMessage(Corps.class, this.corpsID, "name");
		}
		return name;
	}
	
	@Override
	public List<Integer> getExecuteRange(AbstractGround ground) {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		positionList = ground.areaForDistance(getOwner().getPlace().getPosition(), 1, AbstractGround.Contain);
		positionList.retainAll(ground.queryEmptyList());
		return positionList;
	}
	
	private Execute execute = null;

	public Execute getExecute() {
		if(null==this.execute){
			Execute execute = new OptionCallExecute(this.corpsID);
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}
	
	@Override
	public void execute(Object...objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		Place place = (Place) objects[0];
		AbstractCorps corps = CorpsFactory.getInstance(corpsID, getOwner().getPlayer());
		
		if(null!=place.getCorps())            //如果是补充兵源，就判断招募的兵源是否一致
			addValidator(new CallUnitEqualValidator(place.getCorps(), (Corps) corps));
		
		addValidator(new CallConsumeValidator((Corps) corps, getNumber()));
		addValidator(new CallRangeValidator((AbstractBuilding) getOwner(), place));
		addValidator(new RationLimitValidator((Corps) corps, getNumber()));
		//验证单个队伍人口上限
		
		addValidator(new CallNopValidator((Corps) corps, getNumber(), getOwner()));
		
		super.execute(objects);		
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
	
	public class OptionCallExecute extends Execute implements IAction {
		
		private Integer corpsID = null;

		public OptionCallExecute(Integer corpsID) {
			// TODO Auto-generated constructor stub
			this.corpsID = corpsID;
		}
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			Place place = (Place) objects[0];
			
			Corps corps = (Corps) CorpsFactory.getInstance(corpsID, getOwner().getOwner().getPlayer());
			corps.call(place, getNumber());
		}
	}
}
