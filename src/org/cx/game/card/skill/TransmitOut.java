package org.cx.game.card.skill;

import java.util.Collections;
import java.util.List;

import org.cx.game.action.IMove;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.validator.OwnerValidator;
import org.cx.game.widget.IGround;

/**
 * 传送.出，把自己传送到友军身边
 * @author chenxian
 *
 */
public class TransmitOut extends ActiveSkill {
	
	private Integer range = 8;
	
	public TransmitOut(Integer consume, Integer cooldown, Integer velocity, Integer range) {
		super(consume, cooldown, velocity);
		// TODO Auto-generated constructor stub
		setParameterTypeValidator(new Class[]{LifeCard.class});		
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return range;
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list = ground.easyAreaForDistance(life.getContainerPosition(), 1, IGround.Equal);
		Collections.shuffle(list);
		Integer position = null;
		for(Integer pos : list){
			if(null==ground.getCard(pos)){
				position = pos;
				break;
			}
		}
		
		ground.move(getOwner(), position, IMove.Type_Flash);
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		LifeCard life = (LifeCard) objects[0];
		addValidator(new OwnerValidator(life,getOwner().getPlayer(),true));
		super.useSkill(objects);
		
		life.affected(this);
	}

}
