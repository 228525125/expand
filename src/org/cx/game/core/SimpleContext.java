package org.cx.game.core;

import java.util.List;

import org.cx.game.action.Action;
import org.cx.game.action.IAction;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;
import org.cx.game.widget.building.BuildingCall;
import org.cx.game.widget.building.BuildingResource;
import org.cx.game.widget.building.BuildingTown;
import org.cx.game.widget.building.IBuilding;

public class SimpleContext extends Context {

	public SimpleContext(IGround ground, IPlayer[] players) {
		super(ground, players);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public PlayState getStartState() {
		// TODO Auto-generated method stub
		return new StartState();
	}
	
	@Override
	public PlayState getDeployState() {
		// TODO Auto-generated method stub
		return new DeployState();
	}
	
	@Override
	public PlayState getDoneState() {
		// TODO Auto-generated method stub
		return new DoneState();
	}
	
	@Override
	public PlayState getFinishState() {
		// TODO Auto-generated method stub
		return new FinishState();
	}
	
	@Override
	public void start() throws RuleValidatorException {
		// TODO Auto-generated method stub
		setPlayState(getStartState());
		super.start();
	}
	
	private IAction addBoutAction = null;
	
	public IAction getAddBoutAction(){
		if(null==this.addBoutAction){
			this.addBoutAction = new ContextAddBout();
			addBoutAction.setOwner(this);
		}
		return this.addBoutAction;
	}
	
	public void addBout() throws RuleValidatorException{
		getAddBoutAction().execute();
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
	public void addDay() throws RuleValidatorException {
		// TODO Auto-generated method stub
		getAddDayAction().execute();
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
	public void addWeek() throws RuleValidatorException {
		// TODO Auto-generated method stub
		getAddWeekAction().execute();
	}
	
	public class ContextAddBout extends Action implements IAction {
		
		@Override
		public void action(Object... objects) throws RuleValidatorException {
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
	
	public class ContextAddDay extends Action implements IAction {
		
		@Override
		public void action(Object... objects) throws RuleValidatorException {
			// TODO Auto-generated method stub
			day++;
			
			/*
			 * 产出
			 */
			IGround ground = getOwner().getGround();
			List<IBuilding> list = ground.getBuildingList();
			for(IBuilding building : list){
				if(building instanceof BuildingTown){
					BuildingTown town = (BuildingTown) building;
					for(IBuilding innerBuilding :town.getBuildings()){
						if(innerBuilding instanceof BuildingResource){
							BuildingResource br = (BuildingResource) innerBuilding;
							br.output();           
						}
					}
				}
				
				if(building instanceof BuildingResource){
					BuildingResource br = (BuildingResource) building;
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
	
	public class ContextAddWeek extends Action implements IAction {
		
		@Override
		public void action(Object... objects) throws RuleValidatorException {
			// TODO Auto-generated method stub
			week++;
			
			/*
			 * 产出
			 */
			IGround ground = getOwner().getGround();
			List<IBuilding> list = ground.getBuildingList();
			for(IBuilding building : list){
				if(building instanceof BuildingTown){
					BuildingTown town = (BuildingTown) building;
					for(IBuilding innerBuilding :town.getBuildings()){
						if(innerBuilding instanceof BuildingCall){
							BuildingCall bc = (BuildingCall) innerBuilding;
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
