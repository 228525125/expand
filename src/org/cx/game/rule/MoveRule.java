package org.cx.game.rule;

import java.util.Map;
import java.util.Observable;

import org.cx.game.action.IMove;
import org.cx.game.card.LifeCard;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.observer.NotifyInfo;

public class MoveRule implements IRule {

	private IMove move = null;
	
	public MoveRule(IMove move) {
		// TODO Auto-generated constructor stub
		this.move = move;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof NotifyInfo) {
			NotifyInfo info = (NotifyInfo) arg;
			
			if(NotifyInfo.Card_LifeCard_State_Hide.equals(info.getType())){
				Map bean = (Map) info.getInfo();
				Boolean hide = (Boolean) bean.get("hide");
				LifeCard owner = getOwner().getOwner();

			}
		}
	}

	@Override
	public IMove getOwner() {
		// TODO Auto-generated method stub
		return this.move;
	}

}
