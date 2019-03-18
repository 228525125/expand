package org.cx.game.ai.policy;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.ai.CorpsAgent;
import org.cx.game.ai.policy.formula.AttackableFormula;
import org.cx.game.ai.policy.formula.LockFormula;
import org.cx.game.ai.policy.formula.ShechengneidedirenFormula;
import org.cx.game.command.Command;
import org.cx.game.command.CommandFactory;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;

public class AttackPolicy extends AbstractPolicy<CorpsAgent> {

	private String cmdStr = "";
	
	private static AttackPolicy policy = null;
	
	private AttackPolicy() {
		// TODO Auto-generated constructor stub
	}
	
	public static AttackPolicy getInstance(){
		if(null==policy)
			policy = new AttackPolicy();
		
		return policy;
	}
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		super.calculate();
		
		Corps corps = getAgent().getOwner();
		
		setPri(AbstractPolicy.PRI_Min);
		
		/*
		 * 是否可发动攻击
		 */
		AttackableFormula af = new AttackableFormula(corps);
		try {
			doValidator(af);
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			return ;
		}
		
		
		/*
		 * 判断在攻击范围内，是否有敌人
		 */
		ShechengneidedirenFormula scFormula = new ShechengneidedirenFormula(corps);
		try {
			doValidator(scFormula);
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			return ;
		}
		
		
		/*
		 * 判断是否被锁定
		 */
		List<Corps> lockerList = new ArrayList<Corps>();
		
		LockFormula lockFormula = new LockFormula(corps);
		Boolean isLock = null;
		try {
			doValidator(lockFormula);
			isLock = true;
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			isLock = false;
		}
		
		
		
		if(isLock){       //没有被锁，搜索射程范围内的最近的敌人
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
		Corps corps = getAgent().getOwner();
		String cmd = "select ground place"+corps.getPosition()+" corps;";
		try {
			Command command= CommandFactory.getInstance(cmd, corps.getPlayer().getCommandBuffer());
			command.execute();
			
			super.command = CommandFactory.getInstance(this.cmdStr, corps.getPlayer().getCommandBuffer());
			super.command.doValidator();
			if(!super.command.hasError()){
				setPri(AbstractPolicy.PRI_High);
			}
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			return;
		}
	}
}
