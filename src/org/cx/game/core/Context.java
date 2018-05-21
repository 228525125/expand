package org.cx.game.core;

import java.util.List;

import org.cx.game.action.AbstractAction;
import org.cx.game.action.IAction;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IGroundE;
import org.cx.game.widget.building.CallBuilding;
import org.cx.game.widget.building.ResourceBuilding;
import org.cx.game.widget.building.TownBuilding;
import org.cx.game.widget.building.IBuilding;

public class Context extends AbstractContext implements IContextE {

	private int bout = 0;  //回合	
	private Integer day = 0; //天
	private Integer week = 0; //星期几
	
	public Context(IGround ground, IPlayer[] players) {
		super(ground, players);
		// TODO Auto-generated constructor stub
	}
	
	public IPlayer getPlayer(Integer troop) {
		// TODO Auto-generated method stub
		for(IPlayer player : getPlayerList()){
			if(troop.equals(player.getId()))
				return player;
		}
		return null;
	}
	
	@Override
	public AbstractPlayState getStartState() {
		// TODO Auto-generated method stub
		return new StartState();
	}
	
	@Override
	public AbstractPlayState getDeployState() {
		// TODO Auto-generated method stub
		return new DeployState();
	}
	
	@Override
	public AbstractPlayState getDoneState() {
		// TODO Auto-generated method stub
		return new DoneState();
	}
	
	@Override
	public AbstractPlayState getFinishState() {
		// TODO Auto-generated method stub
		return new FinishState();
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		setPlayState(getStartState());
		super.start();
	}
	
	@Override
	public int getBout() {
		return bout;
	}
	
	private IAction addBoutAction = null;
	
	public IAction getAddBoutAction(){
		if(null==this.addBoutAction){
			this.addBoutAction = new ContextAddBout();
			addBoutAction.setOwner(this);
		}
		return this.addBoutAction;
	}

	@Override
	public Integer getDay() {
		// TODO Auto-generated method stub
		return day;
	}
	
	private IAction addDayAction = null;
	
	public IAction getAddDayAction(){
		if(null==this.addDayAction){
			IAction ad = new ContextAddDay();
			ad.setOwner(this);
			this.addDayAction = ad;
		}
		return this.addDayAction;
	}
	
	@Override
	public Integer getWeek() {
		// TODO Auto-generated method stub
		return this.week;
	}
	
	private IAction addWeekAction = null;
	
	public IAction getAddWeekAction(){
		if(null==this.addWeekAction){
			IAction aw = new ContextAddWeek();
			aw.setOwner(this);
			this.addWeekAction = aw;
		}
		return this.addWeekAction;
	}
	
	@Override
	public IGroundE getGround() {
		// TODO Auto-generated method stub
		return (IGroundE) super.getGround();
	}
	
	public class ContextAddBout extends AbstractAction implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			bout++;
			if(1==bout%getPlayerList().size()){
				addDay();
				if(1==day%7)
					addWeek();
			}
			
			IPlayer player = getControlPlayer();
			
			try {
				player.addBout();
			} catch (RuleValidatorException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		@Override
		public IContext getOwner() {
			// TODO Auto-generated method stub
			return (IContext) super.getOwner();
		}
	}
	
	public class ContextAddDay extends AbstractAction implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			day++;
			
			/*
			 * 产出
			 */
			IGround ground = getOwner().getGround();
			List<IBuilding> list = ground.getBuildingList();
			for(IBuilding building : list){
				if(building instanceof TownBuilding){
					TownBuilding town = (TownBuilding) building;
					for(IBuilding innerBuilding :town.getBuildings()){
						if(innerBuilding instanceof ResourceBuilding){
							ResourceBuilding br = (ResourceBuilding) innerBuilding;
							br.output();           
						}
					}
				}
				
				if(building instanceof ResourceBuilding){
					ResourceBuilding br = (ResourceBuilding) building;
					br.output();
				}
			}
		}
		
		@Override
		public IContext getOwner() {
			// TODO Auto-generated method stub
			return (IContext) super.getOwner();
		}
	}
	
	public class ContextAddWeek extends AbstractAction implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			week++;
			
			/*
			 * 产出
			 */
			IGround ground = getOwner().getGround();
			List<IBuilding> list = ground.getBuildingList();
			for(IBuilding building : list){
				if(building instanceof TownBuilding){
					TownBuilding town = (TownBuilding) building;
					for(IBuilding innerBuilding :town.getBuildings()){
						if(innerBuilding instanceof CallBuilding){
							CallBuilding bc = (CallBuilding) innerBuilding;
							bc.output();           
						}
					}
				}
			}
		}
		
		@Override
		public IContext getOwner() {
			// TODO Auto-generated method stub
			return (IContext) super.getOwner();
		}
	}

}
