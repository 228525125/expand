package org.cx.game.widget;

import org.cx.game.action.AbstractAction;
import org.cx.game.action.IAction;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;

public class Place extends AbstractPlace {

	
	public Place(IGround ground, Integer position) {
		super(ground, position);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Corps getCorps() {
		// TODO Auto-generated method stub
		return (Corps) super.getCorps();
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
		public void action(Object... objects) throws RuleValidatorException {
			// TODO Auto-generated method stub
			Corps corps = (Corps) objects[0];
			
			corps.setPosition(getPosition());
			setCorps(corps);
			
			setEmpty(false);
			getOwner().getOwner().getEmptyList().remove(getPosition());
			
			/*
			 * 生成地形优势
			 */
			Integer profession = corps.queryTagForCategory(AbstractCorps.Profession).get(0);
			corps.getAttack().setLandformAtk(corps.getAtk()*LandformEffect.getAttackAdvantage(profession, getLandform())/100);
			corps.getAttacked().setLandformDef(corps.getDef()*LandformEffect.getDefendAdvantage(profession, getLandform())/100);
			
			/*
			 * 添加路径
			 */
			corps.getMove().addMovePath(getPosition());
		}
		
		@Override
		public Place getOwner() {
			// TODO Auto-generated method stub
			return (Place) super.getOwner();
		}
	}
}
