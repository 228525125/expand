package org.cx.game.card.skill;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.GambleBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.observer.NotifyInfo;

/**
 * 孤注一掷
 * @author chenxian
 *
 */
public class Gamble extends ActiveSkill{

	private Integer range = 1;       
	private Integer maxTime = 5;     //最大连击次数
	private Integer downDef = 0;     //受伤比升高
	private Integer bout = 2;
	
	public Gamble(Integer consume, Integer cooldown, Integer velocity, Integer style, Integer func, Integer range, Integer maxTime, Integer downDef, Integer bout) {
		super(consume, cooldown, velocity, style, func);
		// TODO Auto-generated constructor stub
		this.range = range;
		this.maxTime = maxTime;
		this.downDef = downDef;
		this.bout = bout;
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
		
		Integer chance = getOwner().getAttack().getSpeedChance()-100;
		
		try {
			getOwner().attack(life);
			for(int i=0;i<maxTime;i++){
				if(Random.isTrigger(chance)){
					getOwner().attack(life);
					chance /= 2;
				}else{
					break;
				}
			}
		} catch (RuleValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new GambleBuff(bout, getStyle(), IBuff.Type_Harm, getFunc(), downDef, life).effect();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		LifeCard life = (LifeCard) objects[0];
		life.affected(this);
	}

}
