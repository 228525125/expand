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
import org.cx.game.action.Defend;
import org.cx.game.action.Call;
import org.cx.game.action.Chuck;
import org.cx.game.action.Conjure;
import org.cx.game.action.Death;
import org.cx.game.action.Grow;
import org.cx.game.action.IAction;
import org.cx.game.action.Leave;
import org.cx.game.action.Merge;
import org.cx.game.action.Move;
import org.cx.game.action.Pick;
import org.cx.game.action.Upgrade;
import org.cx.game.action.CorpsUpgrade;
import org.cx.game.ai.CorpsAgent;
import org.cx.game.core.Player;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.IMagic;
import org.cx.game.magic.buff.AbstractBuff;
import org.cx.game.magic.buff.AttackLockBuff;
import org.cx.game.magic.skill.AbstractSkill;
import org.cx.game.tag.ITag;
import org.cx.game.tag.TagHelper;
import org.cx.game.tools.CommonIdentifier;
import org.cx.game.tools.I18n;
import org.cx.game.tools.Util;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Ground;
import org.cx.game.widget.Place;
import org.cx.game.widget.treasure.Mineral;
import org.cx.game.widget.treasure.Treasure;

public class Corps implements ITag {

	private Integer type = null;          //类型唯一标识
	private Integer id = null;            //每场比赛动态生成的唯一标识
	private Integer position = null;
	private String name = null;
	private Integer hp = 0;
	private Integer ration = 1;
	private Integer energy = 100;
	private Integer moveType = CommonIdentifier.Move_Type_Walk;
	private Boolean hide = false;
	private Integer atk=0;
	private Integer dmg = 180081;
	private Integer attackRange = 1;
	private Integer attackMode = CommonIdentifier.Attack_Mode_Near;
	private Boolean mobile = false;
	private Integer def = 0;
	private Integer speed = 100;
	private Integer fleeChance = 0;
	private Integer lockChance = 100;
	private Integer level = 1;            //通过获取经验来提升的等级
	private Integer star = 1;             //稀有度
	private Integer rank = 1;             //阶层
	private Boolean hero = false;
	private Integer troop = 0;
	private Integer empiricValue = 0;      //经验值
	
	private Mineral consume = new Mineral();
	private Player player = null;
	private Ground ground = null;
	
	private CorpsAddBuffAction addBuffAction = null;
	private CorpsRemoveBuffAction removeBuffAction = null;
	private CorpsAddSkillAction addSkillAction = null;
	
	private Boolean activation = false;            //激活状态
	
	private Activate activate = null;
	private Attack attack = null;
	private Defend defend = null;
	private CorpsAgent agent = null;
	private Conjure conjure = null;
	private Grow grow = null;
	private Affected affected = null;
	private Move move = null;
	private Call call = null;
	private Death death = null;
	private Chuck chuck = null;
	private Upgrade upgrade = null;
	private Pick pick = null;
	private Merge merge = null;
	private Leave leave = null;
	
	private List<AbstractBuff> nexusBuffList = new ArrayList<AbstractBuff>();
	private List<AbstractBuff> buffList = new ArrayList<AbstractBuff>();
	private List<AbstractSkill> skillList = new ArrayList<AbstractSkill>();
	private List<AbstractOption> optionList = new ArrayList<AbstractOption>();
	private List<Corps> teammateList = new ArrayList<Corps>();
	private Map<Integer, String> upgradeRequirement = new HashMap<Integer, String>();
	
	public Corps(Integer type) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.id = Util.newCount();
		
