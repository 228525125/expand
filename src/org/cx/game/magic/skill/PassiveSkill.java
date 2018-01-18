package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.magic.skill.IPassiveSkill;
import org.cx.game.widget.IGround;
import org.cx.game.action.IAction;
import org.cx.game.action.Upgrade;
import org.cx.game.action.UpgradeSkill;
import org.cx.game.corps.Corps;
import org.cx.game.intercepter.IIntercepter;

/**
 * 被动技能
 * 除Dodge、AttackBack、Parry等系统级被动外，通常都要覆盖setOwer方法，并指定action；
 * @author chenxian
 *
 */
public abstract class PassiveSkill extends AbstractSkill implements IPassiveSkill {
	
	/**
	 * 控制效果是否有效的标记，当invalid被调用该标记为false；另外该标记应该在调用affect之前被判断；
	 */
	protected Boolean activation = true;
	private Boolean isDelete = false;
	private Integer atk = 0;
	private Integer def = 0;
	
	private Upgrade upgrade = null;
	
	public PassiveSkill(Integer id) {
		// TODO Auto-generated constructor stub
		super(id);
	}
	
	@Override
	public Boolean isInvoke() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void activate() {
		// TODO Auto-generated method stub
		this.activation = true;
	}
	
	@Override
	public void invalid() {
		// TODO Auto-generated method stub
		this.activation = false;
	}
	
	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "action";
	}
	
	@Override
	public Integer getOrder() {
		// TODO Auto-generated method stub
		return IIntercepter.Order_Default;
	}
	
	@Override
	public Integer getLevel() {
		// TODO Auto-generated method stub
		return IIntercepter.Level_Current;
	}
	
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		this.isDelete = true;
	}
	
	@Override
	public Boolean isDelete() {
		// TODO Auto-generated method stub
		return this.isDelete;
	}
	
	@Override
	public Integer getAtk() {
		// TODO Auto-generated method stub
		return this.atk;
	}
	
	@Override
	public Integer getDef() {
		// TODO Auto-generated method stub
		return this.def;
	}
	
	@Override
	public List<Integer> getConjureRange() {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		Corps corps = getOwner();
		IGround ground = corps.getGround();
		Integer position = corps.getPosition();
		positionList = ground.areaForDistance(position, getRange(), IGround.Contain);
		return positionList;
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
	
	public Upgrade getUpgrade() {		
		if(null==upgrade){
			Upgrade upgrade = new UpgradeSkill();
			upgrade.setOwner(this);
			this.upgrade = upgrade;
		}
		return upgrade;
	}
}
