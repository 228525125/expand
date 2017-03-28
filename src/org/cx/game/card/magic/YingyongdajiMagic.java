package org.cx.game.card.magic;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.ZengjiagongjiBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;

public class YingyongdajiMagic extends MagicCard {

	public final static Integer YingyongdajiMagic_ID = 10150004;
	
	private Integer atk = 0;
	
	public YingyongdajiMagic(Integer atk, Integer consume) {
		super(YingyongdajiMagic_ID, consume);
		// TODO Auto-generated constructor stub
		
		this.atk = atk;
		
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		IBuff buff = new ZengjiagongjiBuff(1, atk, life);
		buff.effect();
	}
	
	@Override
	public void apply(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.apply(objects);
		
		LifeCard life = (LifeCard) objects[0];
		life.affected(this);
	}
	
	@Override
	public List<Integer> getApplyRange(IGround ground) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		list.add(getOwner().getHeroCard().getContainerPosition());
		return list;
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
