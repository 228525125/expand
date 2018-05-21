package org.cx.game.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.Death.DeathAddToHpAction;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Hero;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.IBuff;
import org.cx.game.magic.skill.IPassiveSkill;
import org.cx.game.magic.skill.ISkill;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.rule.IRule;
import org.cx.game.widget.treasure.EmpiricValue;
import org.cx.game.widget.treasure.ITreasure;
import org.cx.game.widget.treasure.EquipmentTreasure;

/**
 * 受到攻击
 * @author chenxian
 *
 */
public class Attacked extends AbstractAction implements IAction {

	private Boolean fightBack = true;
	private Integer def = 0;         //真实防御力
	private Integer armourDef = 0;   //装备防御力
	private Integer extraDef = 0;    //额外防御力
	private Integer landformDef = 0; //地形防御力
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	public Integer getDef() {
		// TODO Auto-generated method stub
		return this.def;
	}
	
	public void setDef(Integer def) {
		// TODO Auto-generated method stub
		this.def = def;
	}
	
	public Integer getArmourDef() {
		// TODO Auto-generated method stub
		return this.armourDef;
	}
	
	public void setArmourDef(Integer armourDef) {
		// TODO Auto-generated method stub
		if(!armourDef.equals(this.armourDef)){
			this.armourDef = armourDef;
		}
	}
	
	public Integer getLandformDef() {
		return landformDef;
	}

	public void setLandformDef(Integer landformDef) {
		if(!landformDef.equals(this.landformDef)){
			this.landformDef = landformDef;
		}
	}

	public Integer getExtraDef() {
		return extraDef;
	}

	public void setExtraDef(Integer extraDef) {
		if(!extraDef.equals(this.extraDef)){
			this.extraDef = extraDef;
		}
	}

	public Boolean getFightBack() {
		// TODO Auto-generated method stub
		return this.fightBack;
	}
	
	public void setFightBack(Boolean fightBack) {
		// TODO Auto-generated method stub
		if(!fightBack.equals(this.fightBack)){
			this.fightBack = fightBack;
		}
	}

	@Override
	public void action(Object...objects) {
		// TODO Auto-generated method stub
		Corps attackLife = (Corps) objects[0];
		Attack attack = (Attack) objects[1];
		
		/*
		 * 伤害 使用H3计算规则
		 */
		Integer atk = attack.getAtk();
		Integer def = getDef();
		Integer temp = atk - def;
		Integer ratio = temp<0 ? temp*25 : temp*50;
		ratio += 1000;
		Integer[] dmg = Attack.IntegerToDamage(attack.getDmg());
		Integer result = Random.nextInt(dmg[1]-dmg[0]);
		Integer damage =  dmg[0]+result;
		damage = damage*ratio/1000;
		damage = -damage;
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("attack", attack.getOwner());
		map.put("attacked", getOwner());
		map.put("position", getOwner().getPosition());
		map.put("damage", damage);
		NotifyInfo info = new NotifyInfo(NotifyInfo.Corps_Attacked,map);
		super.notifyObservers(info);
		
		//造成的实际伤害
		Death death = getOwner().getDeath();
		death.addToHp(damage);
		damage = ((DeathAddToHpAction) death.getAddToHpAction()).getDamage();
		
		//增加经验值
		CorpsUpgrade uc = (CorpsUpgrade) attack.getOwner().getUpgrade();
		uc.addToEmpiricValue(Math.abs(damage));
		
		/*
		 * 反击
		 */
		if(AbstractCorps.Death_Status_Live.equals(getOwner().getDeath().getStatus())          //没有死亡 
			&& getFightBack()                                           //是否反击过 
			&& !attack.getCounterAttack()                               //这次攻击方式是否是反击
			&& 0<getOwner().getAttack().getAtk()){                        //是否有攻击力 
			
			getOwner().getAttack().setCounterAttack(true);      //设置为反击
			getOwner().attack(attackLife);
			getOwner().getAttack().setCounterAttack(false);     //反击结束
			getOwner().getAttacked().setFightBack(false);
		} 
	}
}
