package org.cx.game.rule;

import java.util.Observable;

import org.cx.game.action.Death;
import org.cx.game.action.IRenew;
import org.cx.game.card.LifeCard;
import org.cx.game.core.IPlayer;
import org.cx.game.observer.NotifyInfo;

public class RenewRule implements IRule {

	private IRenew renew = null;
	
	public RenewRule(IRenew renew) {
		// TODO Auto-generated constructor stub
		this.renew = renew;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof NotifyInfo) {
			NotifyInfo info = (NotifyInfo) arg;
			
			if(NotifyInfo.Card_LifeCard_Action_Renew.equals(info.getType())){
				LifeCard owner = getOwner().getOwner(); 
				owner.getDeath().setStatus(Death.Status_Live);
				owner.getAttacked().setFightBack(true);
			}
		}
	}

	@Override
	public IRenew getOwner() {
		// TODO Auto-generated method stub
		return this.renew;
	}

}
