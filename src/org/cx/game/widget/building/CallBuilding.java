package org.cx.game.widget.building;

import java.util.List;
import java.util.Map;

import org.cx.game.widget.treasure.Mineral;
import org.cx.game.widget.treasure.Resource;

public class CallBuilding extends AbstractBuilding {

	private Integer corpsType = null;       //可招募单位
	private Integer nop = 0;             //当前可招募数量
	private Integer yield = null;        //产量/天
	
	public CallBuilding(Integer type, Integer corpsType, Integer yield) {
		// TODO Auto-generated constructor stub
		super(type);
		
		this.corpsType = corpsType;
		this.yield = yield;
	}
	
	@Override
	public void afterConstruct() {
		// TODO Auto-generated method stub
		BuildOption optionBuild = new BuildOption(this);
		addOption(optionBuild);
		
		CallOption callOption = new CallOption(this);
		addOption(callOption);
	}

	public Integer getCorpsType() {
		return corpsType;
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
