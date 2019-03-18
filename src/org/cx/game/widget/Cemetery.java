package org.cx.game.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Corps;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.observer.NotifyInfo;

/**
 * 墓地，是基于ground每个格子都带一个墓地
 * @author chenxian
 *
 */
public class Cemetery implements ICemetery {
	
	private List<Corps> corpsList = new ArrayList<Corps>();
	
	private Place place;
	
	public Cemetery(Place place) {
		// TODO Auto-generated constructor stub
		super();
		this.place = place;
	}
	
	
	/**
	 * 进入墓地
	 */
	void add(Corps corps) {
		// TODO Auto-generated method stub
		corpsList.add(corps); 
	}
	
	/**
	 * 移出墓地
	 * @param corps
	 */
	void remove(Corps corps){				
		corpsList.remove(corps);
	}
	
	public Place getOwner() {
		// TODO Auto-generated method stub
		return place;
	}
	
	
	/**
	 * 因为Cemetery的getPosition(corps)返回Cemetery所在ground的position
	 * @param corps
	 * @return
	 */
	public Integer indexOf(Corps corps) {
		// TODO Auto-generated method stub
		return corpsList.indexOf(corps);
	}
	
	public Boolean contains(Corps corps) {
		// TODO Auto-generated method stub
		return corpsList.contains(corps);
	}
	
	public Integer getSize() {
		// TODO Auto-generated method stub
		return corpsList.size();
	}

	public Corps getCorps(Integer index) {
		// TODO Auto-generated method stub
		if(index<getSize())
			return corpsList.get(index);
		else
			return null;
	}

	public List<Corps> getList() {
		return corpsList;
	}
} 
