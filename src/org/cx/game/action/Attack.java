package org.cx.game.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Corps;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.widget.Ground;

public class Attack extends AbstractAction implements IAction {
	
	private Integer mode = CommonIdentifierE.Attack_Mode_Far;          //模式，近战/远程
	private Integer range = 1;                        //距离
	private Integer lockChance = 0;                   //锁定几率
	private Integer atk = 0;                          //真实攻击力
	private Integer extraAtk = 0;                     //额外攻击力
	private Integer landformAtk = 0;                  //地形攻击力
	private Integer weaponAtk = 0;                    //武器攻击力
	private Boolean fightBack = false;                //是否是反击
	private Boolean attackable = false;
	
	public static String space = "8008";              //伤害间隔符
	private Integer  dmg = 180081;                          //伤害 180082 = 1-2
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		if(!range.equals(this.range))
			this.range = range;
	}
	
	public Integer getMode() {
		return mode;
	}
	
	public void setMode(Integer mode) {
		if(!mode.equals(this.mode))
			this.mode = mode;
	}
	
	public Integer getAtk() {
		return this.atk;
	}
	
	public void setAtk(Integer atk) {
		// TODO Auto-generated method stub
		this.atk = atk;
	}

	public Integer getWeaponAtk() {
		return weaponAtk;
	}
	
	public void setWeaponAtk(Integer weaponAtk) {
		// TODO Auto-generated method stub
		if(!weaponAtk.equals(this.weaponAtk)){
			this.weaponAtk = weaponAtk;
		}
	}

	public Integer getExtraAtk() {
		// TODO Auto-generated method stub
		return this.extraAtk;
	}

	public void setExtraAtk(Integer extraAtk) {
		if(!extraAtk.equals(this.extraAtk)){
			this.extraAtk = extraAtk;
		}
	}
	
	public Integer getLandformAtk() {
		return landformAtk;
	}

	public void setLandformAtk(Integer landformAtk) {
		if(!landformAtk.equals(this.landformAtk)){
			this.landformAtk = landformAtk;
		}
	}

	public Integer getDmg() {
		return dmg;
	}

	public void setDmg(Integer dmg) {
		this.dmg = dmg;
	}

	/*public void updateDmg() {
		// TODO Auto-generated method stub
		Integer [] dmg = IntegerToDamage(getOwner().getDmg());
		Integer d = DamageToInteger(new Integer[]{dmg[0],dmg[1]});
		setDmg(d);
	}*/

	public Integer getLockChance() {
		// TODO Auto-generated method stub
		return lockChance;
	}

	public void setLockChance(Integer lockChance) {
		// TODO Auto-generated method stub
		if(!lockChance.equals(this.lockChance)){
			this.lockChance = lockChance;
		}
	}
	
	public Boolean isFightBack() {
		return fightBack;
	}

	public void setFightBack(Boolean fightBack) {
		if(!fightBack.equals(this.fightBack)){
			this.fightBack = fightBack;
		}
	}
	
	public Boolean getAttackable() {
		// TODO Auto-generated method stub
		return this.attackable;
	}
	
	public void setAttackable(Boolean attackable) {
		// TODO Auto-generated method stub
		this.attackable = attackable;
	}

	@Override
	public void action(Object...objects) {
		// TODO Auto-generated method stub
		super.action(objects);
		
		Corps attacked = (Corps) objects[0];
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("attack", getOwner());
		map.put("attacked", attacked);
		map.put("position", getOwner().getPosition());
		String desc = getOwner().getName()+" "+(isFightBack() ? "【反击】" : "【攻击】")+attacked.getName()+"； 攻击力："+getAtk()+"，伤害："+Attack.IntegerToDamage(getDmg())[0]+"-"+Attack.IntegerToDamage(getDmg())[1]+"，mode:"+(getMode().equals(CommonIdentifierE.Attack_Mode_Near) ? "近战" : "远程");
		map.put("description", desc);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Attack,map);
		super.notifyObservers(info);
		
		Ground ground = getOwner().getGround();
		
		//如果是远程，这里要设置为近身攻击模式
		Integer distance = ground.distance(attacked.getPosition(), getOwner().getPosition());
		if(1==distance){                                           //近身
			addActionResult("attackMode", CommonIdentifierE.Attack_Mode_Near);
		}else{
			addActionResult("attackMode", CommonIdentifierE.Attack_Mode_Far);
		}
		
		addActionResult("atk", getAtk());
		
		//判断攻击模式，远程近战伤害减半
		if(CommonIdentifierE.Attack_Mode_Far.equals(getMode()) && 1==distance){
			Integer [] dmg = IntegerToDamage(getDmg());
			addActionResult("dmg", DamageToInteger(new Integer[]{dmg[0]/2,dmg[1]/2}));
		}else{
			addActionResult("dmg", getDmg());
			
			/*
			 * 判断是否受到干扰，远程射击时如果受到干扰攻击力减半
			 */
			if(isDisturbance()){
				addActionResult("atk", getAtk()/2);
			}
		}
		
		/*
		 * 生成朝向信息
		 */
		Integer direction = ground.getDirection(getOwner().getPosition(), attacked.getPosition());
		getOwner().getMove().setDirection(direction);
		addActionResult("direction", direction);
		
		addActionResult("isFightBack", isFightBack());
		
		setAttackable(false);
	}
	
	public static Integer[] IntegerToDamage(Integer dmg){
		String [] dmgs = dmg.toString().split(space);
		Integer x = Integer.valueOf(dmgs[0]);
		Integer y = Integer.valueOf(dmgs[1]);
		return new Integer [] {x,y};
	}
	
	public static Integer DamageToInteger(Integer [] dmg){
		return Integer.valueOf(dmg[0]+space+dmg[1]);
	}
	
	private Boolean isDisturbance() {
		Boolean ret = false;
		Ground ground = getOwner().getGround();
		List<Integer> list = ground.areaForDistance(getOwner().getPosition(), 1, Ground.Equal);
		for(Integer pos : list){
			Corps corps = ground.getCorps(pos);
			if(null!=corps && !getOwner().getPlayer().equals(corps.getPlayer()))
				return true;
		}
		return ret;
	}
}
