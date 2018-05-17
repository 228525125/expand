package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.cx.game.action.Execute;
import org.cx.game.action.IAction;
import org.cx.game.corps.Hero;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.I18n;
import org.cx.game.validator.CallConsumeValidator;
import org.cx.game.validator.CallRangeValidator;
import org.cx.game.widget.AbstractPlace;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IGroundE;
import org.cx.game.widget.Place;

/**
 * 英雄复活
 * @author chenxian
 *
 */
public class ReviveOption extends AbstractOption implements IOption {

	private String name = null;
	private Hero hero = null;

	public ReviveOption(Hero hero) {
		// TODO Auto-generated constructor stub
		this.hero = hero;
		this.hero.getDeath().addObserver(new OptionObserver());
		
		setParameterTypeValidator(new Class[]{AbstractPlace.class}, new String[]{"empty"}, new Object[]{true});
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name){
			name = super.getName();
			name += I18n.getMessage(Hero.class, hero.getType(), "name");
		}
		return name;
	}
	
	@Override
	public List<Integer> getExecuteRange(IGround ground) {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		positionList = ground.areaForDistance(getOwner().getPosition(), 1, IGround.Contain);
		positionList.retainAll(((IGroundE)ground).getEmptyList());
		return positionList;
	}
	
	private Execute execute = null;

	public Execute getExecute() {
		if(null==this.execute){
			Execute execute = new OptionReviveExecute(this.hero);
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}
	
	@Override
	public void execute(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		Place place = (Place) objects[0];
		
		getExecute().addValidator(new CallConsumeValidator(this.hero, 1));
		getExecute().addValidator(new CallRangeValidator(getOwner(), place));
		
		super.execute(objects);
	}
	
	class OptionObserver implements Observer {

		@Override
		public void update(Observable o, Object arg) {
			// TODO Auto-generated method stub
			
			if (arg instanceof NotifyInfo) {
				NotifyInfo info = (NotifyInfo) arg;
				
				if(NotifyInfo.Corps_Death.equals(info.getType())){
					Map bean = (Map) info.getInfo();
					Hero hero = (Hero) bean.get("card");
					
					setSpacingWait(hero.getUpgrade().getLevel());
					cooling();
				}
			}
			
		}	
	}
	
	public class OptionReviveExecute extends Execute implements IAction {
		
		private Hero hero = null;

		public OptionReviveExecute(Hero hero) {
			// TODO Auto-generated constructor stub
			this.hero = hero;
		}
		
		@Override
		public void action(Object... objects) throws RuleValidatorException {
			// TODO Auto-generated method stub
			setSpacingWait(10000);              //英雄复活后，该选项被锁
			
			super.action(objects);
			
			AbstractPlace place = (Place) objects[0];
			this.hero.call(place,1);
		}
	}
}