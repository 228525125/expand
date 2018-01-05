package org.cx.game.card.skill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.cx.game.action.IUpgrade;
import org.cx.game.action.UpgradeSkill;
import org.cx.game.card.LifeCard;
import org.cx.game.core.Context;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IInterceptable;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.out.JsonOut;
import org.cx.game.tools.I18n;

public abstract class Skill extends Observable implements ISkill {

	private Integer id;
	private String cType;
	private String name;
	private String depiction = null;
	private LifeCard owner;
	private String action = null;
	
	protected final static String UseSkill = "_UseSkill";
	
	public Skill(Integer id) {
		// TODO Auto-generated constructor stub
		this.id = id;
		
		addObserver(JsonOut.getInstance());
		
		/* 取类名 */
		String allName = this.getClass().getName();
		String packageName = this.getClass().getPackage().getName();
		this.cType = allName.substring(packageName.length()+1);
		setAction("Skill");
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("player", owner.getPlayer());
		map.put("container", owner.getGround());
		map.put("card", owner);
		map.put("skill", this);
		map.put("position", owner.getPosition());
		NotifyInfo info = new NotifyInfo(getAction()+UseSkill,map);
		notifyObservers(info);
	}
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
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
	
	public String getDepiction() {
		// TODO Auto-generated method stub
		if(null==depiction)
			depiction = I18n.getMessage(this, "depiction");
		return depiction;
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
	public Integer getRange() {
		// TODO Auto-generated method stub
		return owner.getAttack().getRange();
	}

	@Override
	public void notifyObservers(Object arg) {
		// TODO Auto-generated method stub
		super.setChanged();
		super.notifyObservers(arg);
	}

	@Override
	public Boolean contains(Integer tag) {
		// TODO Auto-generated method stub
		List<Integer> objectList = Context.queryForTag(tag);
		return objectList.contains(getId());
	}

	@Override
	public List<Integer> queryTagForCategory(Integer category) {
		// TODO Auto-generated method stub
		List<Integer> list1 =  Context.queryForCategory(category);
		List<Integer> list2 = Context.queryForObject(getId());
		list2.retainAll(list1);
		return list2;
	}

	@Override
	public List<Integer> queryTagForObject() {
		// TODO Auto-generated method stub
		return Context.queryForObject(getId());
	}
	
	@Override
	public Boolean isTrigger(Object[] args) {
		// TODO Auto-generated method stub
		return true;
	}

	private IUpgrade upgrade = null;

	public IUpgrade getUpgrade() {		
		if(null==upgrade){
			IUpgrade upgrade = new UpgradeSkill();
			upgrade.setOwner(this);
			this.upgrade = upgrade;
		}
		return upgrade;
	}
	
	@Override
	public void upgrade() throws RuleValidatorException {
		// TODO Auto-generated method stub
		this.upgrade.action();
	}

}
