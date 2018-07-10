package org.cx.game.widget.building;

import java.util.Map;

import org.cx.game.widget.treasure.Resource;

public class ResourceBuilding extends AbstractBuilding {
	
	private Resource resource = null;
	
	public ResourceBuilding(Integer type) {
		super(type);
		// TODO Auto-generated constructor stub
		this.resource = new Resource();
		
		BuildOption optionBuild = new BuildOption();				
		addOption(optionBuild);
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	/**
	 * 产出
	 */
	public void output() {
		getPlayer().addToResource(this.resource);
	}
	
	@Override
	public void setConsume(Resource consume) {
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
		
		BuildingUpgradeOption optionBuildingUpgrade = new BuildingUpgradeOption();
		addOption(optionBuildingUpgrade);
	}

}
