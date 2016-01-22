package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.IBuff;
import org.cx.game.card.skill.CircleRangeAcitveSkill;
import org.cx.game.exception.RuleValidatorException;

/**
 * 怒吼
 * @author chenxian
 *
 */
public class AngerRoar extends CircleRangeAcitveSkill {

	private Integer bout;
	private Integer atkScale;
	
	public AngerRoar(Integer consume, Integer cooldown, Integer velocity, Integer style, Integer func, Integer radius, Integer bout,
			Integer atkScale) {
		super(consume, cooldown, velocity, style, func, radius);
		// TODO Auto-generated constructor stub'
		this.bout = bout;
		this.atkScale = atkScale;
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		if(life.getPlayer().equals(getOwner().getPlayer()))
			new AngerRoarBuff(bout,getStyle(), IBuff.Type_Benefit, getFunc(), atkScale, life).effect();
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return 0;
	}
}
