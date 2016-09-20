package org.cx.game.rule;

import java.util.Observable;

import org.cx.game.action.Death;
import org.cx.game.action.IDeath;
import org.cx.game.core.IPlayer;
import org.cx.game.observer.NotifyInfo;

public class DeathRule implements IRule {

	private IDeath death = null;
	
	public DeathRule(IDeath death) {
		// TODO Auto-generated constructor stub
		this.death = death;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof NotifyInfo) {
			NotifyInfo info = (NotifyInfo) arg;
			
			if(NotifyInfo.Card_LifeCard_Action_Death.equals(info.getType())){
				getOwner().setStatus(IDeath.Status_Death);
				
				getOwner().getOwner().initState();
			}
		}
	}

	@Override
	public IDeath getOwner() {
		// TODO Auto-generated method stub
		return this.death;
	}

}
