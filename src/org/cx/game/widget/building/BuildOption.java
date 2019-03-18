package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IAction;
import org.cx.game.core.Player;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.Util;
import org.cx.game.validator.OperatePowerValidator;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.Ground;
import org.cx.game.widget.AbstractOption;

public class BuildOption extends AbstractOption {
 
	private String name = null;
	
	public BuildOption(AbstractBuilding building) {
		// TODO Auto-generated constructor stub
		setOwner(building);
	}
	
	@Override
	public List<Integer> getExecuteRange() {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		return list;
	}
	
	@Override
	public Integer getExecuteWait() {
		// TODO Auto-generated method stub
		return getOwner().getBuildWait();
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
	public void setOwner(Object building) {
		// TODO Auto-generated method stub
		super.setOwner(building);
		AbstractBuilding b = (AbstractBuilding) building;
		//addValidator(new BuildConsumeValidator(b));
	}

	private BeforeExecute beforeExecute = null;
	private Execute execute = null;

	@Override
	public BeforeExecute getBeforeExecute() {
		// TODO Auto-generated method stub
		if(null==this.beforeExecute){
			this.beforeExecute = new OptionBuildBeforeExecute();
			this.beforeExecute.setOwner(this);
		}
		return this.beforeExecute;
	}
	
	@Override
	public Execute getExecute() {
		if(null==this.execute){
			Execute execute = new OptionBuildExecute();
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name){
			name = super.getName();
			name += getOwner().getName();
		}
		
		return name;
	}
	
	class OptionBuildBeforeExecute extends BeforeExecute implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			AbstractBuilding building = getOwner().getOwner();
			//building.getPlayer().setMineral(Util.Sub, building.getConsume());
			building.setStatus(AbstractBuilding.Building_Status_Build);
		}
		
		@Override
		public BuildOption getOwner() {
			// TODO Auto-generated method stub
			return (BuildOption) super.getOwner();
		}
	}
	
	class OptionBuildExecute extends Execute implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			((AbstractBuilding) getOwner().getOwner()).build();
		}
	}
}
