package org.cx.game.widget.building;

import java.util.List;
import java.util.Map;

import org.cx.game.widget.treasure.IResource;

public class CallBuilding extends AbstractBuilding implements IBuilding {

	private Integer corpsID = null;       //可招募单位
	private Integer nop = 0;             //当前可招募数量
	private Integer yield = null;        //产量/天
	
	public CallBuilding(Integer corpsID, Integer yield, Integer buildingType) {
		// TODO Auto-generated constructor stub
		super(buildingType);
		
		this.corpsID = corpsID;
		this.yield = yield;
		
		IOption optionBuild = new BuildOption();				
		addOption(optionBuild);
		
		IOption callOption = new CallOption(corpsID);
		addOption(callOption);
	}

	public Integer getCorpsID() {
		return corpsID;
	}

	public Integer getNop() {
		return nop;
	}

	public Integer getYield() {
		return yield;
	}

	public void setYield(Integer yield) {
		this.yield = yield;
	}
	
	/**
	 * 产出
	 */
	public void output() {
		nop += yield;
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
	public void setUpgradeRequirement(Map<Integer, String> upgradeRequirement) {
		// TODO Auto-generated method stub
		super.setUpgradeRequirement(upgradeRequirement);
	}
	
	@Override
	public void setLevelLimit(Integer levelLimit) {
		// TODO Auto-generated method stub
		super.setLevelLimit(levelLimit);
	}
	
	@Override
	public void setNeedBuilding(List<Integer> needBuilding) {
		// TODO Auto-generated method stub
		super.setNeedBuilding(needBuilding);
	}
}
