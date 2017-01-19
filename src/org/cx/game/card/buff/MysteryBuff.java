package org.cx.game.card.buff;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.widget.IGround;

/**
 * 秘法
 * @author chenxian
 *
 */
public abstract class MysteryBuff extends Buff {
	
	public final static Integer Default_Bout = 99;
	public final static Integer Default_Range = 5;
	
	private Integer range = Default_Range;
	private List<LifeCard> affectedList = new ArrayList<LifeCard>();
	public MysteryBuff(Integer id, LifeCard life) {
		super(id, Default_Bout, life);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		Intercepter callIn = new Intercepter(){
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				refurbish();
			}
		};
		recordIntercepter(getOwner().getCall(), callIn);
		
		Intercepter deathIn = new Intercepter(){
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				for(LifeCard life : affectedList){
					List<IBuff> buffs = life.getBuff(getBuffClass());
					if(buffs.isEmpty())
						buffs.get(0).invalid();
				}
			}
		};
		recordIntercepter(getOwner().getDeath(), deathIn);
		
		Intercepter moveIn = new Intercepter(){
			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				refurbish();
			}
		};
		recordIntercepter(getOwner().getMove(), moveIn);
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}
	
	public abstract void leave(LifeCard life);
	public abstract Class getBuffClass();
	
	public void into(LifeCard life){
		try {
			life.affected(this);
		} catch (RuleValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void refurbish(){
		List<LifeCard> ls = new ArrayList<LifeCard>();
		
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list = ground.easyAreaForDistance(getOwner().getContainerPosition(), getRange(), IGround.Contain);
		for(Integer position : list){
			LifeCard life = ground.getCard(position);
			if(null!=life){
				ls.add(life);
			}
		}
		
		List<LifeCard> tempList = new ArrayList<LifeCard>();  //将离开范围的life去掉buff
		tempList.addAll(affectedList);
		
		tempList.removeAll(ls);
		for(LifeCard life : tempList){
			leave(life);
		}
		
		tempList.clear();                 //将新进范围的life加上buff
		tempList.addAll(ls);
		
		tempList.removeAll(affectedList);
		for(LifeCard life : tempList){
			into(life);
		}
		
		affectedList = ls;
	}
	
	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		super.finish(args);
		refurbish();
	}

	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "deploy";
	}
}
