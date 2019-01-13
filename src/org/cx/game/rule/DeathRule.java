package org.cx.game.rule;

import org.cx.game.action.Death;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.core.Player;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.tools.Logger;
import org.cx.game.widget.treasure.EmpiricValue;

public class DeathRule extends AbstractRule {

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		super.after(args);
		
		Corps corps = getOwner().getOwner();
		AbstractPlayer player = corps.getPlayer();
		
		player.getCorpsList().remove(corps);
		
		corps.clearBuff();
		corps.clearNexusBuff();
		
		//Logger.debug(this, corps.getName()+" [死亡] ;");
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
