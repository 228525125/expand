package org.cx.game.card.magic;

import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.card.buff.CureTiredBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 治疗
 * @author chenxian
 *
 */
public class Cure extends MagicCard {

	private Integer cureScale;
	private Integer tireBout;
	
	public Cure(Integer id, Integer consume, Integer style, Integer func, Integer cureScale, Integer tireBout) {
		super(id, consume, style, func);
		// TODO Auto-generated constructor stub
		this.cureScale = cureScale;
		this.tireBout = tireBout;
		
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}

	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		
		LifeCard life = (LifeCard) objects[0];
		
		Integer cureValue = life.getHp()*cureScale/100;  //保持下限
		life.getDeath().magicToHp(cureValue);
		new CureTiredBuff(tireBout,getStyle(),IBuff.Type_Benefit, getFunc(),life).effect();
	}
	
	@Override
	public void apply(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.apply(objects);
		
		LifeCard life = (LifeCard) objects[0];
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
		return false;
	}
}
