package org.cx.game.magic.skill;

import java.util.Collections;
import java.util.List;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.DizzyBuff;
import org.cx.game.tools.IListFilter;
import org.cx.game.tools.ListUtils;
import org.cx.game.validator.CorpsMoveableValidator;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Ground;
import org.cx.game.widget.Place;
import org.cx.game.widget.SceneControlQueue;

/**
 * 俯冲，一般为飞行近战单位的标准技能
 * @author chenxian
 *
 */
public class Dive extends ActiveSkill {

	public final static Integer Dive_ID = 10200010;

	private Integer atkScale = 0;
	private Integer dizzyBout = 0;
	
	private AbstractOption diveOption = null;
	
	public Dive(Integer id, String range, Integer waitBout, Integer atkScale, Integer dizzyBout) {
		super(id);
		// TODO Auto-generated constructor stub
		setRange(range);
		setConjureWait(waitBout);
		this.atkScale = atkScale;
		this.dizzyBout = dizzyBout;
	}
	
	@Override
	public void afterConstruct() {
		// TODO Auto-generated method stub
		this.diveOption = new DiveOption(this);
		addOption(this.diveOption);
	}
	
	@Override
	public void setOwner(AbstractCorps corps) {
		// TODO Auto-generated method stub
		super.setOwner(corps);
		
		/*
		 * 使用技能必须保证Corps可移动
		 */
		this.diveOption.addValidator(new CorpsMoveableValidator((Corps) corps));
	}
	
	@Override
	public void setCooldown(Integer cooldown) {
		// TODO Auto-generated method stub
		super.setCooldown(cooldown);
	}
	
	@Override
	public void useSkill(Object... objects) {
		// TODO Auto-generated method stub
		Place place = (Place) objects[0];
		Corps owner = (Corps) getOwner();
		
		Ground ground = (Ground) owner.getGround();
		
		List<AbstractCorps> corpsList = ground.queryCorpsList(place.getPosition(), 1, Ground.Contain);
		if(!corpsList.isEmpty()){
			Collections.shuffle(corpsList);
			Corps corps = (Corps) corpsList.get(0);
			corps.affected(this);
		}else{
			ground.placementCorps(place.getPosition(), owner);
		}
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		
		Corps corps = (Corps) objects[0];
		Corps owner = (Corps) getOwner();
		
		final Ground ground = (Ground) owner.getGround();
		
		List<Integer> posList = ground.areaForDistance(corps.getPosition(), 2, Ground.Contain);
		posList = ListUtils.filter(posList, new IListFilter<Integer>(){

			@Override
			public Boolean content(Integer t) {
				// TODO Auto-generated method stub
				return ground.getPlace(t).isEmpty();
			}
		});
		
		Collections.shuffle(posList);
		Integer pos = posList.get(0);
		ground.placementCorps(pos, owner);
		owner.setPosition(pos);
			
		SceneControlQueue queue = (SceneControlQueue) ground.getQueue();
		queue.add(owner);
		
		/*
		 * 俯冲后，需要休息
		 */
		queue.setPlaceCount(owner, 0);
		
		Integer atk = owner.getAttack().getAtk();
		Integer harm = atk * this.atkScale / 100;
		
		if(harmToCorps(harm, corps))
			new DizzyBuff(this.dizzyBout, corps).effect();
		
		super.affect(objects);
	}
	
	public class DiveOption extends ConjureToPlaceOption {

		public DiveOption(ActiveSkill skill) {
			super(skill);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void execute(Object... objects) throws RuleValidatorException {
			// TODO Auto-generated method stub
			super.execute(objects);
			
			AbstractCorps corps =  getOwner().getOwner();
			
			Ground ground = (Ground) corps.getGround();
			ground.removeCorps(corps);
			ground.getQueue().remove(corps);
		}
	}

}
