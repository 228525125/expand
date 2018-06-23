package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.Execute;
import org.cx.game.action.IAction;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.CorpsFactory;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IGroundE;
import org.cx.game.widget.Place;
import org.cx.game.widget.building.BuildOption.OptionBuildExecute;

/**
 * 传送选项
 * @author chenxian
 *
 */
public class SpatialOption extends AbstractOption implements IOption {

	private SpatialBuilding spatialBuilding = null;
	
	public SpatialOption(SpatialBuilding spatialBuilding) {
		// TODO Auto-generated constructor stub
		this.spatialBuilding = spatialBuilding;
	}
	
	@Override
	public List<Integer> getExecuteRange(IGround ground) {
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
	
	public class SpatialOptionExecute extends Execute implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			SpatialBuilding sb = (SpatialBuilding) getOwner().getOwner();
			sb.transmit(spatialBuilding);
		}
	}

}
