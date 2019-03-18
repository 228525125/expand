package org.cx.game.widget;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.AbstractAction;
import org.cx.game.action.ActionProxyHelper;
import org.cx.game.action.IAction;
import org.cx.game.corps.Corps;
import org.cx.game.corps.Corps;
import org.cx.game.magic.trick.ITrick;
import org.cx.game.widget.building.AbstractBuilding;
import org.cx.game.widget.treasure.Treasure;

public class Place implements IPlace {
	
	/**
	 * 草地
	 */
	public static final Integer Landform_Sward = 401;
	
	/**
	 * 丘林
	 */
	public static final Integer Landform_Massif = 402;
	
	/**
	 * 森林
	 */
	public static final Integer Landform_Forest = 403;
	
	/**
	 * 河
	 */
	public static final Integer Landform_River = 404;
	
	/**
	 * 山
	 */
	public static final Integer Landform_Hill = 405;
	
	/**
	 * 沼泽
	 */
	public static final Integer Landform_marsh = 406;
	
	private TrickList trickList = new TrickList(this);
	private Cemetery cemetery = new Cemetery(this);
	private Integer position = 0;
	private Ground ground = null;
	private List<Corps> corpsList = new ArrayList<Corps>();
	private AbstractBuilding building  = null;
	private Integer landform = Landform_Sward;
	private Treasure treasure = null;
	
	public Place(Ground ground, Integer position) {
		this.ground = ground;
		this.position = position;
	}
	
	/**
	 * 非隐藏Corps
	 * @return
	 */
	public Corps getCorps() {
		// TODO Auto-generated method stub
		for(Corps corps : getCorpsList()){
			Corps c = (Corps) corps;
			if(!c.getMove().getHide())
				return c;
		}
		return null;
	}
	
	public List<Corps> getCorpsList() {
		List<Corps> retList = new ArrayList<Corps>();
		retList.addAll(this.corpsList);
		return retList;
	}
	
	public Boolean contains(Corps corps) {
		return this.corpsList.contains(corps);
	}
	
	protected void addCorps(Corps corps) {
		this.corpsList.add(corps);
	}
	
	/**
	 * 坐标系位置
	 * @return
	 */
	public Integer getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	public TrickList getTrickList() {
		// TODO Auto-generated method stub
		return trickList;
	}
	
	public Cemetery getCemetery() {
		// TODO Auto-generated method stub
		return cemetery;
	}
	
	public Ground getOwner(){
		return ground;
	}
	
	void in(Corps corps) {
		// TODO Auto-generated method stub
		IAction action = new ActionProxyHelper(getPlaceInAction());
		action.action(corps);
	}
	
	void inCemetery(Corps corps) {
		// TODO Auto-generated method stub
		this.cemetery.add(corps);
	}
	
	void inTrickList(ITrick trick) {
		// TODO Auto-generated method stub
		this.trickList.add(trick);
	}
	
	Boolean out(Corps corps) {
		// TODO Auto-generated method stub
		Boolean ret = this.corpsList.remove(corps);
		
		return ret;
	}
	
	void outCemetery(Corps corps) {
		// TODO Auto-generated method stub
		this.cemetery.remove(corps);
	}
	
	void outTrickList(ITrick trick) {
		// TODO Auto-generated method stub
		this.trickList.remove(trick);
	}
	
	/**
	 * 位置上是否为空
	 * @return
	 */
	public Boolean isEmpty() {
		// TODO Auto-generated method stub
		return null==getCorps();
	}
	
	public Integer getLandform() {
		// TODO Auto-generated method stub
		return this.landform;
	}
	
	void setLandform(Integer langform) {
		// TODO Auto-generated method stub
		this.landform = langform;
	}

	/**
	 * 建筑物
	 * @return 
	 */
	public AbstractBuilding getBuilding() {
		// TODO Auto-generated method stub
		return this.building;
	}

	void setBuilding(AbstractBuilding building) {
		// TODO Auto-generated method stub
		this.building = building;
		building.setPlace(this);
	}
	
	public Treasure getTreasure() {
		// TODO Auto-generated method stub
		return this.treasure;
	}
	
	/**
	 * 放置物品
	 */
	void setTreasure(Treasure treasure) {
		// TODO Auto-generated method stub
		if(null!=treasure)
			treasure.setPosition(position);
		
		this.treasure = treasure;
	}
	
	private IAction placeInAction = null;
	
	public IAction getPlaceInAction(){
		if(null==this.placeInAction){
			this.placeInAction = new PlaceInAction();
			this.placeInAction.setOwner(this);
		}
		return this.placeInAction;
	}

	public class PlaceInAction extends AbstractAction implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			Corps corps = (Corps) objects[0];
			addCorps(corps);
			
			/*
			 * corps移动、招募、放置都会触发这个消息，可能引起混淆
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("corps", corps);
			map.put("place", getOwner());
			NotifyInfo info = new NotifyInfo(NotifyInfo.Place_In,map);
			super.notifyObservers(info);
			 */
		}
		
		@Override
		public Place getOwner() {
			// TODO Auto-generated method stub
			return (Place) super.getOwner();
		}
	}
}
