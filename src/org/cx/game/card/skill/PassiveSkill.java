package org.cx.game.card.skill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Map.Entry;

import org.cx.game.card.ICard;
import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;
import org.cx.game.core.Context;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IInterceptable;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.IntercepterAscComparator;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.out.JsonOut;
import org.cx.game.tools.I18n;

/**
 * 被动技能
 * 除Dodge、AttackBack、Parry等系统级被动外，通常都要覆盖setOwer方法，并指定action；
 * @author chenxian
 *
 */
public abstract class PassiveSkill extends Observable implements IPassiveSkill {

	private String cType;
	private String name;
	private LifeCard owner;
	private Map<String,List<IIntercepter>> intercepterList = new HashMap<String,List<IIntercepter>>();
	private Boolean isDelete = false;
	private String action = null;
	private Integer style = IMagic.Style_physical;       //AttackBack、AttackLock等会用到
	private Integer func = IMagic.Func_Other;	
	
	private List<Map<IInterceptable, IIntercepter>> resetList = new ArrayList<Map<IInterceptable, IIntercepter>>();
	
	public PassiveSkill() {
		// TODO Auto-generated constructor stub
		addObserver(new JsonOut());
		
		/* 取类名 */
		String allName = this.getClass().getName();
		String packageName = this.getClass().getPackage().getName();
		this.cType = allName.substring(packageName.length()+1);
		setAction("Skill");
		
		this.func = Context.getMagicFunction(allName);
		this.style = Context.getMagicStyle(allName);
	}
	
	private final static String UseSkill = "_UseSkill";
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("player", owner.getPlayer());
		map.put("container", owner.getContainer());
		map.put("card", owner);
		map.put("skill", this);
		map.put("position", owner.getContainerPosition());
		NotifyInfo info = new NotifyInfo(getAction()+UseSkill,map);
		notifyObservers(info);
	}
	
	@Override
	public String getCType() {
		// TODO Auto-generated method stub
		return cType;
	}
	
	public String getName() {
		if(null==name)
			name = I18n.getMessage(this, "name");
		return name;
	}

	public LifeCard getOwner() {
		return owner;
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		this.owner = life;		
	}
	

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Boolean isInvoke() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return owner.getAttack().getRange();
	}
	
	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "action";
	}

	@Override
	public void addIntercepter(IIntercepter intercepter) {
		// TODO Auto-generated method stub
		List<IIntercepter> intercepters = intercepterList.get(intercepter.getIntercepterMethod());
		if(null!=intercepters){
			intercepters.add(intercepter);
		}else{
			intercepters = new ArrayList<IIntercepter>();
			intercepters.add(intercepter);
			intercepterList.put(intercepter.getIntercepterMethod(), intercepters);
		}
	}

	@Override
	public void deleteIntercepter(IIntercepter intercepter) {
		// TODO Auto-generated method stub
		/*List<IIntercepter> list = intercepterList.get(intercepter.getIntercepterMethod());
		if(null!=list){
			list.remove(intercepter);
		}*/
		
		intercepter.delete();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		intercepterList.clear();
	}

	@Override
	public Map<String,List<IIntercepter>> getIntercepterList() {
		// TODO Auto-generated method stub
		return intercepterList;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public Integer getStyle() {
		return style;
	}
	
	public Integer getFunc() {
		return func;
	}
	
	@Override
	public Integer getOrder() {
		// TODO Auto-generated method stub
		return IIntercepter.Order_Default;
	}
	
	@Override
	public Integer getLevel() {
		// TODO Auto-generated method stub
		return IIntercepter.Level_Current;
	}

	@Override
	public void notifyObservers(Object arg) {
		// TODO Auto-generated method stub
		super.setChanged();
		super.notifyObservers(arg);
	}
	
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		this.isDelete = true;
	}
	
	@Override
	public Boolean isDelete() {
		// TODO Auto-generated method stub
		return this.isDelete;
	}


}
