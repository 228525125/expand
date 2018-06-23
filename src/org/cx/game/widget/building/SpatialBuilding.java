package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.AbstractAction;
import org.cx.game.action.ActionProxyHelper;
import org.cx.game.action.IAction;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.AbstractPlace;
import org.cx.game.widget.IGround;

/**
 * 传送站，用于链接不同地图空间的节点，但区别于时空门；
 * @author chenxian
 *
 */
public class SpatialBuilding extends AbstractBuilding {
	
	private Boolean transmissionIsUsable = false;
	private Boolean receiveIsUsable = true;
	
	private List<Integer> spatialBuildingTypeList = new ArrayList<Integer>();
	private List<SpatialBuilding> spatialBuildingList = new ArrayList<SpatialBuilding>();
	
	public SpatialBuilding(Integer buildingType) {
		super(buildingType);
		// TODO Auto-generated constructor stub
		setStatus(IBuilding.Building_Status_Complete);
	}
	
	/**
	 * 传送到目标节点
	 * @param destNode 节点
	 */
	public void transmit(SpatialBuilding destNode) {
		AbstractCorps corps = getPlace().getCorps();
		IAction action = new ActionProxyHelper(getTransmitAction());
		action.action(destNode, corps);
	}
	
	/**
	 * 接收一位传送来的英雄，当然只有英雄才可使用传送点
	 * @param hero 英雄
	 */
	public void receive(AbstractCorps corps) {
		AbstractPlace place = getPlace();
		IAction action = new ActionProxyHelper(getReceiveAction());
		action.action(place, corps);
	}
	
	@Override
	public void setPlace(AbstractPlace place) {
		// TODO Auto-generated method stub
		super.setPlace(place);
		
	}
	
	public Boolean getTransmissionIsUsable() {
		return this.transmissionIsUsable;
	}
	
	public void setTransmissionIsUsable(Boolean transmissionIsUsable) {
		this.transmissionIsUsable = transmissionIsUsable;
	}
	
	public Boolean getReceiveIsUsable() {
		return this.receiveIsUsable;
	}
	
	public void setReceiveIsUsable(Boolean receiveIsUsable) {
		this.receiveIsUsable = receiveIsUsable;
	}
	
	public List<Integer> getSpatialBuildingTypeList() {
		return spatialBuildingTypeList;
	}

	public void setSpatialBuildingTypeList(List<Integer> spatialBuildingTypeList) {
		this.spatialBuildingTypeList = spatialBuildingTypeList;
	}
	
	public List<SpatialBuilding> getSpatialBuildingList() {
		return spatialBuildingList;
	}

	private IAction transmitAction = null;
	
	private IAction getTransmitAction() {
		if(null==this.transmitAction){
			this.transmitAction = new TransmitAction();
			this.transmitAction.setOwner(this);
		}
		return this.transmitAction;
	}
	
	private IAction receiveAction = null;
	
	private IAction getReceiveAction() {
		if(null==this.receiveAction){
			this.receiveAction = new ReceiveAction();
			this.receiveAction.setOwner(this);
		}
		return this.receiveAction;
	}
	
	class TransmitAction extends AbstractAction implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			SpatialBuilding sb = (SpatialBuilding) objects[0];
			AbstractCorps corps = (AbstractCorps) objects[1];
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("corps", corps);
			map.put("position", SpatialBuilding.this.getPlace().getPosition());
			NotifyInfo info = new NotifyInfo(CommonIdentifierE.Building_Transmit,map);
			super.notifyObservers(info);
			
			sb.receive(corps);
		}
	}
	
	class ReceiveAction extends AbstractAction implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			AbstractPlace place = (AbstractPlace) objects[0];
			AbstractCorps corps = (AbstractCorps) objects[1];
			IGround ground = place.getOwner();
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("corps", corps);
			map.put("mapId", ground.getId());
			map.put("position", place.getPosition());
			NotifyInfo info = new NotifyInfo(CommonIdentifierE.Building_Receive,map);
			super.notifyObservers(info);
			
			
			ground.placementCorps(place.getPosition(), corps);
		}
	}

}