		upgradeRequirement.put(2, "e100");
		upgradeRequirement.put(3, "e300");
		upgradeRequirement.put(4, "e700");
	}
	
	/**
	 * 类型唯一标识
	 * @return
	 */
	public Integer getType() {
		return type;
	}
	
	/**
	 * 每场比赛动态生成的唯一标识
	 */
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	public String getName() {
		if(null==name)
			name = I18n.getMessage(this, getType(), "name");
		return name;
	}

	public Player getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}
	
	public void setPlayer(Player player) {
		// TODO Auto-generated method stub
		this.player = player;
	}
	
	public Integer getPosition() {
		// TODO Auto-generated method stub
		return position;
	}
	
	public Ground getGround() {
		return ground;
	}

	public void setGround(Ground ground) {
		this.ground = ground;
	}
	
	public CorpsAgent getAgent() {
		return agent;
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
	public Boolean isHero() {
		return hero;
	}

	public void setHero(Boolean hero) {
		this.hero = hero;
	}
	
	/**
	 * 星，代表价值，稀有度
	 * @return
	 */
	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}
	
	/**
	 * 阶层，代表一个段位
	 * @return
	 */
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	/**
	 * 消耗资源
	 */
	public Mineral getConsume() {
		// TODO Auto-generated method stub
		return consume;
	}
	
	public void setConsume(Mineral consume) {
		// TODO Auto-generated method stub
		this.consume = consume;
	}
	
	/**
	 * 死亡后能提供的经验值
	 * @return
	 */
	public Integer getEmpiricValue() {
		return empiricValue;
	}

	public void setEmpiricValue(Integer empiricValue) {
		this.empiricValue = empiricValue;
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
	
	/**
	 * 阵营
	 * @return
	 */
	public Integer getTroop() {
		return troop;
	}

	public void setTroop(Integer troop) {
		this.troop = troop;
	}
	
	public List<Corps> getTeammateList() {
		return this.teammateList;
	}
	
	public void removeCorpsFromTeammateList(Corps corps) {
		this.teammateList.remove(corps);
		
		for(AbstractOption option : getOptionList()){
			if(corps.equals(option.getOwner())){
				removeOption(option);
				break;
			}
		}
	}
	
	//---------------------- NexusBuff ----------------------

	/**
	 * Corps自己发起的buff
	 * @return
	 */
	public List<AbstractBuff> getNexusBuffList(){
		return this.nexusBuffList;
	}
	
	public void addNexusBuff(AbstractBuff buff){
		this.nexusBuffList.add(buff);
	}
	
	public void removeNexusBuff(AbstractBuff buff){	
		this.nexusBuffList.remove(buff);
	}
	
	public List<AbstractBuff> getNexusBuff(Class clazz){
		List<AbstractBuff> ret = new ArrayList<AbstractBuff>();
		List<AbstractBuff> buffs = new ArrayList<AbstractBuff>();
		buffs.addAll(nexusBuffList);
		for(AbstractBuff buff : buffs)
			if(buff.getClass().equals(clazz))
				ret.add(buff);
		return ret;
	}
	
	public void clearNexusBuff(){
		List<AbstractBuff> buffs = new ArrayList<AbstractBuff>();
		buffs.addAll(nexusBuffList);
		for(AbstractBuff buff : buffs){
			buff.invalid();
		}
	}
	
	//--------------------- Buff ---------------------
	
	public IAction getAddBuffAction(){
		if(null==this.addBuffAction){
			this.addBuffAction = new CorpsAddBuffAction();
			this.addBuffAction.setOwner(this);
		}
		
		return this.addBuffAction;
	}
	
	public void addBuff(AbstractBuff buff) {
		IAction action = new ActionProxyHelper(getAddBuffAction());
		action.action(buff);
	}
	
	public IAction getRemoveBuffAction() {
		// TODO Auto-generated method stub
		if(null==this.removeBuffAction){
			this.removeBuffAction = new CorpsRemoveBuffAction();
			this.removeBuffAction.setOwner(this);
		}
		
		return this.removeBuffAction;
	}
	
	/**
	 * 该方法仅用于Buff.invalid
	 * @param buff
	 */
	public void removeBuff(AbstractBuff buff) {
		IAction action = new ActionProxyHelper(getRemoveBuffAction());
		action.action(buff);
	}
	
	/**
	 * 自身Buff
	 */
	public List<AbstractBuff> getBuffList() {
		return buffList;
	}
	
	public List<AbstractBuff> getBuff(Class clazz){
		List<AbstractBuff> ret = new ArrayList<AbstractBuff>();
		List<AbstractBuff> buffs = new ArrayList<AbstractBuff>();
		buffs.addAll(buffList);
		for(AbstractBuff buff : buffs)
			if(buff.getClass().equals(clazz))
				ret.add(buff);
		return ret;
	}
	
	public List<AbstractBuff> getBuff(String className){
		List<AbstractBuff> ret = new ArrayList<AbstractBuff>();
		List<AbstractBuff> buffs = new ArrayList<AbstractBuff>();
		buffs.addAll(buffList);
		for(AbstractBuff buff : buffs)
			if(buff.getClass().getName().equals(className))
				ret.add(buff);
		return ret;
	}
	
	public void clearBuff(){
		List<AbstractBuff> buffs = new ArrayList<AbstractBuff>();
		buffs.addAll(buffList);
		for(AbstractBuff buff : buffs){
			buff.invalid();
		}
	}
	
	public Boolean containsBuff(Class clazz){
		for(AbstractBuff buff : this.buffList)
			if(buff.getClass().equals(clazz))
				return true;
		return false;
	}
	
	//------------------- Skill ---------------------
	
	public IAction getAddSkillAction() {
		// TODO Auto-generated method stub
		if(null==this.addSkillAction){
			this.addSkillAction = new CorpsAddSkillAction();
			this.addSkillAction.setOwner(this);
		}
		return this.addSkillAction;
	}
	
	public void addSkill(AbstractSkill skill) {
		IAction action = new ActionProxyHelper(getAddSkillAction());
		action.action(skill);
	}
	
	public List<AbstractSkill> getSkillList() {
		return skillList;
	}
	
	public void setSkillList(List<AbstractSkill> skillList) {
		for(AbstractSkill skill : skillList)
			skill.setOwner(this);
		this.skillList = skillList;
	}

	/**
	 * 
	 * @param code skill的id，这里为了兼容一部分程序，增加code也作为index
	 * @return
	 */
	public AbstractSkill getSkill(Integer code){
		AbstractSkill skill = null;
		if(code<skillList.size()){
			skill = (AbstractSkill) skillList.get(code);
		}else{
			for(AbstractSkill s : skillList){
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
	
	//--------------------- Option ----------------------
	
	public void addOption(AbstractOption option) {
		this.optionList.add(option);
	}
	
	public Boolean removeOption(AbstractOption option) {
		return this.optionList.remove(option);
	}
	
	public List<AbstractOption> getOptionList() {
		return this.optionList;
	}
	
	public List<AbstractOption> getOptionByClass(Class clazz) {
		List<AbstractOption> retList = new ArrayList<AbstractOption>();
		for(AbstractOption option : getOptionList()){
			if(clazz.equals(option.getClass()))
				retList.add(option);
		}
		
		return retList;
	}
	
	public AbstractOption getOption(Integer index) {
		return getOptionList().get(index);
	}
	
	public void updateAtk() {
		// TODO Auto-generated method stub
		Integer atk = this.getAtk();
		Integer weaponAtk = this.getAttack().getWeaponAtk();
		Integer landformAtk = this.getAttack().getLandformAtk();
		Integer extraAtk = this.getAttack().getExtraAtk();
		this.getAttack().setAtk(atk + weaponAtk + landformAtk + extraAtk);
	}
	
	public void updateDef(){
		Integer def = this.getDef();
		Integer armourDef = this.getDefend().getArmourDef();
		Integer landformDef = this.getDefend().getLandformDef();
		Integer extraDef = this.getDefend().getExtraDef();
		this.getDefend().setDef(def + armourDef + landformDef + extraDef);
	}
	
	public void addCorpsToTeammateList(Corps corps) {
		// TODO Auto-generated method stub
		this.teammateList.add(corps);
		corps.setPosition(getPosition());
		
		addOption(new TeammateOption(corps));
	}
	
	public void setPosition(Integer position) {
		// TODO Auto-generated method stub
		this.position = position;
		
		for(Corps corps : getTeammateList()){
			corps.setPosition(position);
		}
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
			attack.setOwner(this);
		}
		return attack;
	}
	
	public Defend getDefend() {
		if(null==defend){
			defend = new Defend();
			defend.setDef(getDef());
			defend.setOwner(this);
		}
		return defend;
	}
	
	public Conjure getConjure() {
		if(null==conjure){
			conjure = new Conjure();
			conjure.setOwner(this);
		}
		return conjure;
	}
	
	public Grow getGrow() {
		if(null==grow){
			grow = new Grow();
			grow.setOwner(this);
		}
		return grow;
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
			move.setMobile(getMobile());
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
			death.setHpLimit(getHp());
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
	
	public IAction getMerge() {
		// TODO Auto-generated method stub
		if(null==merge){
			merge = new Merge();
			merge.setOwner(this);
		}
		return this.merge;
	}
	
	public IAction getLeave() {
		// TODO Auto-generated method stub
		if(null==leave){
			leave = new Leave();
			leave.setOwner(this);
		}
		return this.leave;
	}
	
	public Boolean isLock() {
		return !getBuff(AttackLockBuff.class).isEmpty();
	}
	
	/**
	 * 激活
	 * @param activate
	 */
	public void activate(Boolean activate) {
		IAction action = new ActionProxyHelper(getActivate());
		action.action(activate);
	}

	/**
	 * 攻击
	 * @param attacked 被攻击的卡片
	 */
	public void attack(Corps attacked) {
		IAction action = new ActionProxyHelper(getAttack());
		action.action(attacked);
	}
	
	/**
	 * 受攻击
	 * @param attack
	 */
	public void defend(Object...objects) {
		IAction action = new ActionProxyHelper(getDefend());
		action.action(objects);
	}
	
	/**
	 * 受到法术影响
	 * @param magic
	 */
	public void affected(IMagic magic) {
		IAction action = new ActionProxyHelper(getAffected());
		action.action(magic);
	}
	
	/**
	 * 施法
	 * @param skill ActiveSkill
	 * @param objects Corps/Place/...
	 */
	public void conjure(AbstractSkill skill, Object...objects) {
		IAction action = new ActionProxyHelper(getConjure());
		action.action(skill,objects);
	}
	
	/**
	 * 蓄力
	 */
	public void grow() {
		IAction action = new ActionProxyHelper(getGrow());
		action.action();
	}
	
	/**
	 * 移动到指定位置
	 * @param position 指定位置
	 */
	public void move(Place place) {
		IAction action = new ActionProxyHelper(getMove());
		action.action(place);
	}
	
	/**
	 * 召唤
	 *
	 */
	public void call(Place place, Integer nop) {
		IAction action = new ActionProxyHelper(getCall());
		action.action(place,nop);
	}

	/**
	 * 死亡
	 */
	public void death() {
		IAction action = new ActionProxyHelper(getDeath());
		action.action();
	}
	
	/**
	 * 丢弃
	 */
	public void chuck() {
		IAction action = new ActionProxyHelper(getChuck());
		action.action();
	}
	
	/**
	 * 升级
	 * @throws RuleValidatorException
	 */
	public void upgrade() {
		IAction action = new ActionProxyHelper(getUpgrade());
		action.action();
	}
	
	/**
	 * 拾取
	 * @param treasure
	 * @throws RuleValidatorException
	 */
	public void pick(Treasure treasure) {
		IAction action = new ActionProxyHelper(getPick());
		action.action(treasure);
	}
	
	/**
	 * 合并到corps
	 * @param corps 
	 */
	public void merge(Corps corps) {
		IAction action = new ActionProxyHelper(getMerge());
		action.action(corps);
	}
	
	/**
	 * 分离
	 * @param place
	 */
	public void leave(Place place) {
		IAction action = new ActionProxyHelper(getLeave());
		action.action(place);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return id.intValue();
	}
	
	public Boolean contains(Integer tag) {
		// TODO Auto-generated method stub
		List<Integer> objectList = TagHelper.queryForTag(tag);
		return objectList.contains(getType());
	}

	public List<Integer> queryTagForCategory(Integer category) {
		// TODO Auto-generated method stub
		List<Integer> list1 =  TagHelper.queryForCategory(category);
		List<Integer> list2 = TagHelper.queryForObject(getType());
		list2.retainAll(list1);
		return list2;
	}
	
	public List<Integer> queryTagForObject() {
		// TODO Auto-generated method stub
		return TagHelper.queryForObject(getType());
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof Corps) {
			Corps corps = (Corps) obj;
			return getId().equals(corps.getId());
		}else{
			return false;
		}
	}
	
	public class CorpsAddBuffAction extends AbstractAction implements IAction {

		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			AbstractBuff buff = (AbstractBuff) objects[0];
			
			for(AbstractBuff b : getBuffList()) {     //当添加一个已有的buff,并且不能叠加时，要先删除之前的buff
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
			AbstractBuff buff = (AbstractBuff) objects[0];
			
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
			AbstractSkill skill = (AbstractSkill) objects[0];
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
