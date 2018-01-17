package org.cx.game.policy;

import java.util.List;

import org.cx.game.corps.Corps;
import org.cx.game.intercepter.AbstractIntercepter;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.widget.Place;

/**
 * 原地驻守
 * @author chenxian
 *
 */
public class GuardPolicyGroup extends PolicyGroup {

	private Integer guardPosition = null;
	
	public static final Integer gpId = 10400001;
	
	private IIntercepter callIn = null;
	
	public GuardPolicyGroup() {
		// TODO Auto-generated constructor stub
		super(gpId);
		
		this.callIn = new AbstractIntercepter() {
			
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				/*
				 * 设置固守位置
				 */
				Place place = (Place)((Object[]) args[0])[0];
				Integer position = place.getPosition();
				setGuardPosition(position);
			}
			
			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				Corps owner = (Corps) getOwner();
				owner.getCall().deleteIntercepter(callIn);
			}
		};
	}
	
	public void setGuardPosition(Integer gp) {
		// TODO Auto-generated method stub
		this.guardPosition = gp;
		for(IPolicy policy : policyList){
			if (policy instanceof RunbackPolicy) {
				RunbackPolicy rp = (RunbackPolicy) policy;
				rp.setGuardPoistion(this.guardPosition);
			}
			
			if (policy instanceof SallyPolicy) {
				SallyPolicy sp = (SallyPolicy) policy;
				sp.setGuardPosition(this.guardPosition);
			}
		}
	}
	
	@Override
	public void setPolicyList(List<IPolicy> policyList) {
		// TODO Auto-generated method stub 反射需要
		super.setPolicyList(policyList);
	}
	
	@Override
	public void setOwner(Object owner) {
		// TODO Auto-generated method stub
		super.setOwner(owner);
		
		Corps corps = (Corps) owner;
		corps.getCall().addIntercepter(callIn);
	}
}
