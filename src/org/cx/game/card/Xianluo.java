package org.cx.game.card;

import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.skill.IBuff;
import org.cx.game.card.skill.IMagic;
import org.cx.game.card.skill.ITrick;
import org.cx.game.card.skill.MaimedBuff;
import org.cx.game.card.skill.PrickTrick;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.validator.ApplyRangeValidator;
import org.cx.game.widget.IGround;
import org.cx.game.widget.Place;

public class Xianluo extends MagicCard {

	private Integer bout = 2;
	
	public Xianluo(Integer id, Integer consume, Integer bout) {
		super(id, consume, IMagic.Style_physical, IMagic.Func_Astrict);
		// TODO Auto-generated constructor stub
		this.bout = bout;
		
		setParameterTypeValidator(new Class[]{Place.class});
	}
	
	@Override
	public void apply(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.apply(objects);
		
		Place place = (Place) objects[0];
		
		doValidator(new ApplyRangeValidator(this, place.getPosition(), (IGround)place.getContainer()));
		if(hasError())
			throw new RuleValidatorException(getErrors().getMessage());
		
		place.getTrickList().add(new PrickTrick(ITrick.Setup_Bout, getStyle(), IBuff.Type_Harm, getFunc(), bout, MaimedBuff.DownEnergyScale_Max, place, getPlayer()));
	}

	@Override
	public Boolean needConjurer() {
		// TODO Auto-generated method stub
		return true;
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
