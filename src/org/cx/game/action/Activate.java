package org.cx.game.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.IBuff;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Debug;

public class Activate extends AbstractAction implements IAction {

	public static final Integer ActivationConsume = 100;
	
	private Boolean activation = false;
	private Integer speed = 100;
	private Integer vigour = 0;       //活力，活力为100时，可以行动一次，活力值越多，行动次数越多
	
	public Boolean getActivation() {
		// TODO Auto-generated method stub
		return this.activation;
	}

	public void setActivation(Boolean activation) {
		// TODO Auto-generated method stub
		this.activation = activation;
	}
	
	public Integer getSpeed() {
		// TODO Auto-generated method stub
		return this.speed;
	}
	
	public void setSpeed(Integer speed) {
		// TODO Auto-generated method stub
		if(this.speed!=speed){
			this.speed = speed;
		}
	}
	
	public void addToSpeed(Integer speed) {
		// TODO Auto-generated method stub
		if(!Integer.valueOf(0).equals(speed)){
			this.speed += speed;
			this.speed = this.speed < 0 ? 0 : this.speed;		
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("card", getOwner());
			map.put("change", speed);
			map.put("position", getOwner().getPosition());
			NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Speed,map);
			super.notifyObservers(info);
		}
	}
	
	public Integer getVigour() {
		// TODO Auto-generated method stub
		return this.vigour;
	}
	
	public void addToVigour(Integer vigour) {
		// TODO Auto-generated method stub
		if(!Integer.valueOf(0).equals(vigour)){
			this.vigour += vigour;
			this.vigour = this.vigour > 200 ? 200 : this.vigour;         //最多一个回合只能行动两次
		}
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		
		Boolean activate = (Boolean) objects[0];
		
		setActivation(activate);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("card", getOwner());
		map.put("position", getOwner().getPosition());
		map.put("activate", activate);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Activate,map);
		notifyObservers(info);
		
		Corps owner = getOwner();
		
		if(activation){
			owner.getAttack().setAttackable(true);
			owner.getMove().setMoveable(true);
			owner.getMove().setEnergy(owner.getEnergy());
			owner.getAttacked().setFightBack(true);
			
			addToVigour(ActivationConsume);
		}else{
			owner.getAttack().setAttackable(false);
			owner.getMove().setMoveable(false);
		}
	}
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}

}
