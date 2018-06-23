package org.cx.game.corps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.AbstractAction;
import org.cx.game.action.ActionProxyHelper;
import org.cx.game.action.Activate;
import org.cx.game.action.Affected;
import org.cx.game.action.Attack;
import org.cx.game.action.Attacked;
import org.cx.game.action.Call;
import org.cx.game.action.Chuck;
import org.cx.game.action.Conjure;
import org.cx.game.action.Death;
import org.cx.game.action.IAction;
import org.cx.game.action.Move;
import org.cx.game.action.Pick;
import org.cx.game.action.Upgrade;
import org.cx.game.action.CorpsUpgrade;
import org.cx.game.ai.CorpsAgent;
import org.cx.game.core.IPlayer;
import org.cx.game.core.IPlayerE;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.IBuff;
import org.cx.game.magic.skill.ISkill;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.I18n;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IGroundE;
import org.cx.game.widget.treasure.IResource;
import org.cx.game.widget.treasure.Resource;

import com.sun.org.apache.xml.internal.dtm.ref.CoroutineManager;

public class Corps extends AbstractCorps { 
	
	private static final String Effect = "_Effect";
	private static final String Invalid = "_Invalid";
	
	
	private String name = null;
	private Integer hp = 0;
	private Integer ration = 1;
	private Integer energy = 100;
	private Integer moveType = CommonIdentifierE.Move_Type_Walk;
	private Boolean hide = false;
	private Integer atk=0;
	private Integer dmg = 180081;
	private Integer attackRange = 0;
	private Integer attackMode = CommonIdentifierE.Attack_Mode_Near;
	private Boolean mobile = false;
	private Integer def = 0;
	private Integer speed = 100;
	private Integer fleeChance = 0;
	private Integer lockChance = 0;
	private Integer level = 1;
	private Integer star = 1;
	private Boolean hero = false;
	private Boolean activation = false;            //激活状态
	
	private IResource consume = new Resource();
	private CorpsAddBuffAction addBuffAction = null;
	private CorpsRemoveBuffAction removeBuffAction = null;
	private CorpsAddSkillAction addSkillAction = null;
	
	
	private Activate activate = null;
	private Attack attack = null;
	private Attacked attacked = null;
	private CorpsAgent agent = null;
	private Conjure conjure = null;
	private Affected affected = null;
	private Move move = null;
	private Call call = null;
	private Death death = null;
	private Chuck chuck = null;
	private Upgrade upgrade = null;
	private Pick pick = null;
	
	private List<IBuff> nexusBuffList = new ArrayList<IBuff>();
	private List<IBuff> buffList = new ArrayList<IBuff>();
	private List<ISkill> skillList = new ArrayList<ISkill>();
	private Map<Integer, String> upgradeRequirement = new HashMap<Integer, String>();
	
	public Corps(Integer type) {
		// TODO Auto-generated constructor stub
		super(type);
		
		upgradeRequirement.put(2, "e-100");
		upgradeRequirement.put(3, "e-200");
		upgradeRequirement.put(4, "e-400");
	}
	
	@Override
	public IPlayerE getPlayer() {
		// TODO Auto-generated method stub
		return (IPlayerE) super.getPlayer();
	}
	
	@Override
	public IGroundE getGround() {
		// TODO Auto-generated method stub
		return (IGroundE) super.getGround();
	}
	
	public CorpsAgent getAgent() {
		return agent;
	}
	
