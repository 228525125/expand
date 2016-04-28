package org.cx.game.card.magic;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.MaimedBuff;
import org.cx.game.card.magic.IMagic;
import org.cx.game.card.trick.ITrick;
import org.cx.game.card.trick.PrickTrick;
import org.cx.game.card.trick.XianluoTrick;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.validator.ApplyRangeValidator;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IPlace;
import org.cx.game.widget.Place;

public class Xianluo extends MagicCard {

	private Integer bout = 2;
	private Integer defDownScale = 0;
	
	/**
	 * 
	 * @param id
	 * @param consume
	 * @param bout             持续回合
	 * @param defDownScale     防御下降比例
	 */
	public Xianluo(Integer id, Integer consume, Integer bout, Integer defDownScale) {
		super(id, consume);
		// TODO Auto-generated constructor stub
		this.bout = bout;
		this.defDownScale = defDownScale;
		
		setParameterTypeValidator(new Class[]{IPlace.class});
	}
	
	@Override
	public void apply(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.apply(objects);
		
		IPlace place = (IPlace) objects[0];
		
		doValidator(new ApplyRangeValidator(this, place.getPosition(), (IGround)place.getContainer()));
		if(hasError())
			throw new RuleValidatorException(getErrors().getMessage());
		
		ITrick trick = new XianluoTrick(ITrick.Setup_Bout, bout, defDownScale, place, getPlayer());
	}

	@Override
	public Boolean needConjurer() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public List<Integer> getApplyRange(IGround ground) {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		LifeCard conjure = getConjurer();
		Integer position = conjure.getContainerPosition();
		positionList = ground.easyAreaForDistance(position, conjure.getEnergy(), IGround.Contain);
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
