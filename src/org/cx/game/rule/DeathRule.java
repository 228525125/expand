package org.cx.game.rule;

import org.cx.game.action.Death;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.core.Player;
import org.cx.game.corps.AbstractCorps;

public class DeathRule extends AbstractRule {

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		super.after(args);
		
		AbstractCorps corps = getOwner().getOwner();
		AbstractPlayer player = corps.getPlayer();
		
		player.getCorpsList().remove(corps);
		
		if(Player.Neutral.equals(player.getName()) && player.getCorpsList().isEmpty())
			player.getContext().removePlayer(player);
	}
	
	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return Death.class;
	}
	
	@Override
	public Death getOwner() {
		// TODO Auto-generated method stub
		return (Death) super.getOwner();
	}

}