	public String getName() {
		if(null==name)
			name = I18n.getMessage(this, getType(), "name");
		return name;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	/**
	 * 人口
	 * @return
	 */
	public Integer getRation() {
		return ration;
	}

	public void setRation(Integer ration) {
		this.ration = ration;
	}

	/**
	 * 精力、与移动范围相关
	 */
	public Integer getEnergy() {
		return energy;
	}

	public void setEnergy(Integer energy) {
		this.energy = energy;
	}
	
	/**
	 * 移动类型
	 * @return
	 */
	public Integer getMoveType() {
		return moveType;
	}

	public void setMoveType(Integer moveType) {
		this.moveType = moveType;
	}

	/**
	 * 隐形状态
	 */
	public Boolean getHide() {
		return hide;
	}

	public void setHide(Boolean hide) {
		this.hide = hide;
	}

	public Integer getAtk() {
		// TODO 自动生成方法存根
		return atk;
	}
	
	public void setAtk(Integer atk) {
		this.atk = atk;
	}
	
	public void updateAtk() {
		// TODO Auto-generated method stub
		Integer atk = this.getAtk();
		Integer weaponAtk = this.getAttack().getWeaponAtk();
		Integer landformAtk = this.getAttack().getLandformAtk();
		Integer extraAtk = this.getAttack().getExtraAtk();
		this.getAttack().setAtk(atk + weaponAtk + landformAtk + extraAtk);
	}
	
	/**
	 * 伤害
	 * @return
	 */
	public Integer getDmg() {
		return dmg;
	}

	public void setDmg(Integer dmg) {
		this.dmg = dmg;
	}

	/**
	 * 攻击范围
	 */
	public Integer getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(Integer attackRange) {
		this.attackRange = attackRange;
	}
	
	/**
	 * 攻击模式，远/近
	 */
	public Integer getAttackMode() {
		return attackMode;
	}

	public void setAttackMode(Integer attackMode) {
		this.attackMode = attackMode;
	}
	
	/**
	 * 移动攻击，例如，骑兵
	 * @return
	 */
	public Boolean getMobile() {
		return mobile;
	}

	public void setMobile(Boolean mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 初始防御力
	 * @return
	 */
	public Integer getDef(){
		return this.def;
	}
	
	public void setDef(Integer def){
		this.def = def;
	}
	
	public void updateDef(){
		Integer def = this.getDef();
		Integer armourDef = this.getAttacked().getArmourDef();
		Integer landformDef = this.getAttacked().getLandformDef();
		Integer extraDef = this.getAttacked().getExtraDef();
		this.getAttacked().setDef(def + armourDef + landformDef + extraDef);
	}
	
	/**
	 * 速度，基准100，为什么速度是int型，因为在ControlQueue中使用int方便计算
	 */
	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	
	/**
	 * 逃脱几率
	 */
	public Integer getFleeChance() {
		return fleeChance;
	}

	public void setFleeChance(Integer fleeChance) {
		this.fleeChance = fleeChance;
	}

	/**
	 * 锁定几率
	 */
	public Integer getLockChance() {
		return lockChance;
	}

	public void setLockChance(Integer lockChance) {
		this.lockChance = lockChance;
	}
	
	/**
	 * 是否为英雄卡
	 * @return
	 */
	public Boolean getHero() {
		return hero;
	}

	public void setHero(Boolean hero) {
		this.hero = hero;
	}
	
	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}
	
	/**
	 * 消耗资源
	 */
	public IResource getConsume() {
		// TODO Auto-generated method stub
		return consume;
	}
	
	public void setConsume(IResource consume) {
		// TODO Auto-generated method stub
		this.consume = consume;
	}
	
	/**
	 * 等级
	 * @return
	 */
	public Integer getLevel(){
		return level;
	}
	
	public void setLevel(Integer level){
		this.level = level;
	}
	
	public List<IBuff> getNexusBuffList(){
		return this.nexusBuffList;
	}
	
	/**
	 * 发起方状态
	 */
	public void addNexusBuff(IBuff buff){
		this.nexusBuffList.add(buff);
	}
	
	public void removeNexusBuff(IBuff buff){	
		this.nexusBuffList.remove(buff);
	}
	
	public List<IBuff> getNexusBuff(Class clazz){
		List<IBuff> ret = new ArrayList<IBuff>();
		List<IBuff> buffs = new ArrayList<IBuff>();
		buffs.addAll(nexusBuffList);
		for(IBuff buff : buffs)
			if(buff.getClass().equals(clazz))
				ret.add(buff);
		return ret;
	}
	
	public void clearNexusBuff(){
		List<IBuff> buffs = new ArrayList<IBuff>();
		buffs.addAll(nexusBuffList);
		for(IBuff buff : buffs){
			buff.invalid();
		}
	}

	/**
	 * 自身Buff
	 */
	public List<IBuff> getBuffList() {
		return buffList;
	}
	
	public IAction getAddBuffAction(){
		if(null==this.addBuffAction){
			this.addBuffAction = new CorpsAddBuffAction();
			this.addBuffAction.setOwner(this);
		}
		
		return this.addBuffAction;
	}
	
	@Override
	public IAction getRemoveBuffAction() {
		// TODO Auto-generated method stub
		if(null==this.removeBuffAction){
			this.removeBuffAction = new CorpsRemoveBuffAction();
			this.removeBuffAction.setOwner(this);
		}
		
		return this.removeBuffAction;
	}
	
	public List<IBuff> getBuff(Class clazz){
		List<IBuff> ret = new ArrayList<IBuff>();
		List<IBuff> buffs = new ArrayList<IBuff>();
		buffs.addAll(buffList);
		for(IBuff buff : buffs)
			if(buff.getClass().equals(clazz))
				ret.add(buff);
		return ret;
	}
	
	public List<IBuff> getBuff(String className){
		List<IBuff> ret = new ArrayList<IBuff>();
		List<IBuff> buffs = new ArrayList<IBuff>();
		buffs.addAll(buffList);
		for(IBuff buff : buffs)
			if(buff.getClass().getName().equals(className))
				ret.add(buff);
		return ret;
	}
	
	public void clearBuff(){
		List<IBuff> buffs = new ArrayList<IBuff>();
		buffs.addAll(buffList);
		for(IBuff buff : buffs){
			buff.invalid();
		}
	}
	
	public Boolean containsBuff(Class clazz){
		for(IBuff buff : this.buffList)
			if(buff.getClass().equals(clazz))
				return true;
		return false;
	}
	
	@Override
	public IAction getAddSkillAction() {
		// TODO Auto-generated method stub
		if(null==this.addSkillAction){
			this.addSkillAction = new CorpsAddSkillAction();
			this.addSkillAction.setOwner(this);
		}
		return this.addSkillAction;
	}

	public List<ISkill> getSkillList() {
		return skillList;
	}
	
	public void setSkillList(List<ISkill> skillList) {
		for(ISkill skill : skillList)
			skill.setOwner(this);
		this.skillList = skillList;
	}

	/**
	 * 
	 * @param code skill的id，这里为了兼容一部分程序，增加code也作为index
	 * @return
	 */
	public ISkill getSkill(Integer code){
		ISkill skill = null;
		if(code<skillList.size()){
			skill = (ISkill) skillList.get(code);
		}else{
			for(ISkill s : skillList){
				if(code.equals(s.getType()))
					skill = s;
			}
		}
		
		return skill;
	}
	
	public Boolean containsSkill(Class clazz){
		for(Object skill : this.skillList){
			if(skill.getClass().equals(clazz))
				return true;
		}
		return false;
	}

	public Activate getActivate() {
		if(null==activate){
			activate = new Activate();
			activate.setActivation(activation);
			activate.setSpeed(getSpeed());
			activate.setOwner(this);
		}
		return activate;
	}
	
	public Attack getAttack() {
		if(null==attack){
			attack = new Attack();
			attack.setAtk(getAtk());
			attack.setDmg(getDmg());
			attack.setRange(getAttackRange());
			attack.setLockChance(getLockChance());
			attack.setMode(getAttackMode());
			attack.setMobile(getMobile());
			attack.setOwner(this);
		}
		return attack;
	}
	
	public Attacked getAttacked() {
		if(null==attacked){
			attacked = new Attacked();
			attacked.setDef(getDef());
			attacked.setOwner(this);
		}
		return attacked;
	}
	
	public Conjure getConjure() {
		if(null==conjure){
			conjure = new Conjure();
			conjure.setOwner(this);
		}
		return conjure;
	}
	
	public Affected getAffected() {
		if(null==affected){
			affected = new Affected();
			affected.setOwner(this);
		}
		return affected;
	}
	
	public Move getMove() {
		if(null==move){
			move = new Move();
			move.setEnergy(getEnergy());
			move.setFlee(getFleeChance());
			move.setType(getMoveType());
			move.setHide(getHide());
			move.setOwner(this);
		}
		return move;
	}
	
	public Call getCall() {
		if(null==call){
			call = new Call();
			call.setConsume(getConsume());
			call.setRation(getRation());
			call.setOwner(this);
		}
		
		return call;
	}
	
	public Death getDeath() {
		if(null==death){
			death = new Death();
			death.setHp(getHp());
			death.setOwner(this);
		}
		return death;
	}

	public Chuck getChuck() {
		if(null==chuck){
			chuck = new Chuck();
			chuck.setOwner(this);
		}
		return chuck;
	}
	
	public void setUpgradeRequirement(Map<Integer, String> upgradeRequirement) {
		this.upgradeRequirement = upgradeRequirement;
	}
	
	protected Map<Integer, String> getUpgradeRequirement() {
		return upgradeRequirement;
	}

	public Upgrade getUpgrade() {
		if(null==upgrade){
			upgrade = new CorpsUpgrade(upgradeRequirement);
			upgrade.setLevel(getLevel());            //setLevel会触发upgradeRequirement
			upgrade.setOwner(this);
		}
		return this.upgrade;
	}
	
	public Pick getPick() {
		if(null==pick){
			pick = new Pick();
			pick.setOwner(this);
		}
		return this.pick;
	}
	
	public class CorpsAddBuffAction extends AbstractAction implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			IBuff buff = (IBuff) objects[0];
			
			for(IBuff b : getBuffList()) {     //当添加一个已有的buff,并且不能叠加时，要先删除之前的buff
				if(b.getClass().equals(buff.getClass())&&!b.isDuplication()){
					removeBuff(b);
					break;
				}
			}
			
			getBuffList().add(buff);
		}
		
		@Override
		public Corps getOwner() {
			// TODO Auto-generated method stub
			return (Corps) super.getOwner();
		}
	}
	
	public class CorpsRemoveBuffAction extends AbstractAction implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			IBuff buff = (IBuff) objects[0];
			
			getBuffList().remove(buff);
		}
		
		@Override
		public Corps getOwner() {
			// TODO Auto-generated method stub
			return (Corps) super.getOwner();
		}
	}
	
	public class CorpsAddSkillAction extends AbstractAction implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			ISkill skill = (ISkill) objects[0];
			skill.setOwner(getOwner());
			getSkillList().add(skill);
		}
		
		@Override
		public Corps getOwner() {
			// TODO Auto-generated method stub
			return (Corps) super.getOwner();
		}
	}
}
