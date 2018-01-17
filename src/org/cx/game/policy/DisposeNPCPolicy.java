package org.cx.game.policy;

import java.util.List;
import java.util.Set;

import org.cx.game.core.IContext;
import org.cx.game.core.IPlayer;
import org.cx.game.corps.CorpsFactory;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.GroundFactory;
import org.cx.game.widget.IGround;
import org.cx.game.widget.Place;

/**
 * 放置中立部队的NPC
 * @author chenxian
 *
 */
public class DisposeNPCPolicy extends AbstractPolicy {

	private Boolean already = false;
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		super.calculate();
		
		IPlayer owner = (IPlayer) getOwner().getOwner();
		IContext context = owner.getContext();
		setPri(IPolicy.PRI_Min);
		
		if(!already && 1==context.getBout()){
			setPri(IPolicy.PRI_High);
		}
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		this.already = true;
		
		IGround ground = GroundFactory.getGround();
		IPlayer neutral = ground.getNeutral();
		List<String> npcData = ground.getNpcData();
		
		for(String data : npcData){
			String [] datas = data.split(",");
			Integer corpsID = Integer.valueOf(datas[0]);
			Integer position = Integer.valueOf(datas[1]);
			Integer nop = Integer.valueOf(datas[2]);
			Integer policyID = Integer.valueOf(datas[3]);
			
			Corps npc = (Corps) CorpsFactory.getInstance(corpsID, neutral);
			Place place = (Place) ground.getPlace(position);
			IPolicyGroup policy = PolicyGroupFactory.getInstance(policyID);
			
			if(null!=policy)
				npc.setGroupPolicy(policy);
			
			try {
				npc.call(place, nop);
			} catch (RuleValidatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
