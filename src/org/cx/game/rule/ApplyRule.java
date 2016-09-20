package org.cx.game.rule;

import java.util.Map;
import java.util.Observable;

import org.cx.game.action.IApply;
import org.cx.game.core.IPlayer;
import org.cx.game.observer.NotifyInfo;

public class ApplyRule implements IRule {

	private IApply apply = null;
	
	public ApplyRule(IApply apply) {
		// TODO Auto-generated constructor stub
		this.apply = apply;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof NotifyInfo) {
			NotifyInfo info = (NotifyInfo) arg;
			
			if(NotifyInfo.Card_MagicCard_Apply.equals(info.getType())){
				IPlayer player = getOwner().getOwner().getPlayer();
				player.addToResource(-getOwner().getConsume());
			}
		}
	}

	@Override
	public IApply getOwner() {
		// TODO Auto-generated method stub
		return this.apply;
	}

}
