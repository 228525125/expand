package org.cx.game.card.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.tools.Debug;
import org.cx.game.widget.IGround;

/**
 * 夺命
 * @author chenxian
 *
 */
public class SpearLine extends Spurting {

	/**
	 * 
	 * @param atkScale 溅射伤害比例，基数是自身atk
	 * @param life
	 */
	public SpearLine(Integer atkScale) {
		super(atkScale);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<LifeCard> getAffectedList() {
		// TODO Auto-generated method stub
		List<LifeCard> list = new ArrayList<LifeCard>();
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> l = ground.line(getOwner().getContainerPosition(), ground.queryDirection(getOwner().getContainerPosition(), affected.getContainerPosition()), 2);
		if(2==l.size()){
			LifeCard life = ground.getCard(l.get(1));
			if((null!=life && Debug.isDebug)  || (null!=life && !getOwner().getPlayer().equals(life.getPlayer())))   //判断敌友
				list.add(life);
		}
		return list;
	}
	
	private LifeCard affected = null;

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		affected = (LifeCard) ((Object[]) args[0])[0];
	}

}
