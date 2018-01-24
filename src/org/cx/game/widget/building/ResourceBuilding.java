package org.cx.game.widget.building;

import java.util.Map;

import org.cx.game.widget.treasure.IResource;
import org.cx.game.widget.treasure.Resource;

public class ResourceBuilding extends AbstractBuilding {
	
	private IResource resource = null;
	
	public ResourceBuilding(Integer buildingType) {
		super(buildingType);
		// TODO Auto-generated constructor stub
		this.resource = new Resource();
		
		IOption optionBuild = new BuildOption();				
		addOption(optionBuild);
		
		IOption optionBuildingUpgrade = new BuildingUpgradeOption();
		addOption(optionBuildingUpgrade);
	}

	public IResource getResource() {
		return resource;
	}

	public void setResource(IResource resource) {
		this.resource = resource;
	}

	/**
	 * 产出
	 */
	public void output() {
		getPlayer().addToResource(this.resource);
	}
	
	@Override
	public void setConsume(IResource consume) {
		// TODO Auto-generated method stub
		super.setConsume(consume);
	}
	
	@Override
	public void setBuildWait(Integer bout) {
		// TODO Auto-generated method stub
		super.setBuildWait(bout);
	}
	
	@Override
	public void setLevelLimit(Integer levelLimit) {
		// TODO Auto-generated method stub
		super.setLevelLimit(levelLimit);
	}
	
	@Override
	public void setUpgradeRequirement(Map<Integer, String> upgradeRequirement) {
		// TODO Auto-generated method stub
		super.setUpgradeRequirement(upgradeRequirement);
	}

}
