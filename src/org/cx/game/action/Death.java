package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Logger;
import org.cx.game.tools.Util;
import org.cx.game.widget.AbstractGround;

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
		
		Logger.debug(this, getOwner().getName()+"HP("+beforeHp+") [改变]("+(this.hp-beforeHp)+"); 当前HP="+this.hp);
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
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Death,map);
		super.notifyObservers(info);           //通知所有卡片对象，死亡事件
		
		AbstractGround ground = owner.getGround();     //只有在战场上才会死亡
		ground.inCemetery(owner);
	}
}
