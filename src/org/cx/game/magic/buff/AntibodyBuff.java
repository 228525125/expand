package org.cx.game.magic.buff;

import org.cx.game.corps.Corps;
import org.cx.game.intercepter.AbstractIntercepter;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.magic.IMagic;
import org.cx.game.tools.CommonIdentifierE;

/**
 * 抗体，当一个单位接受治疗法术后，在之后的一段时间将对治疗法术免疫
 * @author chenxian
 *
 */
public class AntibodyBuff extends AbstractBuff {
	
	private final static Integer AntibodyBuff_ID = 10350003;
	
	public AntibodyBuff(Integer bout,
			Corps corps) {
		super(AntibodyBuff_ID, bout, corps);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		IIntercepter affectedIn = new AbstractIntercepter() {

			private boolean invoke = true;

			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				IMagic magic = (IMagic) ((Object[]) args[0])[0];
				if(magic.contains(CommonIdentifierE.Func_Cure)){
					invoke = false;
				}
			}

			@Override
			public Boolean isInvoke() {
				// TODO Auto-generated method stub
				return invoke;
			}
			
		};
		recordIntercepter(getOwner().getAffected(), affectedIn);
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		
	}

}
