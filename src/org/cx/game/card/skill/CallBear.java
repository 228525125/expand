package org.cx.game.card.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.CardFactory;
import org.cx.game.card.ICard;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;

/**
 * 召唤熊
 * @author chenxian
 *
 */
public class CallBear extends ActiveSkill {

	public CallBear(Integer consume, Integer cooldown, Integer velocity,
			Integer style, Integer func) {
		super(consume, cooldown, velocity, style, func);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		List<Integer> cid = new ArrayList<Integer>();
		cid.add(10000);
		List<ICard> list = CardFactory.getInstances(cid);
		LifeCard bear = (LifeCard) list.get(0);
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> ps = ground.easyAreaForDistance(getOwner().getContainerPosition(), 1, IGround.Equal);
		for(Integer p : ps){
			LifeCard life = ground.getCard(p);
			if(null==life){				
				bear.call(ground.getPlace(p));
			}
		}
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		getOwner().affected(this);
	}

}
