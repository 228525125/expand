package org.cx.game.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.AbstractAction;
import org.cx.game.action.IAction;
import org.cx.game.ai.policy.DonePolicy;
import org.cx.game.command.Command;
import org.cx.game.command.CommandFactory;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.exception.SyntaxValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.LandformEffect;
import org.cx.game.widget.treasure.IResource;

public class Player extends AbstractPlayer implements IPlayerE {
	
	private Integer bout = 0;
	
	private IAction addBoutAction = null;
	//private IPolicyGroup groupPolicy = null;
	
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
	
	public List<Corps> getAttendantList(Boolean activate) {
		// TODO Auto-generated method stub
		List<Corps> list = new ArrayList<Corps>();
		for(AbstractCorps corps : getAttendantList(AbstractCorps.Death_Status_Live)){
			Corps sc = (Corps) corps;
			if(activate.equals(sc.getActivate().getActivation()))
				list.add(sc);
		}
		return list;
	}
	
	@Override
	public List<Corps> getAttendantList(Integer status) {
		// TODO Auto-generated method stub
		return getContext().getGround().getCorpsList(this, status);
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
		try {
			getContext().done();
		} catch (RuleValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 用于xml配置
	 * @param res
	 */
	public void setResource(IResource res){
		super.setResource(res);
	}
	
	public class PlayerAddBout extends AbstractAction implements IAction {
		
		@Override
		public void action(Object... objects) throws RuleValidatorException {
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
			for(AbstractCorps corps : getAttendantList(AbstractCorps.Death_Status_Live)){
				Corps sc = (Corps) corps;
				Integer speed = sc.getActivate().getSpeed();
				sc.getActivate().addToVigour(speed);
				try {
					sc.activate(true);
				} catch (RuleValidatorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		@Override
		public IPlayer getOwner() {
			// TODO Auto-generated method stub
			return (IPlayer) super.getOwner();
		}
	}
}
