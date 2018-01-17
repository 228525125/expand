package org.cx.game.policy.formula;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;
import org.cx.game.widget.GroundFactory;
import org.cx.game.widget.IGround;

/**
 * 出击范围内是否有敌人
 * @author chenxian
 *
 */
public class ChujifanweineidedirenFormula extends Validator implements IFormula {

	private Corps corps = null;
	
	private List<Corps> enemyList = new ArrayList<Corps>();
	
	public ChujifanweineidedirenFormula(Corps corps) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Integer range = this.corps.getMove().getEnergy();
		IGround ground = GroundFactory.getGround();
		List<Integer> list =ground.areaForDistance(this.corps.getPosition(), range, IGround.Contain, this.corps.getMove().getType(), this.corps.getPlayer());
		for(Integer pos : list){            //移动范围内的敌人
			Corps corps = (Corps) ground.getCorps(pos);
			if(null!=corps && !this.corps.getPlayer().equals(corps.getPlayer()))
				this.enemyList.add(corps);
			
			List<Integer> attackRangeList = ground.areaForDistance(pos, 1, IGround.Contain);
			attackRangeList.removeAll(list);
			for(Integer p : attackRangeList){       //移动距离+攻击距离范围内的敌人
				corps = (Corps) ground.getCorps(p);
				if(null!=corps && !this.corps.getPlayer().equals(corps.getPlayer()))
					this.enemyList.add(corps);
			}
		}
		
		
		
		Boolean ret = false;
		if(this.enemyList.isEmpty()){
			addMessage(I18n.getMessage(this));
		}else{
			ret = true;
		}
		
		return ret;
	}
	
	/**
	 * 
	 * @return 最近敌人附近的位置
	 */
	public Integer getPosition(){
		IGround ground = GroundFactory.getGround();
		Integer distance = this.corps.getMove().getEnergy() + this.corps.getAttackRange();
		Corps enemy = null;
		for(Corps corps : this.enemyList){
			Integer d = ground.distance(this.corps.getPosition(), corps.getPosition());
			if(d<=distance){
				distance = d;
				enemy = corps;
			}
		}
		List<Integer> l1 = ground.areaForDistance(enemy.getPosition(), 1, IGround.Contain);
		List<Integer> l2 = ground.areaForDistance(this.corps.getPosition(), this.corps.getMove().getEnergy(), IGround.Contain, this.corps.getMove().getType(), this.corps.getPlayer());
		l1.retainAll(l2);
		return l1.get(0);
	}
}
