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
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Util;
import org.cx.game.widget.treasure.Mineral;
import org.cx.game.widget.treasure.Resource;

public class Player extends AbstractPlayer {
	
	public static final String Neutral = "neutral";
	
	private Integer bout = 0;
	
	private IAction addBoutAction = null;
	//private IPolicyGroup groupPolicy = null;
	
	private List<Integer> heroIDList = new ArrayList<Integer>();
	private List<AbstractCorps> heroList = new ArrayList<AbstractCorps>();
	private List<AbstractCorps> corpsList = new ArrayList<AbstractCorps>();
	
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
	
	@Override
	public IAction getAddBoutAction(){
		if(null==this.addBoutAction){
			this.addBoutAction = new PlayerAddBout();
			addBoutAction.setOwner(this);
		}
		return this.addBoutAction;
	}
	
	@Override
	public void setMineral(Mineral mineral) {
		// TODO Auto-generated method stub
		super.setMineral(mineral);
	}
	
	public List<Integer> getHeroIDList() {
		// TODO Auto-generated method stub
		return this.heroIDList;
	}
	
	public void addHeroID(Integer ID) {
		// TODO Auto-generated method stub
		this.heroIDList.add(ID);
	}
	
	public void addHero(AbstractCorps hero) {
		// TODO Auto-generated method stub
		this.heroList.add(hero);
	}
	
	public List<AbstractCorps> getHeroList() {
		// TODO Auto-generated method stub
		return this.heroList;
	}
	
	public List<AbstractCorps> getCorpsList() {
		return corpsList;
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
	
	public class PlayerAddBout extends AbstractAction implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			Player.this.bout++;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("player", Player.this);
			map.put("bout", Player.this.bout);
			NotifyInfo info = new NotifyInfo(CommonIdentifierE.Player_Bout,map);
			notifyObservers(info);
			
			/*
			 * 获得控制权的玩家单位被激活
			 */
			for(AbstractCorps corps : getOwner().getCorpsList()){
				Corps sc = (Corps) corps;
				Integer speed = sc.getActivate().getSpeed();
				sc.getActivate().setVigour(Util.Sum, speed);
				sc.activate(true);
			}
		}
		
		@Override
		public Player getOwner() {
			// TODO Auto-generated method stub
			return (Player) super.getOwner();
		}
	}
}
