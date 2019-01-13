package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Logger;
import org.cx.game.tools.Util;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.Scene;

public class Death extends AbstractAction implements IAction {
	
	private Integer hp = 0;
	private Integer hpLimit = 0;
	private Integer status = CommonIdentifierE.Death_Status_Live;

	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}
	
	/**
	 * 
	 * @param funType
	 * @param hp
	 * @return 如果是造成伤害，则返回伤害值，否则返回0；
	 */
	public void setHp(Integer funType, Integer hp) {
		Integer beforeHp = this.hp;
		this.hp = Util.operating(funType, this.hp, hp);
		this.hp = Death.this.hp>0 ? Death.this.hp : 0;       //判断下限
		this.hp = Death.this.hp<getHpLimit() ? Death.this.hp : getHpLimit(); //判断上限
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("card", getOwner());
		map.put("position", getOwner().getPosition());
		String desc = getOwner().getName()+"HP（"+beforeHp+"/"+getHpLimit()+"） 【改变】（"+(this.hp-beforeHp)+"）； 当前HP="+this.hp+"/"+getHpLimit();
		map.put("description", desc);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Death_Hp_Change,map);
		super.notifyObservers(info);
	}
	
	public Integer getHpLimit() {
		return hpLimit;
	}

	public void setHpLimit(Integer hpLimit) {
		this.hpLimit = hpLimit;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public void action(Object...objects) {

		Corps owner = (Corps) getOwner();
		
		setStatus(CommonIdentifierE.Death_Status_Death);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("card", getOwner());
		map.put("position", owner.getPosition());
		String desc = getOwner().getName()+" 【死亡】；";
		map.put("desciption", desc);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Death,map);
		super.notifyObservers(info);           //通知所有卡片对象，死亡事件
		
		Scene ground = (Scene) owner.getGround();     //只有在战场上才会死亡
		ground.inCemetery(owner);
		ground.getQueue().remove(owner);
	}
}
