package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IAction;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.I18n;
import org.cx.game.tools.Util;
import org.cx.game.validator.BuildingUpgradeConsumeValidator;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.treasure.Mineral;

public class BuildingUpgradeOption extends AbstractOption {
 
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
		
		inquiryBuildingIsAllowUpdate();
		AbstractBuilding b = (AbstractBuilding) building;
		addValidator(new BuildingUpgradeConsumeValidator(b));
	}
	
	@Override
	public List<Integer> getExecuteRange() {
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
	
	private BeforeExecute beforeExecute = null;
	private Execute execute = null;

	@Override
	public BeforeExecute getBeforeExecute() {
		// TODO Auto-generated method stub
		if(null==this.beforeExecute){
			this.beforeExecute = new OptionBuildingUpgradeBeforeExecute();
			this.beforeExecute.setOwner(this);
		}
		return this.beforeExecute;
	}
	
	@Override
	public Execute getExecute() {
		if(null==this.execute){
			Execute execute = new OptionBuildingUpgradeExecute();
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}
	
	class OptionBuildingUpgradeBeforeExecute extends BeforeExecute implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			AbstractPlayer player = getOwner().getOwner().getPlayer();
			player.setMineral(Util.Sub, (Mineral) getOwner().getOwner().getUpgrade().getRequirement());
			
			getOwner().setStatus(AbstractBuilding.Building_Status_Build);
		}
		
		@Override
		public BuildingUpgradeOption getOwner() {
			// TODO Auto-generated method stub
			return (BuildingUpgradeOption) super.getOwner();
		}
	}
	
	class OptionBuildingUpgradeExecute extends Execute implements IAction{

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			AbstractBuilding building = (AbstractBuilding) getOwner().getOwner();
			building.upgrade();
			
			BuildingUpgradeOption.this.inquiryBuildingIsAllowUpdate();
		}
	}
	
	/**
	 * 查看所属建筑物是否达到升级上限
	 */
	private void inquiryBuildingIsAllowUpdate() {
		Integer level = getOwner().getUpgrade().getLevel();
		Integer levelLimit = getOwner().getUpgrade().getLevelLimit();
		if(AbstractOption.Status_Executable.equals(getStatus()) && level<levelLimit)
			setStatus(AbstractOption.Status_Executable);
		else
			setStatus(AbstractOption.Status_Unenforceable); 
	}
}
