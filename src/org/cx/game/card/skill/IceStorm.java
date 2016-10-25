package org.cx.game.card.skill;

import java.util.List;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IPlace;

/**
 * 冰风暴(未完成)
 * @author chenxian
 *
 */
public class IceStorm extends ActiveSkill {

	private Integer damage;
	private Integer freezeChance;
	
	public IceStorm(Integer consume, Integer cooldown, Integer velocity, Integer damage, Integer freezeChance) {
		super(consume, cooldown, velocity);
		// TODO Auto-generated constructor stub
		this.damage = damage;
		this.freezeChance = freezeChance;
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return getOwner().getAttack().getRange();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		IPlace place = (IPlace) objects[0];
		
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list = ground.easyAreaForDistance(place.getPosition(), 1, IGround.Equal);
		list.add(place.getPosition());   //包含目标位置
		for(Integer position : list){
			IPlace p = ground.getPlace(position);
			LifeCard life = p.getLife();
			if(null!=life){
				life.getDeath().addToHp(damage);
				if(Random.isTrigger(freezeChance)){
					
				}
			}
		}
		
		super.useSkill(objects);
	}

}
