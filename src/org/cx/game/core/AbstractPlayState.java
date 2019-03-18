package org.cx.game.core;

import java.util.Observable;

import org.cx.game.exception.RuleValidatorException;
import org.cx.game.out.ResponseFactory;

public abstract class AbstractPlayState extends Observable implements org.cx.game.observer.Observable{
	
	private IContext context;
	
	public AbstractPlayState() {
		// TODO Auto-generated constructor stub
		addObserver(ResponseFactory.getResponse());
	}

	public void setContext(IContext context) {
		this.context = context;
	}
	
	public IContext getContext() {
		return context;
	}
	
	/**
	 * 比赛开始
	 * @throws RuleValidatorException 
	 */
	public abstract void start();
	
	/**
	 * 玩家部署
	 * @throws RuleValidatorException 
	 */
	public abstract void deploy();
	
	/**
	 * 回合结束
	 * @throws RuleValidatorException 
	 */
	public abstract void done();
	
	/**
	 * 比赛结束
	 * @throws RuleValidatorException 
	 */
	public abstract void finish();
	
	@Override
	public void notifyObservers(Object arg0) {
		// TODO Auto-generated method stub
		super.setChanged();
		super.notifyObservers(arg0);
	}
}
