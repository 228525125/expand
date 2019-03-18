package org.cx.game.command;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.core.AbstractHost;
import org.cx.game.core.HostManager;
import org.cx.game.core.SceneHost;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

public class JoinCommand extends Command {

	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		//addValidator(new HostVacancyValidator(parameter.toString()));
		
		super.execute();
		
		String SPACE = " ";
		String account = parameter.toString().split(SPACE)[0];
		String hostName = parameter.toString().split(SPACE)[1];
		String playNo = HostManager.getPlayNoByHostName(hostName);
		SceneHost host = (SceneHost) HostManager.joinGame(playNo, account);
		Integer troop = host.getTroopForAccount(account);
		
		host.setCorpsDataOfTroop(troop, "[10100009,1,"+troop+"];[10100009,1,"+troop+"]");
		host.setStatus(AbstractHost.Status_WaitReady);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("playNo", playNo);
		map.put("troop", troop);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Command_JoinHost,map);
		super.notifyObservers(info);
	}
}
