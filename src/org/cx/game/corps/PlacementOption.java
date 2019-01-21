package org.cx.game.corps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.IAction;
import org.cx.game.core.SceneHost;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.I18n;
import org.cx.game.validator.CorpsPlacementRangeValidator;
import org.cx.game.validator.OperatePowerValidator;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.AbstractPlace;
import org.cx.game.widget.Ground;
import org.cx.game.widget.HoneycombGround;
import org.cx.game.widget.Place;

/**
 * 放置
 * @author chenxian
 *
 */
public class PlacementOption extends AbstractOption {
	
	public PlacementOption(AbstractCorps corps) {
		// TODO Auto-generated constructor stub
		setOwner(corps);
	}
	
	@Override
	public AbstractCorps getOwner() {
		// TODO Auto-generated method stub
		return (AbstractCorps) super.getOwner();
	}
	
	@Override
	public List<Integer> getExecuteRange() {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		Ground ground = (Ground) getOwner().getGround();
		positionList = ground.getEntranceList(getOwner().getTroop());
		positionList.retainAll(ground.queryPositionList(true));
		return positionList;
	}

	@Override
	protected AbstractControlQueue getControlQueue() {
		// TODO Auto-generated method stub
		return getOwner().getGround().getQueue();
	}
	
	private Execute execute = null;

	public Execute getExecute() {
		if(null==this.execute){
			Execute execute = new OptionPlacementExecute();
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}
	
	@Override
	public void execute(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		Place place = (Place) objects[0];
		
		doValidator(new CorpsPlacementRangeValidator(getOwner(), place.getPosition()));
		super.execute(place.getPosition(), getOwner());
	}
	
	class OptionPlacementExecute extends Execute implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			Integer position = (Integer) objects[0];
			Corps corps = (Corps) objects[1];
			
			corps.getGround().removeCorps(corps);
			corps.getGround().placementCorps(position, corps);
			corps.setPosition(position);
			
			Map<String,Object> map = new HashMap<String,Object>();
			Ground ground = (HoneycombGround) getOwner().getOwner().getGround();

			map.put("ground", ground);
			NotifyInfo info = new NotifyInfo(CommonIdentifierE.Ground_LoadMap,map);
			super.notifyObservers(info);
		}
		
		@Override
		public PlacementOption getOwner() {
			// TODO Auto-generated method stub
			return (PlacementOption) super.getOwner();
		}
	}

}
