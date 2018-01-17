package org.cx.game.magic.buff;

import org.cx.game.corps.Corps;

public class ZengjiagongjiBuff extends AbstractBuff {

	public final static Integer ZengjiagongjiBuff_ID = 10300002;
	
	public ZengjiagongjiBuff(Integer bout, Integer atk, Corps corps) {
		super(ZengjiagongjiBuff_ID, bout, corps);
		// TODO Auto-generated constructor stub
		
		setAtk(atk);
	}
}
