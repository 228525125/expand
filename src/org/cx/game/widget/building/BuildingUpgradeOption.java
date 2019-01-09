package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.Execute;
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
	private Boolean allow = false;
	
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
	public Boolean getAllow() {
		// TODO Auto-generated method stub
		return super.getAllow() && this.allow;
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
	
	private Execute execute = null;

	public Execute getExecute() {
		if(null==this.execute){
			Execute execute = new OptionBuildingUpgradeExecute();
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}
	
	@Override
	protected void beforeExecute() {
		// TODO Auto-generated method stub
		AbstractPlayer player = getOwner().getPlayer();
		player.setMineral(Util.Sub, (Mineral) getOwner().getUpgrade().getRequirement());
		
		getOwner().setStatus(AbstractBuilding.Building_Status_Build);
	}
	
	public class OptionBuildingUpgradeExecute extends Execute implements IAction{

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
		this.allow = level<levelLimit;
	}
}
