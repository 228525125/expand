package org.cx.game.policy.formula;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;
import org.cx.game.widget.GroundFactory;
import org.cx.game.widget.IGround;

/**
 * 攻击范围内的敌人
 * @author chenxian
 *
 */
public class ShechengneidedirenFormula extends Validator implements IFormula {
	
	private Corps corps = null;
	
	private List<Corps> enemyList = new ArrayList<Corps>();
	
	public ShechengneidedirenFormula(Corps corps) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Integer range = this.corps.getAttackRange();
		IGround ground = GroundFactory.getGround();
		List<Integer> list =ground.areaForDistance(this.corps.getPosition(), range, IGround.Equal);
		for(Integer p : list){
			Corps corps = (Corps) ground.getCorps(p);
			if(null!=corps && !this.corps.getPlayer().equals(corps.getPlayer()))
				this.enemyList.add(corps);
		}
		
		Boolean ret = false;
		if(this.enemyList.isEmpty()){
			addMessage(I18n.getMessage(this));
		}else{
			ret = true;
		}
		
		return ret;
	}
	
	public Corps getNearEnemy(){
		IGround ground = GroundFactory.getGround();
		Integer distance = this.corps.getAttackRange();
		Corps enemy = null;
		for(Corps corps : this.enemyList){
			Integer d = ground.distance(this.corps.getPosition(), corps.getPosition());
			if(d<=distance){
				distance = d;
				enemy = corps;
			}
		}
		return enemy;
	}
	
}
