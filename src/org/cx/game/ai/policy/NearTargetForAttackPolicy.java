package org.cx.game.ai.policy;

import org.cx.game.ai.CorpsAgent;
import org.cx.game.ai.policy.formula.Chaochushecheng;
import org.cx.game.ai.policy.formula.MoveableFormula;
import org.cx.game.ai.policy.formula.NotLockFormula;
import org.cx.game.command.Command;
import org.cx.game.command.CommandFactory;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.widget.Ground;

public class NearTargetForAttackPolicy extends AbstractPolicy<CorpsAgent> {

	private String cmdStr = "";
	
	private static NearTargetForAttackPolicy policy = null;
	
	private NearTargetForAttackPolicy() {
		// TODO Auto-generated constructor stub
	}
	
	public static NearTargetForAttackPolicy getInstance(){
		if(null==policy)
			policy = new NearTargetForAttackPolicy();
		
		return policy;
	}
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		super.calculate();
		
		Corps corps = getAgent().getOwner();
		Corps target = getAgent().getAttackTarget();
		
		setPri(AbstractPolicy.PRI_Min);
		
		/*
		 * 不在攻击范围
		 */
		addValidator(new Chaochushecheng(target.getPosition(), corps));
		
		/*
		 * 判断是否被锁定
		 */
		addValidator(new NotLockFormula(corps));
		
		/*
		 * 是否能移动
		 */
		addValidator(new MoveableFormula(corps));
		
		doValidator();

		if(hasError()){
			System.out.println(getErrors().getMessage());
		}else{
			
			Ground ground = corps.getGround();
			Integer position = ground.getPointByWay(corps.getPosition(), target.getPosition(), corps.getMove().getEnergy(), corps.getMove().getType());
			
			this.cmdStr = "move ground place"+position+";";
			
			String cmd = "select ground place"+corps.getPosition()+" corps;";
			try {
				Command command= CommandFactory.getInstance(cmd, corps.getPlayer().getCommandBuffer());
				command.execute();
				
				super.command = CommandFactory.getInstance(this.cmdStr, corps.getPlayer().getCommandBuffer());
				super.command.doValidator();
				if(!super.command.hasError()){
					setPri(AbstractPolicy.PRI_Default);
				}
			} catch (ValidatorException e) {
				// TODO Auto-generated catch block
				return;
			}
		}
	}
}
