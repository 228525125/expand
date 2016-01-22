package org.cx.game.card.skill;

import java.util.List;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.skill.DizzyBuff;
import org.cx.game.exception.CommandValidatorException;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.Util;
import org.cx.game.validator.IValidator;
import org.cx.game.validator.NeedConjurerValidator;
import org.cx.game.validator.Validator;
import org.cx.game.widget.IGround;

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
	
	private Integer step = 0;
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		LifeCard life = (LifeCard) objects[0];      //life必须相对于owner前后左右四个方向的直线上；
		LifeCard owner = getOwner();
		IGround ground = owner.getPlayer().getGround(); 
		
		BumpValidator validator = new BumpValidator(life, owner, ground);
		doValidator(validator);
		if(hasError())
			throw new RuleValidatorException(getErrors().getMessage());
		
		this.step = validator.getStep();
		
		life.affected(this);
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Integer atkValue = getOwner().getAttack().getAtk()*(100+this.atkScale*this.step)/100;
		Integer dChance = this.dizzyChance*this.step;
		
		LifeCard life = (LifeCard) objects[0];
		life.getDeath().magicToHp(-atkValue);
		
		if(Random.isTrigger(dChance)){
			new DizzyBuff(this.dizzyBout, life).effect();
		}
	}

}
