package org.cx.game.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.AbstractAction;
import org.cx.game.action.IAction;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.treasure.IResource;

public class Player extends AbstractPlayer implements IPlayerE {
	
	public static final String Neutral = "neutral";
	
	private Integer bout = 0;
	
	private IAction addBoutAction = null;
	//private IPolicyGroup groupPolicy = null;
	
	private List<Integer> heroIDList = new ArrayList<Integer>();
	private List<AbstractCorps> heroList = new ArrayList<AbstractCorps>();
	
	public Player(Integer id, String name) {
		super(id, name);
		// TODO Auto-generated constructor stub
		
		//this.groupPolicy = PolicyGroupFactory.getInstance(10450001);
		//this.groupPolicy.setOwner(this);
	}
	
	@Override
	public Integer getBout() {
		// TODO Auto-generated method stub
		return this.bout;
	}
	
	public IAction getAddBoutAction(){
		if(null==this.addBoutAction){
			this.addBoutAction = new PlayerAddBout();
			addBoutAction.setOwner(this);
		}
		return this.addBoutAction;
	}
	
	@Override
	public IContextE getContext() {
		// TODO Auto-generated method stub
		return (IContextE) super.getContext();
	}
	
	/**
	 * 使用AI自动操作
	 */
	public void automation(){
		/*while (this.equals(getContext().getControlPlayer())) {
			IPolicy policy = this.groupPolicy.getPolicy();
			if(null!=policy){
				policy.execute();
				
				if(policy instanceof DonePolicy)
					break;
			}
			else
				break;
		}*/
		getContext().done();
	}
	
	@Override
	public void dieOut() {
		// TODO Auto-generated method stub
		getContext().removePlayer(this);
	}
	
	/**
	 * 用于xml配置
	 * @param res
	 */
	public void setResource(IResource res){
		super.setResource(res);
	}
	
	@Override
	public List<Integer> getHeroIDList() {
		// TODO Auto-generated method stub
		return this.heroIDList;
	}
	
	@Override
	public void addHeroID(Integer ID) {
		// TODO Auto-generated method stub
		this.heroIDList.add(ID);
	}
	
	@Override
	public void addHero(AbstractCorps hero) {
		// TODO Auto-generated method stub
		this.heroList.add(hero);
	}
	
	@Override
	public List<AbstractCorps> getHeroList() {
		// TODO Auto-generated method stub
		return this.heroList;
	}
	
	public class PlayerAddBout extends AbstractAction implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			Player.this.bout++;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("player", Player.this);
			map.put("bout", Player.this.bout);
			NotifyInfo info = new NotifyInfo(NotifyInfo.Player_Bout,map);
			notifyObservers(info);
			
			/*
			 * 获得控制权的玩家单位被激活
			 */
			for(AbstractCorps corps : getContext().getGround().getCorpsList(getOwner(), AbstractCorps.Death_Status_Live)){
				Corps sc = (Corps) corps;
				Integer speed = sc.getActivate().getSpeed();
				sc.getActivate().addToVigour(speed);
				sc.activate(true);
			}
		}
		
		@Override
		public IPlayer getOwner() {
			// TODO Auto-generated method stub
			return (IPlayer) super.getOwner();
		}
	}
}
