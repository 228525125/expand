package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.IAction;
import org.cx.game.action.IUpgrade;
import org.cx.game.action.UpgradeSkill;
import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.IActiveSkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.validator.Errors;
import org.cx.game.validator.IValidator;
import org.cx.game.validator.ParameterTypeValidator;
import org.cx.game.widget.IGround;

public abstract class ActiveSkill extends AbstractSkill implements IActiveSkill {

	private String code = "";
	private Integer cooldown = 1;             //冷却回合
	private Boolean allow = true;
	
	private ProcessActiveSkillCooling coolingProcess = null;      //间隔
	private IUpgrade upgrade = null;
	private Errors errors = new Errors();
	
	private List<IValidator> validatorList = new ArrayList<IValidator>();
	
	
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
	
	@Override
	public Boolean getAllow() {
		// TODO Auto-generated method stub
		return this.allow;
	}
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return getOwner().getAttack().getRange();
	}
	
	@Override
	public void setAllow(Boolean allow) {
		// TODO Auto-generated method stub
		this.allow = allow;
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
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("player", getOwner().getPlayer());
		map.put("container", getOwner().getGround());
		map.put("corps", getOwner());
		map.put("skill", this);
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(getAction()+UseSkill,map);
		notifyObservers(info);           //通知所有卡片对象，被动技能发动		
		
		cooling();
	}
	
	/**
	 * 技能有效范围
	 * @param ground
	 * @return
	 */
	public List<Integer> getConjureRange(){
		List<Integer> positionList = new ArrayList<Integer>();
		Corps corps = (Corps) getOwner();
		IGround ground = corps.getGround();
		Integer position = corps.getPosition();
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
		return null!=this.coolingProcess ? this.coolingProcess.getRemainBout() : 0;
	}
	
	private void cooling() {
		// TODO Auto-generated method stub		
		if(!Integer.valueOf(0).equals(this.cooldown)){
			setAllow(false);
			this.coolingProcess = new ProcessActiveSkillCooling(this.cooldown, this);
		}else
			setAllow(true);
	}

	public IUpgrade getUpgrade() {		
		if(null==upgrade){
			IUpgrade upgrade = new UpgradeSkill();
			upgrade.setOwner(this);
			this.upgrade = upgrade;
		}
		return upgrade;
	}

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
}
