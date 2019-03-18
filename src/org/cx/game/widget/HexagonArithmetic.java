package org.cx.game.widget;

import java.util.List;

import org.cx.game.tools.GroundUtil;

public class HexagonArithmetic implements IArithmetic {

	@Override
	public Integer getDirection(Integer stand, Integer target) {
		// TODO Auto-generated method stub
		return GroundUtil.getDirection(stand, target);
	}

	@Override
	public Integer getPosition(Integer stand, Integer direction) {
		// TODO Auto-generated method stub
		return GroundUtil.getPosition(stand, direction);
	}

	@Override
	public List<Integer> twoFlanks(Integer stand, Integer direction) {
		// TODO Auto-generated method stub
		return GroundUtil.twoFlanks(stand, direction);
	}

	@Override
	public List<Integer> rectangle(Integer start, Integer stop) {
		// TODO Auto-generated method stub
		return GroundUtil.rectangle(start, stop);
	}

	@Override
	public List route(Integer start, Integer stop, int[][] MAP, int[] hit) {
		// TODO Auto-generated method stub
		return GroundUtil.route(start, stop, MAP, hit);
	}

}
