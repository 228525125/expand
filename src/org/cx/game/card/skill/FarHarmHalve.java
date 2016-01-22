package org.cx.game.card.skill;

import org.cx.game.action.IAttack;
import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.SimplePassiveSkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.IGround;

/**
 * 远程伤害减半
 * @author chenxian
 *
 */
public class FarHarmHalve extends SimplePassiveSkill {

	private LifeCard attack;
	private Integer elevateScale;   //百分比
	
	public FarHarmHalve(Integer style, Integer chance, Integer elevateScale) {
		// TODO Auto-generated constructor stub
		super(style);
		this.elevateScale = elevateScale;
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		attack = (LifeCard) ((Object[]) args[0])[0];
		
		IGround ground = getOwner().getPlayer().getGround();
		Integer distance = ground.easyDistance(attack.getContainerPosition(), getOwner().getContainerPosition());
		
		if(IAttack.Mode_Far.equals(attack.getAttack().getMode()) && 1<distance){
			Integer atk = attack.getAttack().getAtk();
			Integer elevateValue = atk*elevateScale;
			addToEruptAtk(elevateValue);
			affect();
		}
	}
}
