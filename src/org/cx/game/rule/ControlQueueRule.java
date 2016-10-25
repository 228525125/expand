package org.cx.game.rule;

import java.util.Map;
import java.util.Observable;

import org.cx.game.card.LifeCard;
import org.cx.game.card.effect.QuickAttack;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.IControlQueue;
import org.cx.game.widget.ControlQueue.Place;

public class ControlQueueRule implements IRule {

	private IControlQueue queue = null;
	
	public ControlQueueRule(IControlQueue cq) {
		// TODO Auto-generated constructor stub
		this.queue = cq;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof NotifyInfo) {
			NotifyInfo info = (NotifyInfo) arg;
			if(NotifyInfo.Card_LifeCard_Action_Death.equals(info.getType())){
				Map bean = (Map) info.getInfo();
				LifeCard life = (LifeCard) bean.get("card");
				getOwner().remove(life);
			}
			
			if(NotifyInfo.Card_LifeCard_State_Speed.equals(info.getType())){
				Map bean = (Map) info.getInfo();
				LifeCard life = (LifeCard) bean.get("card");
				Place place = getOwner().getPlace(life);
				place.loadSpeed();
				
				getOwner().refurbish();
			}
		}
	}
	
	@Override
	public IControlQueue getOwner() {
		// TODO Auto-generated method stub
		return this.queue;
	}

}
