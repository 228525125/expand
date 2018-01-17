package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.IGround;

public class Death extends AbstractAction implements IDeath {
	
	private Integer hp = 0;
	private Integer hpLimit = 0;
	private Integer status = AbstractCorps.Death_Status_Exist;
	
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
	
	private IAction addToHpAction = null;
	
	public IAction getAddToHpAction() {
		if(null==addToHpAction){
			addToHpAction = new DeathAddToHpAction();
			addToHpAction.setOwner(this);
		}
		return addToHpAction;
	}

	/**
	 * 显式的改变HP
	 * @param hp
	 * @throws RuleValidatorException 
	 */
	public void addToHp(Integer hp) throws RuleValidatorException {
		// TODO Auto-generated method stub
		getAddToHpAction().execute(hp);
	}
	
	public Integer getHpLimit() {
		return hpLimit;
	}

	public void setHpLimit(Integer hpLimit) {
		if(!hpLimit.equals(this.hpLimit)){
			this.hpLimit = hpLimit;
		}
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		if(!status.equals(this.status)){
			this.status = status;
		}
	}

	@Override
	public void action(Object...objects) throws RuleValidatorException {

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("card", getOwner());
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(NotifyInfo.Corps_Death,map);
		super.notifyObservers(info);           //通知所有卡片对象，死亡事件		
		
		IGround ground = getOwner().getGround();     //只有在战场上才会死亡
		ground.inCemetery(getOwner());
		
		if(getOwner().getHero()){
			setStatus(AbstractCorps.Death_Status_Exist);
		}else{
			setStatus(AbstractCorps.Death_Status_Death);
		}
	}
	
	public class DeathAddToHpAction extends AbstractAction implements IAction {
		
		private Integer damage = 0;
		
		@Override
		public void action(Object... objects) throws RuleValidatorException {
			// TODO Auto-generated method stub
			Integer hp = (Integer) objects[0];
			
			if(!Integer.valueOf(0).equals(hp)){
				Integer before = Death.this.hp;
				
				Death.this.hp += hp;
				Death.this.hp = Death.this.hp>0 ? Death.this.hp : 0;       //判断下限
				Death.this.hp = Death.this.hp<getHpLimit() ? Death.this.hp : getHpLimit(); //判断上限
				
				this.damage = Death.this.hp - before;
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("card", getOwner().getOwner());
				map.put("change", this.damage);
				map.put("position", getOwner().getOwner().getPosition());
				NotifyInfo info = new NotifyInfo(NotifyInfo.Corps_Hp,map);
				super.notifyObservers(info);
				
				/*
				 * 判断死亡
				 */
				if(Death.this.hp.equals(0)){
					try {
						getOwner().action();
					} catch (RuleValidatorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		public Integer getDamage(){
			return this.damage;
		}
		
		@Override
		public IDeath getOwner() {
			// TODO Auto-generated method stub
			return (IDeath) super.getOwner();
		}
	}
}
