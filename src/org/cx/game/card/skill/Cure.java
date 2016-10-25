package org.cx.game.card.skill;

import java.util.Collections;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.CureTiredBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;

/**
 * 治疗，治疗量有下限，上限与攻击力相同
 * @author chenxian
 *
 */
public class Cure extends PassiveSkill {

	private Integer cureValue;
	private Integer tireBout;
	
	/**
	 * 
	 * @param consume
	 * @param cureValue 治疗量的下限，上限与攻击力相同
	 * @param life
	 */
	public Cure(Integer cureValue, Integer tireBout) {
		// TODO Auto-generated constructor stub
		this.cureValue = cureValue;
		this.tireBout = tireBout;
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return getOwner().getAttack().getRange();
	}
	
	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		Boolean activation = (Boolean)((Object[])args[0])[0];
		if(activation)
			affect();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		 
		LifeCard patient = getPatient();
		patient.getDeath().addToHp(cureValue);
		new CureTiredBuff(tireBout,patient).effect();
	}

	public Integer getCureValue() {
		return cureValue;
	}

	public void setCureValue(Integer cureValue) {
		this.cureValue = cureValue;
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getActivate().addIntercepter(this);
	}
	
	private LifeCard getPatient(){
		LifeCard life = null;
		
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list = ground.easyAreaForDistance(getOwner().getContainerPosition(), getRange(), IGround.Contain);
		list.remove(getOwner().getContainerPosition());
		Collections.shuffle(list);
		for(Integer position : list){
			LifeCard card = ground.getCard(position);
			if(null!=card && getOwner().getPlayer().equals(card.getPlayer())){
				if(card.getHp()>card.getDeath().getHp()){
					List<IBuff> buffs = card.getBuff(CureTiredBuff.class);
					if(buffs.isEmpty()){
						life = card;
						break;
					}
				}
			}
		}
		
		return life;
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		
	}
}
