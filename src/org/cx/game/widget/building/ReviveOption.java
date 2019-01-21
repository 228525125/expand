package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.cx.game.action.IAction;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.Hero;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.I18n;
import org.cx.game.validator.CorpsCallConsumeValidator;
import org.cx.game.validator.CorpsCallRangeValidator;
import org.cx.game.validator.IValidator;
import org.cx.game.validator.ParameterTypeValidator;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.AbstractPlace;
import org.cx.game.widget.Ground;
import org.cx.game.widget.Place;
import org.cx.game.widget.Scene;

/**
 * 英雄复活
 * @author chenxian
 *
 */
public class ReviveOption extends AbstractOption {

	private String name = null;
	private Hero hero = null;

	public ReviveOption(Hero hero) {
		// TODO Auto-generated constructor stub
		this.hero = hero;
		this.hero.getDeath().addObserver(new OptionObserver());
		
		addValidator(new CorpsCallConsumeValidator(this.hero, 1));
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
	public AbstractBuilding getOwner() {
		// TODO Auto-generated method stub
		return (AbstractBuilding) super.getOwner();
	}
	
	@Override
	protected AbstractControlQueue getControlQueue() {
		// TODO Auto-generated method stub
		return getOwner().getPlace().getOwner().getQueue();
	}
	
	@Override
	public List<Integer> getExecuteRange() {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		Ground ground = (Ground) getOwner().getPlace().getOwner();
		positionList = ground.areaForDistance(getOwner().getPlace().getPosition(), 1, AbstractGround.Contain);
		positionList.retainAll(ground.queryPositionList(true));
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
		/*
		 * 验证参数
		 */
		IValidator validator = new ParameterTypeValidator(objects,new Class[]{AbstractPlace.class}, new String[]{"empty"}, new Object[]{true});
		doValidator(validator);
		
		Place place = (Place) objects[0];
		
		doValidator(new CorpsCallRangeValidator((AbstractBuilding) getOwner(), place));
		
		super.execute(objects);
	}
	
	class OptionObserver implements Observer {

		@Override
		public void update(Observable o, Object arg) {
			// TODO Auto-generated method stub
			
			if (arg instanceof NotifyInfo) {
				NotifyInfo info = (NotifyInfo) arg;
				
				if(CommonIdentifierE.Corps_Death.equals(info.getType())){
					Map bean = (Map) info.getInfo();
					Hero hero = (Hero) bean.get("card");
					
					setSpacingWait(hero.getUpgrade().getLevel());
					cooling();
				}
			}
			
		}	
	}
	
	class OptionReviveExecute extends Execute implements IAction {
		
		private Hero hero = null;

		public OptionReviveExecute(Hero hero) {
			// TODO Auto-generated constructor stub
			this.hero = hero;
		}
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			setSpacingWait(10000);              //英雄复活后，该选项被锁
			
			super.action(objects);
			
			AbstractPlace place = (Place) objects[0];
			Scene scene = (Scene) place.getOwner();
			scene.outCemetery(hero);
			this.hero.call(place,1);
		}
	}
}
