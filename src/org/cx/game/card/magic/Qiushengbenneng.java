package org.cx.game.card.magic;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.ICard;
import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.QiushengbennengBuff;
import org.cx.game.core.Context;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;

public class Qiushengbenneng extends MagicCard {

	private Integer energyUpScale = 0;
	private Integer bout = 0;
	private LifeCard affected = null;
	
	public Qiushengbenneng(Integer id, Integer consume, Integer bout, Integer energyUpScale) {
		super(id, consume);
		// TODO Auto-generated constructor stub
		this.bout = bout;
		this.energyUpScale = energyUpScale;
		
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}

	@Override
	public Boolean needConjurer() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void apply(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.apply(objects);
		
		this.affected = (LifeCard) objects[0];
		affect();
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IBuff buff = new QiushengbennengBuff(bout, energyUpScale, affected);
		buff.effect();
	}
	
	@Override
	public List<Integer> getApplyRange(IGround ground) {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		List<Integer> ids = new ArrayList<Integer>();
		List<ICard> cardList = ground.list();
		for(ICard card : cardList){
			ids.add(card.getId());
		}
		List<Integer> stirps = Context.queryLifeStirps(LifeCard.Stirps_Beast);
		ids.retainAll(stirps);
		
		List<ICard> list = ground.listForID(getPlayer(), ids);
		for(ICard card : list){
			positionList.add(card.getContainerPosition());
		}
		return positionList;
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
