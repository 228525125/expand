package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.magic.IMagic;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

/**
 * 受到法术影响
 * @author chenxian
 *
 */
public class Affected extends AbstractAction implements IAction {
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	public void magicHarm(Integer harm) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void action(Object...objects) {
		// TODO Auto-generated method stub
		
		IMagic magic = (IMagic) objects[0];
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("card", getOwner());
		map.put("position", getOwner().getPosition());
		map.put("magic", magic);
		String desc = getOwner().getName()+" 【受到】 "+magic.getName()+" 效果影响！";
		map.put("description", desc);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Affected,map);
		super.notifyObservers(info);
		
		if(magic.isTrigger(objects))
			magic.affect(getOwner());
	}

}
