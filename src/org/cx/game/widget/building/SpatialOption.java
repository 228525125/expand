package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IAction;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.validator.CorpsInThePlaceValidator;
import org.cx.game.validator.CorpsMoveEnergyValidator;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.AbstractPlace;
import org.cx.game.widget.AbstractGround;

/**
 * 传送选项
 * @author chenxian
 * 
 */
public class SpatialOption extends AbstractOption {

	private String name = null;
	
	private SpatialBuilding spatialBuilding = null;
	
	public SpatialOption(SpatialBuilding dest, SpatialBuilding owner) {
		// TODO Auto-generated constructor stub
		this.spatialBuilding = dest;
		setOwner(owner);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name){
			name = super.getName();
			name += this.spatialBuilding.getPlace().getPosition();
		}
		return name;
	}
	
	@Override
	public SpatialBuilding getOwner() {
		// TODO Auto-generated method stub
		return (SpatialBuilding) super.getOwner();
	}
	
	@Override
	protected AbstractControlQueue getControlQueue() {
		// TODO Auto-generated method stub
		return getOwner().getPlace().getOwner().getQueue();
	}
	
	@Override
	public List<Integer> getExecuteRange() {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		return list;
	}
	
	private Execute execute = null;

	@Override
	public Execute getExecute() {
		// TODO Auto-generated method stub
		if(null==this.execute){
			Execute execute = new SpatialOptionExecute();
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}
	
	@Override
	public void execute(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		AbstractPlace place = getOwner().getPlace();
		doValidator(new CorpsInThePlaceValidator(place.getOwner(), place.getPosition()));
		doValidator(new CorpsMoveEnergyValidator((Corps) place.getCorps()));
		
		super.execute(objects);
	}
	
	class SpatialOptionExecute extends Execute implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			SpatialBuilding sb = (SpatialBuilding) getOwner().getOwner();
			sb.transmit(spatialBuilding);
		}
	}
}
