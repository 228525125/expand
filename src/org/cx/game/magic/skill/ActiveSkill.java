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
import org.cx.game.widget.AbstractOption;

public abstract class ActiveSkill extends AbstractSkill {

	private Integer cooldown = 1;             //冷却回合
	private Integer prepare = 0;              //准备（蓄力）回合
	private Integer range = 0;
	private String target = null;
	
	private Upgrade upgrade = null;
	
	/**
	 * 
	 * @param id 
	 * @param cooldown 冷却回合 
	 * @param prepare 准备回合
	 */
	public ActiveSkill(Integer type, Integer cooldown, Integer prepare) {
		// TODO Auto-generated constructor stub
		super(type);
		this.cooldown = cooldown;
		this.prepare = prepare;
	}
	
	@Override
	public void afterConstruct() {
		// TODO Auto-generated method stub
		AbstractOption option = new ConjureOption(this);
		addOption(option);
	}
	
	public String getTarget() {
		return this.target;
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
	 * 技能准备（蓄力）回合数
	 * @return
	 */
	public Integer getPrepare() {
		return prepare;
	}

	public void setPrepare(Integer prepare) {
		this.prepare = prepare;
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
	public abstract void useSkill(Object...objects); /*{
		// TODO Auto-generated method stub
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("player", getOwner().getPlayer());
		map.put("container", getOwner().getGround());
		map.put("corps", getOwner());
		map.put("skill", this);
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(getAction()+UseSkill,map);
		notifyObservers(info);           //通知所有卡片对象，被动技能发动		
	}*/
}
