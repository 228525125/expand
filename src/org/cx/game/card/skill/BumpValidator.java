package org.cx.game.card.skill;

import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IPlace;

public class BumpValidator extends Validator {

	private LifeCard affected = null;
	private LifeCard owner = null;
	private IGround ground = null;
	private Integer step = null;
	private Integer position = null;
	
	public BumpValidator(LifeCard affected, LifeCard owner, IGround ground) {
		// TODO Auto-generated constructor stub
		this.affected = affected;
		this.owner = owner;
		this.ground = ground;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true;
		List<Integer> list = this.ground.queryLineDistance(this.owner.getContainerPosition(), this.affected.getContainerPosition()); 
		if(list.isEmpty()){            //不在直线上
			addMessage(I18n.getMessage(this));
			ret = false;
		}
		
		this.step = list.size();
		list.remove(this.step-1);       //最后一个位置是affected
		
		for(Integer pos : list){
			IPlace place = this.ground.getPlace(pos);
			if(place.isDisable()){           //判断位置是否不可用
				addMessage(I18n.getMessage(this));
				ret = false;
			}
			
			this.position = pos;
		}
		
		return ret;
	}

	/**
	 * 
	 * @return 移动格数
	 */
	public Integer getStep() {
		return step;
	}

	/**
	 * 
	 * @return 发生撞击后，冲撞者的位置
	 */
	public IPlace getPlace() {
		return this.ground.getPlace(this.position);
	}

}
