package org.cx.game.widget.building;

import java.util.Map;

import org.cx.game.tools.Util;
import org.cx.game.widget.treasure.Mineral;
import org.cx.game.widget.treasure.Resource;

public class MineralBuilding extends AbstractBuilding {
	
	private Mineral mineral = null;
	
	public MineralBuilding(Integer type) {
		super(type);
		// TODO Auto-generated constructor stub
		this.mineral = new Mineral();
	}
	
	@Override
	public void afterConstruct() {
		// TODO Auto-generated method stub
		BuildOption optionBuild = new BuildOption(this);				
		addOption(optionBuild);
	}

	public Mineral getMineral() {
		return mineral;
	}

	public void setMineral(Mineral mineral) {
		this.mineral = mineral;
	}

	/**
	 * 产出
	 */
	public void output() {
		getPlayer().setMineral(Util.Sum, this.mineral);
	}
	
	@Override
	public void setConsume(Mineral consume) {
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
