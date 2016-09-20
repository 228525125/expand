package org.cx.game.card.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;

public class TwoFlanksRangeActiveSkill extends ActiveSkill {

	private Integer range;
	private LifeCard affected;
	
	public TwoFlanksRangeActiveSkill(Integer consume, Integer cooldown,
			Integer velocity, Integer range) {
		super(consume, cooldown, velocity);
		// TODO Auto-generated constructor stub
		this.range = range;
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}
	
	public List<LifeCard> getAffectedList() {
		List<LifeCard> ls = new ArrayList<LifeCard>();
		
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list = ground.twoFlanks(getOwner().getContainerPosition(), ground.queryDirection(getOwner().getContainerPosition(), affected.getContainerPosition()));
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
		
		this.affected = (LifeCard) objects[0];
		
		List<LifeCard> ls = getAffectedList();
		
		for(LifeCard life : ls){
			life.affected(this);
		}
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return range;
	}

}
