package org.cx.game.ai.policy.formula;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cx.game.ai.policy.DistanceComparator;
import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;
import org.cx.game.widget.Ground;

/**
 * 攻击范围内有敌人
 * @author chenxian
 *
 */
public class ShechengneidedirenFormula extends Validator {
	
	private Corps corps = null;
	
	private List<Corps> enemyList = new ArrayList<Corps>();
	
	public ShechengneidedirenFormula(Corps corps) {
		// TODO Auto-generated constructor stub
		this.corps = corps;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		/*
		 * 搜索攻击范围内的敌人
		 */
		this.enemyList = searchEnemy(this.corps);
		
		Boolean ret = false;
		if(this.enemyList.isEmpty()){
			addMessage(I18n.getMessage(this));
		}else{
			ret = true;
		}
		
		return ret;
	}
	
	public Corps getNearEnemy(){
		Collections.sort(this.enemyList, new DistanceComparator(this.corps.getPosition()));
		return this.enemyList.get(0);
	}
	
	public static List<Corps> searchEnemy(Corps owner){
		List<Corps> enemyList = new ArrayList<Corps>();
		Integer range = owner.getAttackRange();
		Ground ground = owner.getGround();
		List<Integer> list =ground.areaForDistance(owner.getPosition(), range, Ground.Equal);
		for(Integer p : list){
			Corps enemy = (Corps) ground.getCorps(p);
			if(null!=enemy && !owner.getPlayer().equals(enemy.getPlayer()))
				enemyList.add(enemy);
		}
		
		return enemyList;
	}
	
}
