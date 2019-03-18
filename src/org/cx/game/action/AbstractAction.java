package org.cx.game.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.cx.game.intercepter.IIntercepter;
import org.cx.game.out.ResponseFactory;
import org.cx.game.rule.RuleGroupFactory;

public abstract class AbstractAction extends Observable implements IAction {

	private Object owner;
	
	private Map<String,List<IIntercepter>> intercepterList = new HashMap<String,List<IIntercepter>>();
	private List<IIntercepter> intercepterAppendList = new ArrayList<IIntercepter>();
	private List<IIntercepter> intercepterDeleteList = new ArrayList<IIntercepter>();
	
	private Map<String,Object> actionResultMap = new HashMap<String,Object>();

	public AbstractAction() {
		// TODO Auto-generated constructor stub
		addObserver(ResponseFactory.getResponse());
		
		RuleGroupFactory.bindingRule(this);
	}
	
	@Override
	public void setOwner(Object object) {
		// TODO Auto-generated method stub
		this.owner = object;
	}
	
	public Object getOwner() {
		return owner;
	}
	
	public void addActionResult(String name, Object obj) {
		this.actionResultMap.put(name, obj);
	}
	
	public Object getActionResult(String name) {
		return this.actionResultMap.get(name);
	}
	
	public void clearActionResult() {
		this.actionResultMap.clear();
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		clearActionResult();
	}

	@Override
	public void addIntercepter(IIntercepter intercepter) {
		// TODO Auto-generated method stub
		this.intercepterAppendList.add(intercepter);
	}

	@Override
	public void deleteIntercepter(IIntercepter intercepter) {
		// TODO Auto-generated method stub
		this.intercepterDeleteList.add(intercepter);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		intercepterList.clear();
	}

	@Override
	public Map<String,List<IIntercepter>> getIntercepterList() {
		// TODO Auto-generated method stub
		addIntercepter();
		deleteIntercepter();
		return intercepterList;
	}
	
	/**
	 * 将缓存里的拦截器，真正添加到被拦截对象
	 */
	private void addIntercepter() {
		for(IIntercepter intercepter : this.intercepterAppendList){
			List<IIntercepter> intercepters = intercepterList.get(intercepter.getIntercepterMethod());
			if(null!=intercepters){
				intercepters.add(intercepter);
			}else{
				intercepters = new ArrayList<IIntercepter>();
				intercepters.add(intercepter);
				intercepterList.put(intercepter.getIntercepterMethod(), intercepters);
			}
		}
		
		this.intercepterAppendList.clear();
	}
	
	/**
	 * 将缓存的拦截器，真正删除
	 */
	private void deleteIntercepter() {
		for(IIntercepter intercepter : this.intercepterDeleteList){
			List<IIntercepter> intercepters = intercepterList.get(intercepter.getIntercepterMethod());
			intercepters.remove(intercepter);
		}
		
		this.intercepterDeleteList.clear();
	}
	
	@Override
	public void notifyObservers(Object arg) {
		// TODO Auto-generated method stub
		super.setChanged();
		super.notifyObservers(arg);
	}
}
