package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.Execute;
import org.cx.game.action.IAction;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.validator.BuildConsumeValidator;
import org.cx.game.widget.IGround;

public class BuildOption extends AbstractOption {

	private String name = null;
	
	@Override
	public List<Integer> getExecuteRange(IGround ground) {
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
	public void setOwner(IBuilding building) {
		// TODO Auto-generated method stub
		super.setOwner(building);
		
		getExecute().addValidator(new BuildConsumeValidator(getOwner()));
	}

	private Execute execute = null;

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

	@Override
	public Boolean getAllow() {
		// TODO Auto-generated method stub
		Boolean ret = true;
		
		/*
		 * 判断基础建筑物是否建造
		 */
		if(getOwner().getOwner() instanceof TownBuilding && !getOwner().getNeedBuilding().isEmpty()){
			TownBuilding town = (TownBuilding) getOwner().getOwner();
			List<IBuilding> buildings = town.getBuildings();
			List<Integer> list = new ArrayList<Integer>();
			for(IBuilding building : buildings){
				if(IBuilding.Building_Status_Complete.equals(building.getStatus()))
					list.add(building.getType());
			}
			
			for(Integer type : getOwner().getNeedBuilding()){
				if(!list.contains(type)){
					ret = false;
					break;
				}
			}
		}
			
		return ret && IBuilding.Building_Status_Nothingness.equals(getOwner().getStatus());
	}
	
	@Override
	public void beforeExecute() {
		// TODO Auto-generated method stub
		IBuilding building = getOwner();
		building.getPlayer().addToResource(building.getConsume());
		building.setStatus(IBuilding.Building_Status_Build);
	}
	
	public class OptionBuildExecute extends Execute implements IAction {

		@Override
		public void action(Object... objects) throws RuleValidatorException {
			// TODO Auto-generated method stub
			super.action(objects);
			
			getOwner().getOwner().build();
		}
	}
}