package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.AbstractBuff;
import org.cx.game.widget.Ground;
import org.cx.game.intercepter.AbstractIntercepter;

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
	public void setOwner(Corps corps) {
		// TODO Auto-generated method stub
		super.setOwner(corps);
		
		Corps cs = (Corps) corps;
		
		cs.getCall().addIntercepter(new AbstractIntercepter(){
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				refurbish();
				getOwner().getGround().getQueue().getAddBoutAction().addIntercepter(this);
			}
		});
		
		cs.getDeath().addIntercepter(new AbstractIntercepter(){
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				getOwner().getGround().getQueue().getAddBoutAction().deleteIntercepter(this);
				for(Corps corps : affectedList){
					List<AbstractBuff> buffs = corps.getBuff(getBuffClass());
					if(buffs.isEmpty())
						buffs.get(0).invalid();
				}
			}
		});
		
		cs.getMove().addIntercepter(new AbstractIntercepter(){
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
		((Corps) corps).affected(this);
	}

	private void refurbish(){
		Ground ground = (Ground) getOwner().getPlayer().getContext().getGround();
		List<Corps> ls = ground.queryCorpsList(getOwner().getPosition(), getRange(), Ground.Contain);
		
		List<Corps> tempList = new ArrayList<Corps>();  //将离开范围的corps去掉buff
		tempList.addAll(affectedList);
		
		tempList.removeAll(ls);
		for(Corps corps : tempList){
			leave(corps);
		}
		
		tempList.clear();                 //将新进范围的corps加上buff
		tempList.addAll(ls);
		
		tempList.removeAll(affectedList);
		for(Corps corps : tempList){
			into(corps);
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
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}

}
