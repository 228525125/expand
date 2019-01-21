package org.cx.game.magic.skill;

import org.cx.game.action.Upgrade;
import org.cx.game.action.SkillUpgrade;
import org.cx.game.corps.Corps;
import org.cx.game.tools.Util;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Place;

public abstract class ActiveSkill extends AbstractSkill {
	
	public static final Integer UseItOnFrend = 1;
	public static final Integer UseItOnFoe = 2;
	public static final Integer UseItOnAll = 3;

	private Integer cooldown = 1;             //冷却回合
	private Integer prepare = 0;              //准备（蓄力）回合
	private Boolean isReserve = false;         //是否允许蓄力
	private Integer conjureWait = 0;          //施法等待
	
	private String range = "1-1";             //range不一定总是从1开始计算，例如3-8；因此用一个字符串来表示
	private String target = null;
	private Integer useItOnFriendOrFoeOrAll = UseItOnFoe;
	private Boolean useItOnYouself = false;
	
	private Upgrade upgrade = null;
	
	/**
	 * 
	 * @param type 唯一标识 
	 */
	public ActiveSkill(Integer type) {
		// TODO Auto-generated constructor stub
		super(type);
	}
	
	@Override
	public void afterConstruct() {
		// TODO Auto-generated method stub
		AbstractOption option = new ConjureToCorpsOption(this);
		addOption(option);
	}

	public Integer getUseItOnFriendOrFoeOrAll() {
		return useItOnFriendOrFoeOrAll;
	}

	public void setUseItOnFriendOrFoeOrAll(Integer useItOnFriendOrFoeOrAll) {
		this.useItOnFriendOrFoeOrAll = useItOnFriendOrFoeOrAll;
	}

	public Boolean getUseItOnYouself() {
		return useItOnYouself;
	}

	public void setUseItOnYouself(Boolean useItOnYouself) {
		this.useItOnYouself = useItOnYouself;
	}

	public String getTarget() {
		return this.target;
	}
	
	/**
	 * 技能使用范围
	 * @return range不一定总是从1开始计算，例如3-8；因此range是一个
	 */
	public String getRange() {
		return this.range;
	}
	
	public void setRange(String range) {
		this.range = range;
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
		this.isReserve = prepare>0;
	}
	
	/**
	 * 施法等待，法术使用后一定回合才会生效
	 * @return
	 */
	public Integer getConjureWait() {
		return conjureWait;
	}

	public void setConjureWait(Integer conjureWait) {
		this.conjureWait = conjureWait;
	}

	/**
	 * 是否支持蓄力
	 * @return
	 */
	public Boolean isReserve() {
		return this.isReserve;
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
		
		Place place = (Place) objects[0];
		Corps corps = place.getCorps();
		corps.affected(this);
	}
	
	/**
	 * 造成伤害，并判断是否死亡
	 * @param harm 伤害
	 * @param corps 被伤害者
	 * @return false 表示 死亡
	 */
	protected Boolean harmToCorps(Integer harm, Corps corps) {
		corps.getDeath().setHp(Util.Sub, harm);
		if(corps.getDeath().getHp()<=0){
			corps.death();
			return false;
		}else{
			return true;
		}
	}
}
