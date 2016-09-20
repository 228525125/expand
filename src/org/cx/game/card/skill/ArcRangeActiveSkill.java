package org.cx.game.card.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IPlace;

public abstract class ArcRangeActiveSkill extends ActiveSkill {

	private Integer radius;
	private IPlace place;
	
	public ArcRangeActiveSkill(Integer consume, Integer cooldown,
			Integer velocity, Integer radius) {
		super(consume, cooldown, velocity);
		// TODO Auto-generated constructor stub
		this.radius = radius;
		setParameterTypeValidator(new Class[]{IPlace.class});
	}
	
	public List<LifeCard> getAffectedList() {
		List<LifeCard> ls = new ArrayList<LifeCard>();
		
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list = ground.arc(getOwner().getContainerPosition(), ground.queryDirection(getOwner().getContainerPosition(), place.getPosition()), radius);
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
		
		this.place = (IPlace) objects[0];
		
		List<LifeCard> ls = getAffectedList();
		
		for(LifeCard life : ls){
			life.affected(this);
		}
	}

}
