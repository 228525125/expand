package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.IBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.AbstractIntercepter;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IGroundE;

/**
 * 光环
 * @author chenxian
 *
 */
public abstract class Aureole extends PassiveSkill {

	public final static Integer Default_Effect_Range = 3;
	public final static Integer Default_AureoleBuff_Bout = 99;
	
	private Integer range = 0;
	private List<Corps> affectedList = new ArrayList<Corps>();
	
	public Aureole(Integer id, Integer range) {
		super(id);
		// TODO Auto-generated constructor stub
		this.range = range;
	}

	@Override
	public void setOwner(AbstractCorps corps) {
		// TODO Auto-generated method stub
		super.setOwner(corps);
		
		corps.getCall().addIntercepter(new AbstractIntercepter(){
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				refurbish();
				getOwner().getPlayer().getAddBoutAction().addIntercepter(this);
			}
		});
		
		corps.getDeath().addIntercepter(new AbstractIntercepter(){
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				getOwner().getPlayer().getAddBoutAction().deleteIntercepter(this);
				for(Corps corps : affectedList){
					List<IBuff> buffs = corps.getBuff(getBuffClass());
					if(buffs.isEmpty())
						buffs.get(0).invalid();
				}
			}
		});
		
		corps.getMove().addIntercepter(new AbstractIntercepter(){
			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				refurbish();
			}
		});
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}
	
	public abstract void leave(Corps corps);
	public abstract Class getBuffClass();
	
	public void into(Corps corps){
		try {
			corps.affected(this);
		} catch (RuleValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void refurbish(){
		IGroundE ground = getOwner().getPlayer().getContext().getGround();
		List<Corps> ls = ground.getCorpsList(getOwner().getPosition(), getRange(), IGround.Contain);
		
		List<Corps> tempList = new ArrayList<Corps>();  //将离开范围的corps去掉buff
		tempList.addAll(affectedList);
		
		tempList.removeAll(ls);
		for(Corps corps : tempList){
			Corps sc = (Corps) corps;
			leave(sc);
		}
		
		tempList.clear();                 //将新进范围的corps加上buff
		tempList.addAll(ls);
		
		tempList.removeAll(affectedList);
		for(Corps corps : tempList){
			Corps sc = (Corps) corps;
			into(sc);
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
