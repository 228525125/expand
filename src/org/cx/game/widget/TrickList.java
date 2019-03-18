package org.cx.game.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.cx.game.corps.Corps;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.magic.trick.ITrick;
import org.cx.game.observer.NotifyInfo;

/**
 * 与Cemetery类似，每个格子都带一个TrickList，用于保存陷阱
 * @author chenxian
 *
 */
public class TrickList {

	private List<ITrick> trickList = new ArrayList<ITrick>();
	
	private Place place;
	
	public TrickList(Place place) {
		// TODO Auto-generated constructor stub
		super();
		this.place = place;
	}
	
	void add(ITrick trick){
		trickList.add(trick);
	}
	
	void remove(ITrick trick){
		trickList.remove(trick);
	}
	
	/**
	 * 因为TrickList的getPosition()返回TrickList所在ground的position
	 * @param trick
	 * @return
	 */
	public Integer indexOf(ITrick trick) {
		// TODO Auto-generated method stub
		return trickList.indexOf(trick);
	}
	
	public Integer getSize() {
		// TODO Auto-generated method stub
		return trickList.size();
	}

	public ITrick getTrick(Integer index) {
		// TODO Auto-generated method stub
		if(index<getSize())
			return trickList.get(index);
		else
			return null;
	}

	public Place getOwner() {
		// TODO Auto-generated method stub
		return place;
	}

	public Boolean contains(ITrick trick) {
		// TODO Auto-generated method stub
		return trickList.contains(trick);
	}

	public List<ITrick> getList() {
		return trickList;
	}
	
}
