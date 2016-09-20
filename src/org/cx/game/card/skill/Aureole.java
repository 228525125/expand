package org.cx.game.card.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.widget.IGround;

/**
 * 光环
 * @author chenxian
 *
 */
public abstract class Aureole extends PassiveSkill {

	private Integer range = 0;
	private List<LifeCard> affectedList = new ArrayList<LifeCard>();
	
	public Aureole(Integer range) {
		super();
		// TODO Auto-generated constructor stub
		this.range = range;
	}

	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getCall().addIntercepter(new Intercepter(){
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				refurbish();
				getOwner().getPlayer().getContext().addIntercepter(this);
			}
		});
		
		life.getDeath().addIntercepter(new Intercepter(){
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				getOwner().getPlayer().getContext().deleteIntercepter(this);
				for(LifeCard life : affectedList){
					List<IBuff> buffs = life.getBuff(getBuffClass());
					if(buffs.isEmpty())
						buffs.get(0).invalid();
				}
			}
		});
		
		life.getMove().addIntercepter(new Intercepter(){
			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				refurbish();
			}
		});
	}
	
	public List<LifeCard> getAffectedList() {
		return affectedList;
	}

	public void setAffectedList(List<LifeCard> affectedList) {
		this.affectedList = affectedList;
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
		refurbish();
	}

	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "deploy";
	}

	@Override
	public Boolean isInvoke() {
		// TODO Auto-generated method stub
		return true;
	}

}
