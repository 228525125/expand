package org.cx.game.card.magic;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.ICard;
import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.card.buff.HardSkinBuff;
import org.cx.game.card.magic.IMagic;
import org.cx.game.core.Context;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;

/**
 * 护体石肤
 * @author chenxian
 *
 */
public class HardSkin extends MagicCard {

	private Integer damageDownValue = 0;
	private Integer bout = 0;
	private LifeCard affected = null;
	
	public HardSkin(Integer id, Integer consume, Integer bout, Integer damageDownValue) {
		super(id, consume);
		// TODO Auto-generated constructor stub
		this.bout = bout;
		this.damageDownValue = damageDownValue;
		
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}

	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);

		new HardSkinBuff(this.bout, damageDownValue, this.affected).effect();
	}
	
	@Override
	public void apply(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.apply(objects);
		
		this.affected = (LifeCard) objects[0];
		affect();
	}

	@Override
	public Boolean needConjurer() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<Integer> getApplyRange(IGround ground) {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		List<Integer> ids = new ArrayList<Integer>();
		List<LifeCard> cardList = ground.list(getPlayer());
		for(LifeCard card : cardList){
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
