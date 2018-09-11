package org.cx.game.corps;

import java.util.HashMap;
import java.util.Map;

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
import org.cx.game.action.Leave;
import org.cx.game.action.Merge;
import org.cx.game.action.Move;
import org.cx.game.action.Pick;
import org.cx.game.action.Upgrade;
import org.cx.game.action.CorpsUpgrade;
import org.cx.game.ai.CorpsAgent;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.IMagic;
import org.cx.game.magic.skill.AbstractSkill;
import org.cx.game.widget.AbstractPlace;
import org.cx.game.widget.treasure.Mineral;
import org.cx.game.widget.treasure.Treasure;

public class Corps extends AbstractCorps {

	private Boolean activation = false;            //激活状态
	
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
	private Merge merge = null;
	private Leave leave = null;
	
	private Map<Integer, String> upgradeRequirement = new HashMap<Integer, String>();
	
	public Corps(Integer type) {
		// TODO Auto-generated constructor stub
		super(type);
		
		upgradeRequirement.put(2, "e-100");
		upgradeRequirement.put(3, "e-200");
		upgradeRequirement.put(4, "e-400");
	}
	
	public CorpsAgent getAgent() {
		return agent;
	}
	
	public void setHp(Integer hp) {
		super.setHp(hp);
	}

	public void setRation(Integer ration) {
		super.setRation(ration);
	}

	public void setEnergy(Integer energy) {
		super.setEnergy(energy);
	}
	
	public void setMoveType(Integer moveType) {
		super.setMoveType(moveType);
	}

	public void setHide(Boolean hide) {
		super.setHide(hide);
	}

	public void setAtk(Integer atk) {
		super.setAtk(atk);
	}
	
	public void updateAtk() {
		// TODO Auto-generated method stub
		Integer atk = this.getAtk();
		Integer weaponAtk = this.getAttack().getWeaponAtk();
		Integer landformAtk = this.getAttack().getLandformAtk();
		Integer extraAtk = this.getAttack().getExtraAtk();
		this.getAttack().setAtk(atk + weaponAtk + landformAtk + extraAtk);
	}
	
	public void setDmg(Integer dmg) {
		super.setDmg(dmg);
	}

	public void setAttackRange(Integer attackRange) {
		super.setAttackRange(attackRange);
	}
	
	public void setAttackMode(Integer attackMode) {
		super.setAttackMode(attackMode);
	}
	
	public void setMobile(Boolean mobile) {
		super.setMobile(mobile);
	}
	
	public void setDef(Integer def){
		super.setDef(def);
	}
	
	public void updateDef(){
		Integer def = this.getDef();
		Integer armourDef = this.getAttacked().getArmourDef();
		Integer landformDef = this.getAttacked().getLandformDef();
		Integer extraDef = this.getAttacked().getExtraDef();
		this.getAttacked().setDef(def + armourDef + landformDef + extraDef);
	}
	
	public void setSpeed(Integer speed) {
		super.setSpeed(speed);
	}
	
	public void setFleeChance(Integer fleeChance) {
		super.setFleeChance(fleeChance);
	}

	public void setLockChance(Integer lockChance) {
		super.setLockChance(lockChance);
	}
	
	public void setHero(Boolean hero) {
		super.setHero(hero);
	}
	
	public void setStar(Integer star) {
		super.setStar(star);
	}
	
	public void setConsume(Mineral consume) {
		// TODO Auto-generated method stub
		super.setConsume(consume);
	}
	
	public void setLevel(Integer level){
		super.setLevel(level);
	}
	
	@Override
	public void addCorpsToTeammateList(AbstractCorps corps) {
		// TODO Auto-generated method stub
		super.addCorpsToTeammateList(corps);
		
		addOption(new TroopOption(corps));
	}
	
	@Override
	public void setPosition(Integer position) {
		// TODO Auto-generated method stub
		super.setPosition(position);
		
		for(AbstractCorps corps : getTeammateList()){
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
	public void attack(AbstractCorps attacked) {
		IAction action = new ActionProxyHelper(getAttack());
		action.action(attacked);
	}
	
	/**
	 * 受攻击
	 * @param attack
	 */
	public void attacked(AbstractCorps corps, IAction attack) {
		IAction action = new ActionProxyHelper(getAttacked());
		action.action(corps,attack);
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
	 * @param objects
	 */
	public void conjure(AbstractSkill skill, Object...objects) {
		IAction action = new ActionProxyHelper(getConjure());
		action.action(skill,objects);
	}
	
	/**
	 * 移动到指定位置
	 * @param position 指定位置
	 */
	public void move(AbstractPlace place) {
		IAction action = new ActionProxyHelper(getMove());
		action.action(place);
	}
	
	/**
	 * 召唤
	 *
	 */
	public void call(AbstractPlace place, Integer nop) {
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
	public void merge(AbstractCorps corps) {
		IAction action = new ActionProxyHelper(getMerge());
		action.action(corps);
	}
	
	/**
	 * 分离
	 * @param place
	 */
	public void leave(AbstractPlace place) {
		IAction action = new ActionProxyHelper(getLeave());
		action.action(place);
	}
}
