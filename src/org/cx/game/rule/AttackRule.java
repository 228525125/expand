package org.cx.game.rule;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.cx.game.action.IAttack;
import org.cx.game.action.IDeath;
import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.AttackLockBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.IGround;

public class AttackRule implements IRule {

	private IAttack attack = null;
	
	private IAttack clone = null;
	
	public AttackRule(IAttack attack) {
		// TODO Auto-generated constructor stub
		this.attack = attack;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof NotifyInfo) {
			NotifyInfo info = (NotifyInfo) arg;
			
			//判断攻击距离，决定是否上锁
			if(NotifyInfo.Card_LifeCard_Action_Attack.equals(info.getType())){
				Map bean = (Map) info.getInfo();
				LifeCard attacked = (LifeCard) bean.get("attacked");
				
				IGround ground = getOwner().getOwner().getPlayer().getGround();
				Integer distance = ground.easyDistance(attacked.getContainerPosition(), getOwner().getOwner().getContainerPosition());
				if(IDeath.Status_Live == attacked.getDeath().getStatus()
				&& 1==distance){                                           //近身
					
					List<IBuff> buffList = getOwner().getOwner().getNexusBuff(AttackLockBuff.class);
					for(IBuff buff : buffList)
						buff.invalid();
					
					new AttackLockBuff(2,getOwner().getOwner(),attacked).effect();
				}
				
				//判断攻击模式，远程近战攻击减半
				this.clone = getOwner().clone();
				if(IAttack.Mode_Far.equals(getOwner().getMode()) && 1==distance){
					clone.setMode(IAttack.Mode_Near);
					Integer atk = clone.getAtk()/2;
					clone.setAtk(atk);
				}
				
				//判断是否为反击
				if(getOwner().getCounterAttack()){
					getOwner().setCounterAttack(false);
				}
				
				//判断潜行状态
				LifeCard owner = getOwner().getOwner();
				if(owner.getMove().getHide()){
					owner.getMove().changeHide(false);
				}
			}
		}
	}

	@Override
	public IAttack getOwner() {
		// TODO Auto-generated method stub
		return this.attack;
	}
	
	public IAttack cloneForAttack(){
		return this.clone;
	}

}
