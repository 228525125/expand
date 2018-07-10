package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.IAction;
import org.cx.game.action.Upgrade;
import org.cx.game.action.SkillUpgrade;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.validator.Errors;
import org.cx.game.validator.IValidator;
import org.cx.game.validator.ParameterTypeValidator;
import org.cx.game.widget.AbstractGround;

public abstract class ActiveSkill extends AbstractSkill {

	private Integer cooldown = 1;             //冷却回合
	private Boolean allow = true;
	private Integer range = 0;
	
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
	
	/**
	 * 是否可以执行
	 * @return
	 */
	public Boolean getAllow() {
		// TODO Auto-generated method stub
		return this.allow;
	}
	
	public void setAllow(Boolean allow) {
		// TODO Auto-generated method stub
		this.allow = allow;
	}
	
	/**
	 * 技能使用范围
	 * @return
	 */
	public Integer getRange() {
		return this.range;
	}
	
	public void setRange(Integer range) {
		this.range = range;
	}
	
	/**
	 * 技能有效范围
	 * @param ground
	 * @return
	 */
	public List<Integer> getConjureRange(){
		List<Integer> positionList = new ArrayList<Integer>();
		Corps corps = (Corps) getOwner();
		AbstractGround ground = corps.getGround();
		Integer position = corps.getPosition();
		positionList = ground.areaForDistance(position, getRange(), AbstractGround.Contain);
		return positionList;
	}
	
	@Override
	public void setOwner(AbstractCorps corps) {
		// TODO Auto-generated method stub
		super.setOwner(corps);
		
		this.range = ((Corps) corps).getAttack().getRange();
	}
	
	/**
	 * 技能冷却周期
	 * @return
	 */
	public Integer getCooldown() {
		return cooldown;
	}

	public void setCooldown(Integer cooldown) {
		this.cooldown = cooldown;
	}
	
	/**
	 * 剩余冷却回合
	 * @return
	 */
	public Integer getCooldownRemain() {
		return null!=this.coolingProcess ? this.coolingProcess.getRemainBout() : 0;
	}
	
	public Upgrade getUpgrade() {		
		if(null==upgrade){
			Upgrade upgrade = new SkillUpgrade();
			upgrade.setOwner(this);
			this.upgrade = upgrade;
		}
		return upgrade;
	}
	
	/**
	 * 使用技能
	 * @param objects
	 */
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
	
	private void cooling() {
		// TODO Auto-generated method stub		
		if(!Integer.valueOf(0).equals(this.cooldown)){
			setAllow(false);
			this.coolingProcess = new ActiveSkillCoolingProcess(this.cooldown, this);
		}else
			setAllow(true);
	}
}
