package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.cx.game.action.Execute;
import org.cx.game.action.IExecute;
import org.cx.game.corps.CorpsFactory;
import org.cx.game.corps.Corps;
import org.cx.game.corps.Hero;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.I18n;
import org.cx.game.validator.CallConsumeValidator;
import org.cx.game.validator.CallRangeValidator;
import org.cx.game.widget.IGround;
import org.cx.game.widget.Place;

/**
 * 英雄复活
 * @author chenxian
 *
 */
public class OptionRevive extends Option implements IOption {

	private String name = null;
	private Corps hero = null;

	public OptionRevive(Corps hero) {
		// TODO Auto-generated constructor stub
		this.hero = hero;
		this.hero.getDeath().addObserver(new OptionObserver());
		
		setParameterTypeValidator(new Class[]{Place.class}, new String[]{"empty"}, new Object[]{true});
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
		positionList.retainAll(ground.getEmptyList());
		return positionList;
	}
	
	private IExecute execute = null;

	public IExecute getExecute() {
		if(null==this.execute){
			IExecute execute = new OptionReviveExecute(this.hero);
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
					Corps hero = (Corps) bean.get("card");
					
					setSpacingWait(hero.getUpgrade().getLevel());
					cooling();
				}
			}
			
		}	
	}
	
	public class OptionReviveExecute extends Execute implements IExecute {
		
		private Corps hero = null;

		public OptionReviveExecute(Corps hero) {
			// TODO Auto-generated constructor stub
			this.hero = hero;
		}
		
		@Override
		public void action(Object... objects) throws RuleValidatorException {
			// TODO Auto-generated method stub
			setSpacingWait(10000);              //英雄复活后，该选项被锁
			
			super.action(objects);
			
			Place place = (Place) objects[0];
			this.hero.call(place,1);
		}
	}
}
