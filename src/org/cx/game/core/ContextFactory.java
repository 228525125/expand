package org.cx.game.core;

import org.cx.game.widget.IGround;

public class ContextFactory {
	
	private static IContext context = null;
	
	/**
	 * 该方法必须在createContext被调用后，才有效；
	 * @return
	 */
	public static IContext getContext(){
		return context;
	}

	public static IContext getInstance(IGround ground, IPlayer... players){
		context = new Context(ground, players);

		for(int i=0;i<players.length;i++){
			players[i].setContext(context);
		}

		/*for(final IStrongHold strongHold : player1.getGround().getStrongHoldList()){
			strongHold.getPlayer().setContext(context);
			
			context.addIntercepter(new Intercepter("addBout"){
			
				@Override
				public void after(Object[] args) {
					// TODO Auto-generated method stub
					strongHold.refurbish();
				}
			});
		}*/
		
		return context;
	}
}
