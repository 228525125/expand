package org.cx.game.card.magic;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.exception.RuleValidatorException;

public class DispelMagic extends MagicCard {

	private Integer dispelNumber;
	
	public DispelMagic(Integer id, Integer consume, Integer dispelNumber) {
		super(id, consume);
		// TODO Auto-generated constructor stub
		this.dispelNumber = dispelNumber;
		
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
		
		List<IBuff> buffList = new ArrayList<IBuff>();
		LifeCard life = (LifeCard) objects[0];
		Integer i = 0;
		for(IBuff buff : life.getBuffList()){
			if(IBuff.Style_Magic==buff.getStyle()/*&&IBuff.Type_Harm==buff.getHostility()*/){
				buffList.add(buff);
				i += 1;
				if(i==dispelNumber)
					break;
			}
		}
		
		for(IBuff buff : buffList)
			buff.invalid();
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
}