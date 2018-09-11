package org.cx.game.widget;

import java.util.List;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;

public class SceneFactory {

	public static Ground createScene(Integer mapId, Corps corps1, Corps corps2) {
		Ground ground = (Ground) GroundFactory.getInstance(mapId);
		
		AbstractPlayer player1 = corps1.getPlayer();
		AbstractPlayer player2 = corps2.getPlayer();
		List<Integer> entranceList1 = ground.getEntranceList(player1.getTroop());
		List<Integer> entranceList2 = ground.getEntranceList(player2.getTroop());
		List<AbstractCorps> teammateList1 = corps1.getTeammateList();
		List<AbstractCorps> teammateList2 = corps2.getTeammateList();
		
		ground.placementCorps(entranceList1.get(0), corps1);
		
		for(int i=1;i<entranceList1.size() && teammateList1.size()>=i;i++) {
			AbstractPlace place = ground.getPlace(entranceList1.get(i));
			AbstractCorps teammate = teammateList1.get(i-1);
			teammate.leave(place);
		}
		
		ground.placementCorps(entranceList2.get(0), corps2);
		
		for(int i=1;i<entranceList2.size() && teammateList2.size()>=i;i++) {
			AbstractPlace place = ground.getPlace(entranceList2.get(i));
			AbstractCorps teammate = teammateList2.get(i-1);
			teammate.leave(place);
		}
		
		return ground;
	}
}
