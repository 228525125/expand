package org.cx.game.rule;

import java.util.List;

import org.cx.game.action.Death;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.core.Player;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.AbstractGround;

public class DeathRule extends AbstractRule {

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		super.after(args);
		
		AbstractPlayer player = getOwner().getOwner().getPlayer();
		AbstractGround ground = player.getContext().getGround();
		List<AbstractCorps> list = ground.getCorpsList(player, CommonIdentifierE.Death_Status_Live);
		if(Player.Neutral.equals(player.getName()) && list.isEmpty())
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
