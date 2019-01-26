package org.cx.game.widget;

import org.cx.game.action.AbstractAction;
import org.cx.game.action.IAction;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;

public class Place extends AbstractPlace {
	
	public Place(AbstractGround ground, Integer position) {
		super(ground, position);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Corps getCorps() {
		// TODO Auto-generated method stub
		for(AbstractCorps corps : getCorpsList()){
			Corps c = (Corps) corps;
			if(!c.getMove().getHide())
				return c;
		}
		return null;
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
