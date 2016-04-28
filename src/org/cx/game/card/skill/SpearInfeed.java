package org.cx.game.card.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.tools.Debug;
import org.cx.game.widget.IGround;

/**
 * 横扫
 * @author chenxian
 *
 */
public class SpearInfeed extends Spurting {

	public SpearInfeed(Integer atkScale) {
		super(atkScale);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<LifeCard> getAffectedList() {
		// TODO Auto-generated method stub
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list = ground.twoFlanks(getOwner().getContainerPosition(), ground.queryDirection(getOwner().getContainerPosition(), affected.getContainerPosition()));
		List<LifeCard> ret = new ArrayList<LifeCard>();
		for(Integer p : list){
			LifeCard life = ground.getCard(p);
			if((null!=life && Debug.isDebug) || (null!=life && !getOwner().getPlayer().equals(life.getPlayer())))   //判断敌友
				ret.add(life);
		}
		return ret;
	}
	
	private LifeCard affected = null;

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		affected = (LifeCard) ((Object[]) args[0])[0];
	}

}
