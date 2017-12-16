package org.cx.game.card.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.cx.game.action.IUpgrade;
import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;
import org.cx.game.core.Context;
import org.cx.game.core.IPlayer;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.out.JsonOut;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Errors;
import org.cx.game.validator.IValidator;
import org.cx.game.validator.ParameterTypeValidator;
import org.cx.game.widget.IGround;

public abstract class ActiveSkill extends Skill implements IActiveSkill {

	private String code = "";
	private Integer cooldown = 1;             //冷却回合
	private Integer cooldownRemain = 0;         //冷却剩余回合数
	
	/**
	 * 
	 * @param id 
	 * @param cooldown 冷却回合 
	 */
	public ActiveSkill(Integer id, Integer cooldown) {
		// TODO Auto-generated constructor stub
		super(id);
		this.cooldown = cooldown;
	}
	
	private ParameterTypeValidator parameterTypeValidator = null;
	private Class[] parameterType = new Class[]{};      //用于参数的验证
	private String[] proertyName = null;
	private Object[] validatorValue = null;
	
	protected void setParameterTypeValidator(Class[] parameterType) {
		this.parameterType = parameterType;
	}
	
	protected void setParameterTypeValidator(Class[] parameterType, String[] proertyName, Object[] validatorValue) {
		this.parameterType = parameterType;
		this.proertyName = proertyName;
		this.validatorValue = validatorValue;
	}
	
	protected void parameterTypeValidator(Object...objects) throws RuleValidatorException {
		deleteValidator(parameterTypeValidator);
		this.parameterTypeValidator = new ParameterTypeValidator(objects,parameterType,proertyName,validatorValue); 
		addValidator(parameterTypeValidator);
		
		doValidator();
		
		if(hasError())
			throw new RuleValidatorException(getErrors().getMessage());
	}
	
	public void useSkill(Object...objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		
		parameterTypeValidator(objects);
		
		/* 
		 * 执行规则验证
		 */
		doValidator();
		
		if(hasError())
			throw new RuleValidatorException(getErrors().getMessage());
		
		cooldownRemain = cooldown;
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("player", getOwner().getPlayer());
		map.put("container", getOwner().getContainer());
		map.put("card", getOwner());
		map.put("skill", this);
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(getAction()+UseSkill,map);
		notifyObservers(info);           //通知所有卡片对象，被动技能发动		
	}
	
	/**
	 * 技能有效范围
	 * @param ground
	 * @return
	 */
	public List<Integer> getConjureRange(IGround ground){
		List<Integer> positionList = new ArrayList<Integer>();
		LifeCard card = (LifeCard) getOwner();
		Integer position = card.getPosition();
		positionList = ground.areaForDistance(position, getRange(), IGround.Contain);
		return positionList;
	}
	
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}
	
	public Integer getCooldown() {
		return cooldown;
	}

	public void setCooldown(Integer cooldown) {
		this.cooldown = cooldown;
	}
	
	public Integer getCooldownRemain() {
		return cooldownRemain;
	}

	public void setCooldownRemain(Integer cooldownRemain) {
		this.cooldownRemain = cooldownRemain;
	}
	
	private List<IValidator> validatorList = new ArrayList<IValidator>();
	private Errors errors = new Errors();

	@Override
	public void addValidator(IValidator validator) {
		// TODO Auto-generated method stub
		validatorList.add(validator);
	}

	@Override
	public void deleteValidator(IValidator validator) {
		// TODO Auto-generated method stub
		validatorList.remove(validator);
	}

	@Override
	public List<IValidator> getValidators() {
		// TODO Auto-generated method stub
		return validatorList;
	}
	
	@Override
	public void doValidator() {
		// TODO Auto-generated method stub
		for(IValidator v : validatorList)
			if(!v.validate())
				errors.addError(v);
	}
	
	@Override
	public void doValidator(IValidator validator) {
		// TODO Auto-generated method stub
		if(!validator.validate())
			errors.addError(validator);
	}
	
	@Override
	public Errors getErrors() {
		// TODO Auto-generated method stub
		return errors;
	}
	
	@Override
	public Boolean hasError() {
		// TODO Auto-generated method stub
		return errors.hasError();
	}
}
