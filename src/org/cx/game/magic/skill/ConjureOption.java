package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.IAction;
import org.cx.game.corps.Corps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.ConjureWaitBuff;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifier;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.I18n;
import org.cx.game.validator.CorpsConjureableValidator;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.Ground;
import org.cx.game.widget.AbstractOption;

public abstract class ConjureOption extends AbstractOption {
	
	private String name = null;
	
	public ConjureOption(ActiveSkill skill) {
		// TODO Auto-generated constructor stub
		setOwner(skill);
		setSpacingWait(skill.getCooldown());
		setExecuteWait(skill.getConjureWait());
	}
	
	@Override
	public void cancelExecuteWait() {
		// TODO Auto-generated method stub
		super.cancelExecuteWait();
		
		/*
		 * 即使执行被取消，间隔周期同样有效
		 */
		cooling();
	}
	
	@Override
	public void execute(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		Corps corps = (Corps) getOwner().getOwner();
		/*
		 * 使用技能必须保证Corps为可施法状态
		 */
		doValidator(new CorpsConjureableValidator(corps));
		
		super.execute(objects);
		
		/*
		 * 如果是执行等待，就给owner加上一个buff
		 */
		if(AbstractOption.Status_WaitExecute.equals(getStatus())){
			new ConjureWaitBuff(getExecuteWait(), this, getOwner().getOwner()).effect();
		}
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name){
			name = I18n.getMessage(ConjureOption.class, "name");
			name += getOwner().getName();
		}
		return name;
	}
	
	@Override
	public ActiveSkill getOwner() {
		// TODO Auto-generated method stub
		return (ActiveSkill) super.getOwner();
	}

	@Override
	public List<Integer> getExecuteRange() {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		ActiveSkill as = (ActiveSkill) getOwner();
		Corps corps = as.getOwner();
		Ground ground = corps.getGround();
		Integer position = corps.getPosition();
		positionList = ground.areaForDistance(position, as.getRange(), Ground.Contain);
		return positionList;
	}

	@Override
	protected AbstractControlQueue getControlQueue() {
		// TODO Auto-generated method stub
		return getOwner().getOwner().getGround().getQueue();
	}
	
	private BeforeExecute beforeExecute = null;
	
	@Override
	public BeforeExecute getBeforeExecute() {
		// TODO Auto-generated method stub
		if(null==this.beforeExecute){
			this.beforeExecute = new ConjureOptionBeforeExecute();
			this.beforeExecute.setOwner(this);
		}
		return this.beforeExecute;
	}
	
	class ConjureOptionBeforeExecute extends BeforeExecute implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			Corps corps = (Corps) getOwner().getOwner().getOwner();
			corps.getConjure().setConjureable(false);
			corps.getAttack().setAttackable(false);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("corps", corps);
			map.put("option", getOwner().getOwner());
			map.put("position", corps.getPosition());
			String desc = getOwner().getName()+" 【使用技能】 "+getOwner().getOwner().getName();
			map.put("description", desc);
			NotifyInfo info = new NotifyInfo(CommonIdentifier.Option_Executed,map);
			notifyObservers(info);
		}
		
		@Override
		public ConjureOption getOwner() {
			// TODO Auto-generated method stub
			return (ConjureOption) super.getOwner();
		}
	}

}
