package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.IAction;
import org.cx.game.action.Upgrade;
import org.cx.game.action.SkillUpgrade;
import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.IActiveSkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.validator.Errors;
import org.cx.game.validator.IValidator;
import org.cx.game.validator.ParameterTypeValidator;
import org.cx.game.widget.IGround;

public abstract class ActiveSkill extends AbstractSkill implements IActiveSkill {

	//private String code = "";
	private Integer cooldown = 1;             //冷却回合
	private Boolean allow = true;
	
	private ActiveSkillCoolingProcess coolingProcess = null;      //间隔
	private Upgrade upgrade = null;
	
	
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
	
	public void useSkill(Object...objects) {
		// TODO Auto-generated method stub
		
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
	
	/*@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}*/
	
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
			this.coolingProcess = new ActiveSkillCoolingProcess(this.cooldown, this);
		}else
			setAllow(true);
	}

	public Upgrade getUpgrade() {		
		if(null==upgrade){
			Upgrade upgrade = new SkillUpgrade();
			upgrade.setOwner(this);
			this.upgrade = upgrade;
		}
		return upgrade;
	}
	
	/*
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
	}*/
}
