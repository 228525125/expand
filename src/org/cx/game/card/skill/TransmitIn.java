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
 * 传送.回，将友军传送到自己身边
 * @author chenxian
 *
 */
public class TransmitIn extends ActiveSkill {

	public TransmitIn(Integer consume, Integer cooldown, Integer velocity) {
		super(consume, cooldown, velocity);
		// TODO Auto-generated constructor stub
		//还需要效验参数是否
		setParameterTypeValidator(new Class[]{LifeCard.class});		
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return getOwner().getAttack().getRange();
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list = ground.easyAreaForDistance(getOwner().getContainerPosition(), 1, IGround.Equal);
		Collections.shuffle(list);
		Integer position = null;
		for(Integer pos : list){
			if(null==ground.getCard(pos)){
				position = pos;
				break;
			}
		}
		
		ground.move(life, position, IMove.Type_Flash);
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		LifeCard life = (LifeCard) objects[0];
		doValidator(new OwnerValidator(life,getOwner().getPlayer(),true));
		if(hasError())
			throw new RuleValidatorException(getErrors().getMessage());
		
		life.affected(this);
	}

}
