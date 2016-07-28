package org.cx.game.card.magic;

import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.card.buff.JingshenhujiaBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 精神护甲
 * @author krw
 *
 */
public class Jingshenhujia extends MagicCard {

	private Integer hpUpValue = 0;
	private Integer bout = 0;
	private LifeCard affected = null;
	
	public Jingshenhujia(Integer id, Integer consume, Integer bout, Integer hpUpValue) {
		super(id, consume);
		// TODO Auto-generated constructor stub
		this.bout = bout;
		this.hpUpValue = hpUpValue;
		
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}

	@Override
	public Boolean needConjurer() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		new JingshenhujiaBuff(this.bout, this.hpUpValue, this.affected).effect();
	}
	
	@Override
	public void apply(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.apply(objects);
		
		this.affected = (LifeCard) objects[0];
		affect();
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

}
