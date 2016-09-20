package org.cx.game.card.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;

/**
 * 范围型主动技能
 * @author chenxian
 *
 */
public abstract class CircleRangeAcitveSkill extends ActiveSkill {

	private Integer radius;
	
	public CircleRangeAcitveSkill(Integer consume, Integer cooldown,
			Integer velocity, Integer radius) {
		super(consume, cooldown, velocity);
		// TODO Auto-generated constructor stub
		this.radius = radius;
	}
	
	public List<LifeCard> getAffectedList() {
		List<LifeCard> ls = new ArrayList<LifeCard>();
		
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list = ground.easyAreaForDistance(getOwner().getContainerPosition(), radius, IGround.Contain);
		for(Integer position : list){
			LifeCard life = ground.getCard(position);
			if(null!=life){
				ls.add(life);
			}
		}
		
		return ls;
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		List<LifeCard> ls = getAffectedList();
		
		for(LifeCard life : ls){
			life.affected(this);
		}
	}
}
