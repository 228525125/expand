package org.cx.game.card;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;

/**
 * 环形范围魔法
 * @author chenxian
 *
 */
public abstract class CircleRangeMagicCard extends MagicCard {

	private Integer radius;
	private LifeCard affected;
	
	public CircleRangeMagicCard(Integer id, Integer consume, Integer radius) {
		super(id, consume);
		// TODO Auto-generated constructor stub
		this.radius = radius;
	}
	
	public List<LifeCard> getAffectedList() {
		List<LifeCard> ls = new ArrayList<LifeCard>();
		
		IGround ground = getOwner().getGround();
		List<Integer> list = ground.easyAreaForDistance(affected.getContainerPosition(), radius, IGround.Contain);
		for(Integer position : list){
			LifeCard life = ground.getCard(position);
			if(null!=life){
				ls.add(life);
			}
		}
		
		return ls;
	}
	
	@Override
	public void apply(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.apply(objects);
		
		this.affected = (LifeCard) objects[0];
		
		List<LifeCard> ls = getAffectedList();
		
		for(LifeCard life : ls){
			life.affected(this);
		}
	}

}
