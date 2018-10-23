package org.cx.game.core;

import org.cx.game.widget.AbstractGround;

public class Context extends AbstractContext {

	//private int bout = 0;  //回合	
	//private Integer day = 0; //天
	//private Integer week = 0; //星期几
	
	public Context(String playNo, AbstractGround ground) {
		// TODO Auto-generated constructor stub
		super(playNo, ground);
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		setPlayState(new StartState());
		super.start();
	}
	
	
	
	@Override
	public AbstractGround getGround() {
		// TODO Auto-generated method stub
		return (AbstractGround) super.getGround();
	}
	
	@Override
	public AbstractGround getGround(Integer id) {
		// TODO Auto-generated method stub
		if(null==getGround().getArea())
			return getGround();
		
		return (AbstractGround) getGround().getArea().getGround(id);
	}
	
	/*@Override
	public Integer getBout() {
		return bout;
	}
	
	private IAction addBoutAction = null;
	
	@Override
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
	
	@Override
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
	
	@Override
	public IAction getAddWeekAction(){
		if(null==this.addWeekAction){
			IAction aw = new ContextAddWeek();
			aw.setOwner(this);
			this.addWeekAction = aw;
		}
		return this.addWeekAction;
	}*/
	
	/*public class ContextAddBout extends AbstractAction implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			bout++;
			/*if(1==bout%getPlayerList().size()){
				addDay();
				if(1==day%7)
					addWeek();
			}
			
			AbstractPlayer player = getControlPlayer();			
			player.addBout();
		}
		
		@Override
		public Context getOwner() {
			// TODO Auto-generated method stub
			return (Context) super.getOwner();
		}
	}
	
	public class ContextAddDay extends AbstractAction implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			day++;
			
			/*
			 * 产出
			 
			AbstractGround ground = getOwner().getGround();
			List<AbstractBuilding> list = ground.getBuildingList();
			for(AbstractBuilding building : list){
				if(building instanceof TownBuilding){
					TownBuilding town = (TownBuilding) building;
					for(AbstractBuilding innerBuilding :town.getBuildings()){
						if(innerBuilding instanceof MineralBuilding){
							MineralBuilding br = (MineralBuilding) innerBuilding;
							br.output();           
						}
					}
				}
				
				if(building instanceof MineralBuilding){
					MineralBuilding br = (MineralBuilding) building;
					br.output();
				}
			}
		}
		
		@Override
		public Context getOwner() {
			// TODO Auto-generated method stub
			return (Context) super.getOwner();
		}
	}
	
	public class ContextAddWeek extends AbstractAction implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			week++;
			
			/*
			 * 产出
			
			AbstractGround ground = getOwner().getGround();
			List<AbstractBuilding> list = ground.getBuildingList();
			for(AbstractBuilding building : list){
				if(building instanceof TownBuilding){
					TownBuilding town = (TownBuilding) building;
					for(AbstractBuilding innerBuilding :town.getBuildings()){
						if(innerBuilding instanceof CallBuilding){
							CallBuilding bc = (CallBuilding) innerBuilding;
							bc.output();           
						}
					}
				}
			}
		}
		
		@Override
		public Context getOwner() {
			// TODO Auto-generated method stub
			return (Context) super.getOwner();
		}
	}*/

}
