package org.cx.game.policy;

import org.cx.game.command.Command;
import org.cx.game.command.CommandFactory;
import org.cx.game.command.Invoker;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.policy.IPolicy;
import org.cx.game.policy.formula.ChujifanweineidedirenFormula;
import org.cx.game.policy.formula.NotLockFormula;
import org.cx.game.policy.formula.NotStagnantFormula;
import org.cx.game.policy.formula.StagnantFormula;

/**
 * 出击策略
 * @author chenxian
 *
 */
public class SallyPolicy extends AbstractPolicy {

	private Integer guardPosition = null;
	private String cmdStr = "";
	
	public void setGuardPosition(Integer guardPosition){
		this.guardPosition = guardPosition;
	}
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		super.calculate();
		
		Corps owner = (Corps) getOwner().getOwner();
		
		setPri(IPolicy.PRI_Min);
		
		/*
		 * 判断是否被锁
		 */
		NotLockFormula notLockFormula = new NotLockFormula(owner);
		doValidator(notLockFormula);
		if(hasError())
			return ;
		
		/*
		 * 是否在原点
		 */
		StagnantFormula sf = new StagnantFormula(owner, this.guardPosition);
		doValidator(sf);
		if(hasError())
			return ;
		
		/*
		 * 出击范围内是否有敌人
		 */
		ChujifanweineidedirenFormula cf = new ChujifanweineidedirenFormula(owner);
		doValidator(cf);
		if(hasError())
			return ;
		
		Integer pos = cf.getPosition();
		this.cmdStr = "move ground place"+pos+";";
		
		validator();
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
