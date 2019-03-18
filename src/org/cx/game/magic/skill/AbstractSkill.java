package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.cx.game.action.ActionProxyHelper;
import org.cx.game.action.IAction;
import org.cx.game.corps.Corps;
import org.cx.game.corps.Corps;
import org.cx.game.magic.IMagic;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.out.ResponseFactory;
import org.cx.game.tag.TagHelper;
import org.cx.game.tools.CommonIdentifier;
import org.cx.game.tools.I18n;
import org.cx.game.widget.IOption;

public abstract class AbstractSkill extends Observable implements ISkill {

	protected final static String Used = "_Used";
	
	private Integer type;
	private String name;
	private String depiction = null;	
	private String action = null;
	
	private Corps owner = null;
	
	private List<IOption> optionList = new ArrayList<IOption>();
	
	public AbstractSkill(Integer type) {
		// TODO Auto-generated constructor stub
		this.type = type;
		
		addObserver(ResponseFactory.getResponse());
		
		setAction("Skill");
	}
	
	/**
	 * 该方法在对象被创建后调用
	 */
	public abstract void afterConstruct();
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	@Override
	public Integer getType() {
		// TODO Auto-generated method stub
		return type;
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

	public Corps getOwner() {
		return owner;
	}
	
	public void setOwner(Corps corps) {
		// TODO Auto-generated method stub
		this.owner = corps;		
	}
	
	public void addOption(IOption option) {
		this.optionList.add(option);
	}
	
	public Boolean removeOption(IOption option) {
		return this.optionList.remove(option);
	}
	
	public List<IOption> getOptionList() {
		return this.optionList;
	}
	
	public IOption getOption(Integer index) {
		return getOptionList().get(index);
	}
	
	public IOption getOption(Class clazz) {
		IOption ret = null;
		for(IOption option : getOptionList()){
			if(option.getClass().equals(clazz)){
				ret = option;
				break;
			}
		}
		return ret;
	}
	
	public abstract IAction getUpgrade();
	
	public void upgrade() {
		// TODO Auto-generated method stub
		IAction action = new ActionProxyHelper(getUpgrade());
		action.action();
	}

	
	@Override
	public Boolean isTrigger(Object[] args) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("corps", owner);
		map.put("skill", this);
		map.put("position", owner.getPosition());
		NotifyInfo info = new NotifyInfo(CommonIdentifier.Skill_Used,map);
		notifyObservers(info);
	}
	
	//-------------------- ITag ----------------------
	
	@Override
	public Boolean contains(Integer tag) {
		// TODO Auto-generated method stub
		List<Integer> objectList = TagHelper.queryForTag(tag);
		return objectList.contains(getType());
	}

	@Override
	public List<Integer> queryTagForCategory(Integer category) {
		// TODO Auto-generated method stub
		List<Integer> list1 =  TagHelper.queryForCategory(category);
		List<Integer> list2 = TagHelper.queryForObject(getType());
		list2.retainAll(list1);
		return list2;
	}

	@Override
	public List<Integer> queryTagForObject() {
		// TODO Auto-generated method stub
		return TagHelper.queryForObject(getType());
	}
	
	//-------------------- ITag End ----------------------
	
	@Override
	public void notifyObservers(Object arg) {
		// TODO Auto-generated method stub
		super.setChanged();
		super.notifyObservers(arg);
	}

}
