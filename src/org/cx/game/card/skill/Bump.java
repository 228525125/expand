package org.cx.game.card.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IMove;
import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.skill.DizzyBuff;
import org.cx.game.exception.CommandValidatorException;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.Debug;
import org.cx.game.tools.Util;
import org.cx.game.validator.IValidator;
import org.cx.game.validator.NeedConjurerValidator;
import org.cx.game.validator.Validator;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IPlace;

/**
 * 冲撞
 * @author chenxian
 *
 */
public class Bump extends ActiveSkill {

	private Integer range = 8;  //范围5-8
	private Integer atkScale = 0;   //每一格增加攻击比例
	private Integer dizzyBout = 0;  //击晕回合
	private Integer dizzyChance = 0;  //每一格增加击晕几率
	
	public Bump(Integer consume, Integer cooldown, Integer velocity,
			Integer style, Integer func, Integer range, Integer atkScale, 
			Integer dizzyChance, Integer dizzyBout) {
		super(consume, cooldown, velocity, style, func);
		// TODO Auto-generated constructor stub
		this.range = range;
		this.dizzyChance = dizzyChance;
		this.dizzyBout = dizzyBout;
		this.atkScale = atkScale;
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		// 优化：允许范围不从1开始计算，例如5-8；
		return range;
	}
	
	private Integer step = null;
	private IPlace place = null;
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.parameterTypeValidator(objects);
		
		LifeCard life = (LifeCard) objects[0];      //life必须相对于owner前后左右四个方向的直线上；
		LifeCard owner = getOwner();
		IGround ground = owner.getPlayer().getGround(); 
		
		BumpValidator validator = new BumpValidator(life, owner, ground);
		super.addValidator(validator);
		
		super.useSkill(objects);
		
		this.step = validator.getStep();
		this.place = validator.getPlace();
		
		life.affected(this);
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Integer atkValue = getOwner().getAttack().getAtk()*(100+this.atkScale*this.step)/100;
		Integer dChance = this.dizzyChance*this.step;
		
		LifeCard life = (LifeCard) objects[0];
		
		Integer energy = getOwner().getEnergy();
		if(Debug.isDebug)
			getOwner().setEnergy(IMove.Energy_Max);
		getOwner().getMove().setEnergy(IMove.Energy_Max);
		getOwner().move(this.place);
		getOwner().getMove().setEnergy(IMove.Energy_Min);
		if(Debug.isDebug)
			getOwner().setEnergy(energy);
		
		
		life.getDeath().magicToHp(-atkValue);
		
		if(Random.isTrigger(dChance)){
			new DizzyBuff(this.dizzyBout, life).effect();
		}
	}
	
	@Override
	public List<Integer> getConjureRange(IGround ground) {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		LifeCard card = (LifeCard) getOwner();
		Integer position = card.getContainerPosition();
		List<Integer> list = ground.line(position, IGround.Relative_Bottom, getRange());
		for(Integer pos : list){
			if(!ground.getPlace(pos).isDisable())
				positionList.add(pos);
			else
				break;
		}
		
		list = ground.line(position, IGround.Relative_Right, getRange());
		for(Integer pos : list){
			if(!ground.getPlace(pos).isDisable())
				positionList.add(pos);
			else
				break;
		}
		
		list = ground.line(position, IGround.Relative_Top, getRange());
		for(Integer pos : list){
			if(!ground.getPlace(pos).isDisable())
				positionList.add(pos);
			else
				break;
		}
		
		list = ground.line(position, IGround.Relative_Left, getRange());
		for(Integer pos : list){
			if(!ground.getPlace(pos).isDisable())
				positionList.add(pos);
			else
				break;
		}
		
		return positionList;
	}

}
