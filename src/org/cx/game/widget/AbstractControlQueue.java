package org.cx.game.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.AbstractAction;
import org.cx.game.action.ActionProxyHelper;
import org.cx.game.action.IAction;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifier;

public abstract class AbstractControlQueue<T> {

	/**
	 * 获取一次控制权需要消耗的能量
	 */
	static final Integer consume = 100;
	
	private Integer bout = 1;
	
	//private Map<Integer,List<Place<T>>> map = new HashMap<Integer,List<Place<T>>>(); 
	private List<Place<T>> queue = new ArrayList<Place<T>>();        //当前队列
	
	private InsertAction insertAction = null;
	private RefurbishAction refurbishAction = null;
	private TakeOutAction takeOutAction = null;
	
	public Integer getLength() {
		return this.queue.size();
	}
	
	public List<Place<T>> getQueue() {
		return queue;
	}
	
	public Integer getBout() {
		return bout;
	}
	
	private IAction addBoutAction = null;
	
	public IAction getAddBoutAction(){
		if(null==this.addBoutAction){
			this.addBoutAction = new ContextAddBout();
			addBoutAction.setOwner(this);
		}
		return this.addBoutAction;
	}
	
	private void addBout() {
		// TODO Auto-generated method stub
		IAction action = new ActionProxyHelper(getAddBoutAction());
		action.action();
	}
	
	/**
	 * 将一个元素加入到控制列表中，插入时不用考虑位置
	 * @param object
	 */
	public abstract void add(T t);
	
	/**
	 * 移除一个元素
	 * @param t
	 */
	public void remove(T t) {
		Place<T> place = getPlace(t);
		takeOut(place);
	}
	
	/**
	 * 
	 * @return 返回排序最靠前的一个元素
	 */
	public T out() {
		Place<T> place = queue.get(0);  //返回最前面的一个place
		
		while(consume>place.getCount()){        //排在最前面的place.count<100，就本回合所有单位行动完毕
			renew();
			refurbish();
			addBout();
			place = queue.get(0);
		}
		
		takeOut(place);        //从整个队列中取出place
		
		Integer count = place.getCount();
		place.setCount(count - consume);
		
		insert(place);
		
		return place.getObject();
	}
	
	/**
	 * 刷新，当place中的corps的speed发生变化时，立即刷新，该方法只用于rule
	 */
	public void refurbish() {
		IAction action = new ActionProxyHelper(getRefurbishAction());
		action.action();
		
		for(int i=0;i<this.queue.size();i++){
			Place<T> place = this.queue.get(i);
			place.setIndex(i);
		}
	}
	
	/**
	 * 调整机动值
	 * @param t
	 * @param count
	 */
	public void setPlaceCount(T t, Integer count) {
		Place<T> place = getPlace(t);
		place.setCount(count);
		
		refurbish();
	}
	
	protected IAction getRefurbishAction() {
		if(null==this.refurbishAction){
			this.refurbishAction = new RefurbishAction();
			this.refurbishAction.setOwner(this);
		}
		return this.refurbishAction;
	}
	
	/**
	 * 插入队列
	 * @param place
	 */
	protected void insert(Place<T> place) {
		// TODO Auto-generated method stub
		IAction action = new ActionProxyHelper(getInsertAction());
		action.action(place);
	}
	
	protected IAction getInsertAction() {
		if(null==this.insertAction){
			this.insertAction = new InsertAction();
			this.insertAction.setOwner(this);
		}
		return this.insertAction;
	}
	
	/**
	 * 从队列中取出
	 * @param place
	 */
	private void takeOut(Place<T> place){
		IAction action = new ActionProxyHelper(getTakeOutAction());
		action.action(place);
	}
	
	protected IAction getTakeOutAction() {
		if(null==this.takeOutAction){
			this.takeOutAction = new TakeOutAction();
			this.takeOutAction.setOwner(this);
		}
		return this.takeOutAction;
	}
	
	/**
	 * 根据对象反查place
	 * @param object
	 * @return
	 */
	private Place<T> getPlace(T t) {
		for(Place<T> place : this.queue){
			if(place.getObject().equals(t))
				return place;
		}
		return null;
	}
	
	/**
	 * 进入下一个回合，所有单位重新激活
	 */
	private void renew() {
		for(Place<T> place : this.queue){
			Integer count = place.getCount();
			place.setCount(count + place.getSpeed());
		}
	}
	
	private List<Map<String, Object>> getList(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		for(int i=0;i<this.queue.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			Place<T> place = this.queue.get(i);
			map.put("position",this.queue.indexOf(place));
			map.put("unit", place.getObject());
			map.put("count", place.getCount());
			list.add(map);
		}

		return list;
	}
	
	private class RefurbishAction extends AbstractAction implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			for(Place<T> place : queue){
				place.update();
			}
			Collections.sort(queue, new PlaceComparator());
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("queue", getList());
			NotifyInfo info = new NotifyInfo(CommonIdentifier.Context_ControlQueue_Refurbish,map);
			notifyObservers(info);
		}
		
	}
	
	private class InsertAction extends AbstractAction implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			Place<T> place = (Place<T>) objects[0];
			
			queue.add(place);
			Collections.sort(queue, new PlaceComparator());
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("unit", place.getObject());
			map.put("queue", getList());
			NotifyInfo info = new NotifyInfo(CommonIdentifier.Context_ControlQueue_Insert,map);
			notifyObservers(info);
		}
		
	}
	
	private class TakeOutAction extends AbstractAction implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			Place<T> place = (Place<T>) objects[0];
			
			queue.remove(place);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("unit", place.getObject());
			map.put("queue", getList());
			NotifyInfo info = new NotifyInfo(CommonIdentifier.Context_ControlQueue_Remove,map);
			notifyObservers(info);
		}
		
	}
	
	private class ContextAddBout extends AbstractAction implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			bout++;
		}
		
		public AbstractControlQueue<T> getOwner() {
			
			return (AbstractControlQueue<T>) super.getOwner();
		};
	}
	
	abstract class Place<T> {
		private T obj = null;
		private Integer count = 0;
		private Integer speed = 0;
		private Integer index = 0;
		
		private AbstractControlQueue<T> queue = null;
		
		public Place(T t) {
			// TODO Auto-generated constructor stub
			this.obj = t;
			update();
		}
		
		public Integer getCount(){
			return count;
		}
		
		public void setCount(Integer count){
			this.count = count;
		}
		
		public Integer getSpeed() {
			return speed;
		}
		
		public void setSpeed(Integer speed) {
			this.speed = speed;
		}
		
		public T getObject(){
			return obj;
		}
		
		void setIndex(Integer index){
			this.index = index;
		}
		
		Integer getIndex() {
			return this.index;
		}
		
		/**
		 * 刷新speed
		 */
		public void update() {
			loadSpeed(this.obj, this);
		}
		
		abstract void loadSpeed(T t, Place<T> place);
		
		@Override
		public boolean equals(Object arg0) {
			// TODO Auto-generated method stub
			if (arg0 instanceof Place) {
				Place<T> place = (Place<T>) arg0;
				return obj.equals(place.getObject());
			}else
				return false;
		}
	}
	
	class PlaceComparator implements Comparator<Place<T>> {

		@Override
		public int compare(Place<T> p1, Place<T> p2) {
			// TODO Auto-generated method stub
			if(p1.getCount()<p2.getCount())
				return 1;
			else if(p1.getCount().equals(p2.getCount())){
				if(p1.getIndex()<p2.getIndex())
					return 1;
				else if(p1.getIndex().equals(p2.getIndex()))
					return 0;
				else
					return -1;
			}else
				return -1;
		}
	}
}
