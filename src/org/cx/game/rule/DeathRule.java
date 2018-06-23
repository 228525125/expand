package org.cx.game.rule;

import java.util.List;

import org.cx.game.action.Death;
import org.cx.game.core.IPlayerE;
import org.cx.game.core.Player;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.IGroundE;

public class DeathRule extends AbstractRule implements IRule {

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		super.after(args);
		
		IPlayerE player = getOwner().getOwner().getPlayer();
		IGroundE ground = player.getContext().getGround();
		List<Corps> list = ground.getCorpsList(player, CommonIdentifierE.Death_Status_Live);
		if(Player.Neutral.equals(player.getName()) && list.isEmpty())
			player.dieOut();
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
