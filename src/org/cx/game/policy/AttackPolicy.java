package org.cx.game.policy;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.command.Command;
import org.cx.game.command.CommandFactory;
import org.cx.game.command.Invoker;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.policy.IPolicy;
import org.cx.game.policy.formula.AttackableFormula;
import org.cx.game.policy.formula.LockFormula;
import org.cx.game.policy.formula.ShechengneidedirenFormula;
import org.cx.game.policy.formula.StagnantFormula;

/**
 * 攻击策略
 * @author chenxian
 *
 */
public class AttackPolicy extends AbstractPolicy {
	
	private String cmdStr = "";
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		super.calculate();
		
		Corps owner = (Corps) getOwner().getOwner();
		
		setPri(IPolicy.PRI_Min);
		
		/*
		 * 是否可发动攻击
		 */
		AttackableFormula af = new AttackableFormula(owner);
		doValidator(af);
		if(hasError())
			return ;
		
		/*
		 * 判断在攻击范围内，是否有敌人
		 */
		ShechengneidedirenFormula scFormula = new ShechengneidedirenFormula(owner);
		doValidator(scFormula);
		
		if(hasError())
			return;
		
		/*
		 * 判断是否被锁定
		 */
		List<Corps> lockerList = new ArrayList<Corps>();
		
		LockFormula lockFormula = new LockFormula(owner);
		doValidator(lockFormula);
		if(hasError()){       //没有被锁，搜索射程范围内的最近的敌人
			Corps enemy = scFormula.getNearEnemy();
			this.cmdStr = "attack ground place"+enemy.getPosition()+" corps";
			
			validator();
		}else{              //已经被锁，就在锁定的敌人中随机找
			lockerList = lockFormula.getLockerList();
			Corps locker = lockerList.get(0);
			this.cmdStr = "attack ground place"+locker.getPosition()+" corps";
			
			validator();
		}
	}
	
	private void validator(){
		Corps owner = (Corps) getOwner().getOwner();
		String cmd = "select ground place"+owner.getPosition()+" corps;";
		try {
			Command command= CommandFactory.getInstance(owner.getPlayer(),cmd);
			command.execute();
			
			super.command = CommandFactory.getInstance(owner.getPlayer(),this.cmdStr);
			super.command.doValidator();
			if(!super.command.hasError()){
				setPri(IPolicy.PRI_High);
			}
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			return;
		}
	}
}
