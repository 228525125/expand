package org.cx.game.action;

import java.util.List;
import java.util.Map;
import java.util.Observer;

import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.ProxyFactory;
import org.cx.game.validator.Errors;
import org.cx.game.validator.IValidator;

/**
 * Action的包装类，该类辅助Action执行代理模式
 * @author chenxian
 *
 */
public class ActionProxyHelper implements IAction {

	private IAction original;
	
	public ActionProxyHelper(IAction action) {
		// TODO Auto-generated constructor stub
		this.original = action;
	}
	
	@Override
	public void action(Object... objects) {
		// TODO Auto-generated method stub
		Object proxy = ProxyFactory.getProxy(this.original);     
		((IAction)proxy).action(objects);		
	}
	
	@Override
	public void setOwner(Object owner) {
		// TODO Auto-generated method stub
		this.original.setOwner(owner);
	}
	
	@Override
	public Object getOwner() {
		// TODO Auto-generated method stub
		return original.getOwner();
	}
	
	@Override
	public void addIntercepter(IIntercepter intercepter) {
		// TODO Auto-generated method stub
		original.addIntercepter(intercepter);
	}
	
	@Override
	public void addObserver(Observer o) {
		// TODO Auto-generated method stub
		original.addObserver(o);
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		original.clear();
	}
	
	@Override
	public void deleteIntercepter(IIntercepter intercepter) {
		// TODO Auto-generated method stub
		original.deleteIntercepter(intercepter);
	}
	
	@Override
	public synchronized void deleteObserver(Observer o) {
		// TODO Auto-generated method stub
		original.deleteObserver(o);
	}
	
	@Override
	public synchronized int countObservers() {
		// TODO Auto-generated method stub
		return original.countObservers();
	}
	
	@Override
	public synchronized void deleteObservers() {
		// TODO Auto-generated method stub
		original.deleteObservers();
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return original.equals(obj);
	}
	
	@Override
	public Map<String,List<IIntercepter>> getIntercepterList() {
		// TODO Auto-generated method stub
		return original.getIntercepterList();
	}
	
	@Override
	public synchronized boolean hasChanged() {
		// TODO Auto-generated method stub
		return original.hasChanged();
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return original.hashCode();
	}
	
	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		original.notifyObservers();
	}
	
	@Override
	public void notifyObservers(Object arg) {
		// TODO Auto-generated method stub
		original.notifyObservers(arg);
	}
}
