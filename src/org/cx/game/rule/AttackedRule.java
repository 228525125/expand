package org.cx.game.rule;

import java.util.Map;
import java.util.Observable;

import org.cx.game.action.IAttack;
import org.cx.game.action.IAttacked;
import org.cx.game.card.LifeCard;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;

public class AttackedRule implements IRule {

	private IAttacked attacked = null;
	
	public AttackedRule(IAttacked attacked) {
		// TODO Auto-generated constructor stub
		this.attacked = attacked;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof NotifyInfo) {
			NotifyInfo info = (NotifyInfo) arg;
			
			if(NotifyInfo.Card_LifeCard_Action_Attacked.equals(info.getType())){
				Map bean = (Map) info.getInfo();	
				LifeCard attack = (LifeCard) bean.get("attack");
				LifeCard attacked = (LifeCard) bean.get("attacked");
				IAttack att = (IAttack) bean.get("ruleParam");
			
				if(getOwner().getFightBack() && !att.getCounterAttack()){
					try {
						attacked.getAttack().setCounterAttack(true);      //设置为反击
						attacked.attack(attack);
						attacked.getAttacked().setFightBack(false);
					} catch (RuleValidatorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public IAttacked getOwner() {
		// TODO Auto-generated method stub
		return this.attacked;
	}

}
