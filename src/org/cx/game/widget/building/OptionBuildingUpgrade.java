package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.Execute;
import org.cx.game.action.IExecute;
import org.cx.game.action.IUpgrade;
import org.cx.game.core.IPlayer;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.I18n;
import org.cx.game.validator.UpgradeConsumeValidator;
import org.cx.game.widget.IGround;

public class OptionBuildingUpgrade extends Option implements IOption {

	private String name = null;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name && null!=getOwner()){
			name = super.getName();
			name += I18n.getMessage(getOwner().getClass(), getOwner().getType(), "name");
		}
		return name;
	}
	
	@Override
	public Boolean getAllow() {
		// TODO Auto-generated method stub
		return IBuilding.Building_Status_Complete.equals(getOwner().getStatus()) && getOwner().isUpgrade();
	}
	
	@Override
	public List<Integer> getExecuteRange(IGround ground) {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		return positionList;
	}
	
	/**
	 * 默认为建筑周期
	 */
	@Override
	public Integer getExecuteWait() {
		// TODO Auto-generated method stub
		return getOwner().getBuildWait();
	}
	
	private IExecute execute = null;

	public IExecute getExecute() {
		if(null==this.execute){
			IExecute execute = new OptionBuildingUpgradeExecute();
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}
	
	@Override
	public void execute(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		IBuilding building = getOwner();
		getExecute().addValidator(new UpgradeConsumeValidator(building.getUpgrade(), building.getPlayer()));
		
		super.execute(objects);
	}
	
	@Override
	protected void beforeExecute() {
		// TODO Auto-generated method stub
		IPlayer player = getOwner().getPlayer();
		player.addToResource(getOwner().getUpgrade().getRequirement());
		
		getOwner().setStatus(IBuilding.Building_Status_Build);
	}
	
	public class OptionBuildingUpgradeExecute extends Execute implements IExecute{

		@Override
		public void action(Object... objects) throws RuleValidatorException {
			// TODO Auto-generated method stub
			super.action(objects);
			
			IBuilding building = getOwner().getOwner();
			building.upgrade();
		}
	}
}
