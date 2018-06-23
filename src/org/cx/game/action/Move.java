package org.cx.game.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Debug;
import org.cx.game.widget.AbstractPlace;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IGroundE;
import org.cx.game.widget.Place;

public class Move extends AbstractAction implements IAction {

	public static final Integer Consume = 1; //单格移动消耗
	public static final Integer Energy_Max = 20;      //最大精力，表示可移动到任意位置
	public static final Integer Energy_Min = 0;       //最小精力，表示不可移动
	public static final Integer TurnoverRatio = 60; //资源转换率
	
	private Integer energy = 0;
	private Integer consume = Consume;        //移动一格的消耗
	private Integer type = CommonIdentifierE.Move_Type_Walk;
	private Boolean moveable = false;   //是否能移动，回合内只能移动一次
	private Integer flee = 0;         //逃离成功率
	private Boolean hide = false;           //隐形状态
	
	private List<Integer> path = new ArrayList<Integer>();
	
	private Integer direction = IGround.Relative_Right;
	
	public Integer getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
	
	public void setType(Integer type) {
		// TODO Auto-generated method stub
		if(!this.type.equals(type))
			this.type = type;
	}
	
	public Integer getConsume() {
		return consume;
	}

	public Integer getEnergy() {
		return energy;
	}

	public void setEnergy(Integer energy) {
		if(!this.energy.equals(energy)){
			energy = 0>energy ? 0 : energy;
			this.energy = energy;
			
			if(Integer.valueOf(0).equals(this.energy))
				setMoveable(false);
		}
	}
	
	public Integer getFlee() {
		// TODO Auto-generated method stub
		return flee;
	}
	
	public void setFlee(Integer flee) {
		// TODO Auto-generated method stub
		if(!this.flee.equals(flee))
			this.flee = flee;
	}
	
	public Boolean getHide() {
		return hide;
	}

	/**
	 * 隐式的改变隐身状态
	 * @param hide
	 */
	public void setHide(Boolean hide) {
		if(!this.hide.equals(hide))
			this.hide = hide;
	}

	public Boolean getMoveable() {
		return moveable;
	}
	
	public void setMoveable(Boolean moveable) {
		if(!this.moveable.equals(moveable)){
			this.moveable = moveable;
			
			if(!this.moveable)
				setEnergy(0);
		}
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		if(!direction.equals(this.direction))
			this.direction = direction;
	}

	public List<Integer> getMovePath() {
		return path;
	}
	
	public void addMovePath(Integer position) {
		// TODO Auto-generated method stub
		this.path.add(position);
	}

	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}

	@Override
	public void action(Object...objects) {
		// TODO Auto-generated method stub
		
		AbstractPlace place = (Place) objects[0];
		
		Integer start = getOwner().getPosition();
		
		IGroundE ground = getOwner().getGround();
		List<Integer> route = ground.move(getOwner(), place.getPosition(), type);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("card", getOwner());
		map.put("route", route);
		map.put("start", start);
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Move,map);
		super.notifyObservers(info);
		
		/*
		 * 一个回合只能移动一次，移动攻击类型除外，例如骑兵
		 */
		if(!getOwner().getMobile())
			setMoveable(false);     
	}
}
