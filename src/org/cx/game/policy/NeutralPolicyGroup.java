package org.cx.game.policy;

import java.util.List;

public class NeutralPolicyGroup extends PolicyGroup {

	public static final Integer gpId = 10450001;
	
	public NeutralPolicyGroup() {
		super(gpId);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setPolicyList(List<IPolicy> policyList) {
		// TODO Auto-generated method stub 反射需要
		super.setPolicyList(policyList);
	}

}
