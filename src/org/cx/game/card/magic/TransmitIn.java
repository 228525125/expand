package org.cx.game.card.magic;

import java.util.Collections;
import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.action.IMove;
import org.cx.game.card.ICard;
import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.exception.CommandValidatorException;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.validator.ApplyRangeValidator;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IPlace;

/**
 * 传送
 * @author chenxian
 *
 */
public class TransmitIn extends MagicCard {

	public TransmitIn(Integer id, Integer consume) {
		super(id, consume);
		// TODO Auto-generated constructor stub
		
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}

	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub

		LifeCard life = (LifeCard) objects[0];
		
		IGround ground = getOwner().getGround();
		List<Integer> list = ground.easyAreaForDistance(getConjurer().getContainerPosition(), 1, IGround.Equal);
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
	public void apply(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.apply(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		doValidator(new ApplyRangeValidator(this, life.getContainerPosition(), (IGround) life.getContainer()));
		if(hasError())
			throw new RuleValidatorException(getErrors().getMessage());
		
		life.affected(this);
	}
	
	/**
	 * 这个方法必须覆盖，利用反射创建这个对象时会用到
	 */
	@Override
	public void setApply(IApply apply) {
		// TODO Auto-generated method stub
		super.setApply(apply);
	}
	
	/**
	 * 这个方法必须覆盖，利用反射创建这个对象时会用到
	 */
	@Override
	public void setChuck(IChuck chuck) {
		// TODO Auto-generated method stub
		super.setChuck(chuck);
	}
	
	/**
	 * 这个方法必须覆盖，利用反射创建这个对象时会用到
	 */
	@Override
	public void setConjurerList(List<Integer> conjurerList) {
		// TODO Auto-generated method stub
		super.setConjurerList(conjurerList);
	}

	@Override
	public Boolean needConjurer() {
		// TODO Auto-generated method stub
		return true;
	}

}
