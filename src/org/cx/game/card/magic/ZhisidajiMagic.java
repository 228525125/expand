package org.cx.game.card.magic;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;

public class ZhisidajiMagic extends MagicCard {
	
	public final static Integer ZhisidajiMagic_ID = 10150005;
	
	private Integer harm1 = 0;
	private Integer harm2 = 0;

	public ZhisidajiMagic(Integer harm1, Integer harm2, Integer consume) {
		super(ZhisidajiMagic_ID, consume);
		// TODO Auto-generated constructor stub
		
		this.harm1 = harm1;
		this.harm2 = harm2;
		
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		Integer hp = getOwner().getHeroCard().getDeath().getHp();
		Integer hrm = hp<12 ? this.harm2 : this.harm1;
		life.getAffected().magicHarm(hrm);
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
		List<Integer> positionList = new ArrayList<Integer>();
		LifeCard conjure = getConjurer();
		Integer position = conjure.getContainerPosition();
		positionList = ground.easyAreaForDistance(position, conjure.getAttack().getRange(), IGround.Contain);
		positionList.remove(position);
		
		List<Integer> list = new ArrayList<Integer>();
		for(Integer pos : positionList){
			if(null!=ground.getCard(pos))
				list.add(pos);
		}
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
