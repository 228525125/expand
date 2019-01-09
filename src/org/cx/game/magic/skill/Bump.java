package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.DizzyBuff;
import org.cx.game.tools.Util;
import org.cx.game.validator.CorpsMoveableValidator;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Ground;

/**
 * 标准技能，冲撞，一般为骑兵主要技能
 * @author chenxian
 *
 */
public class Bump extends ActiveSkill {

	public final static Integer Bump_ID = 10200009;
	
	private Integer atkScale = 100;
	private Integer dizzyBout = 1;
	
	private BumpOption bumpOption = null;
	
	/**
	 * 
	 * @param cooldown 冷却周期
	 * @param range 范围
	 * @param dizzyBout 击晕回合
	 */
	public Bump(Integer id, Integer cooldown, String range, Integer atkScale, Integer dizzyBout) {
		super(id, cooldown, 0);
		// TODO Auto-generated constructor stub
		super.setRange(range);
		this.atkScale = atkScale;
		this.dizzyBout = dizzyBout;
	}
	
	@Override
	public void afterConstruct() {
		// TODO Auto-generated method stub
		this.bumpOption = new BumpOption(this);
		addOption(bumpOption);
	}
	
	@Override
	public void setOwner(AbstractCorps corps) {
		// TODO Auto-generated method stub
		super.setOwner(corps);
		
		/*
		 * 使用技能必须保证Corps可移动
		 */
		this.bumpOption.addValidator(new CorpsMoveableValidator((Corps) corps));
	}

	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Corps corps = (Corps) objects[0];
		Corps owner = (Corps) getOwner();
		
		Ground ground = (Ground) owner.getGround();
		Integer pos = ground.getPositionOnLineByTarget(owner.getPosition(), corps.getPosition());
		ground.translocation(owner, pos);
		owner.setPosition(pos);
		
		Integer atk = owner.getAttack().getAtk();
		Integer harm = atk * this.atkScale / 100;
		
		if(harmToCorps(harm, corps))
			new DizzyBuff(this.dizzyBout, corps).effect();
		
	}
	
	public class BumpOption extends ConjureToCorpsOption {

		public BumpOption(ActiveSkill skill) {
			super(skill);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public List<Integer> getExecuteRange() {
			// TODO Auto-generated method stub
			List<Integer> positionList = new ArrayList<Integer>();
			ActiveSkill as = (ActiveSkill) getOwner();
			AbstractCorps corps = as.getOwner();
			Ground g = (Ground) corps.getGround();
			Integer position = corps.getPosition();
			Integer begin = Integer.valueOf(as.getRange().split("-")[0]);
			Integer step = Integer.valueOf(as.getRange().split("-")[1]);
			List<Integer> leftList = g.getLine(position, AbstractGround.Relative_Left, step, true, corps.getMoveType());
			
			if(!leftList.isEmpty()){
				Integer p = leftList.get(leftList.size()-1);
				p = g.getPosition(p, AbstractGround.Relative_Left);
				if(null!=p && null!=g.getCorps(p) && !g.getCorps(p).getPlayer().equals(getOwner().getOwner().getPlayer()))
					leftList.add(p);
			}
			for(int i=begin;i<=leftList.size();++i){
				positionList.add(leftList.get(i-1));
			}
			
			List<Integer> leftBottomList = g.getLine(position, AbstractGround.Relative_LeftBottom, step, true, corps.getMoveType());
			if(!leftBottomList.isEmpty()){
				Integer p = leftBottomList.get(leftBottomList.size()-1);
				p = g.getPosition(p, AbstractGround.Relative_LeftBottom);
				if(null!=p && null!=g.getCorps(p) && !g.getCorps(p).getPlayer().equals(getOwner().getOwner().getPlayer()))
					leftBottomList.add(p);
			}
			for(int i=begin;i<=leftBottomList.size();++i){
				positionList.add(leftBottomList.get(i-1));
			}
			
			List<Integer> rightBottomList = g.getLine(position, AbstractGround.Relative_RightBottom, step, true, corps.getMoveType());
			if(!rightBottomList.isEmpty()){
				Integer p = rightBottomList.get(rightBottomList.size()-1);
				p = g.getPosition(p, AbstractGround.Relative_RightBottom);
				if(null!=p && null!=g.getCorps(p) && !g.getCorps(p).getPlayer().equals(getOwner().getOwner().getPlayer()))
					rightBottomList.add(p);
			}
			for(int i=begin;i<=rightBottomList.size();++i){
				positionList.add(rightBottomList.get(i-1));
			}
			
			List<Integer> rightList = g.getLine(position, AbstractGround.Relative_Right, step, true, corps.getMoveType());
			if(!rightList.isEmpty()){
				Integer p = rightList.get(rightList.size()-1);
				p = g.getPosition(p, AbstractGround.Relative_Right);
				if(null!=p && null!=g.getCorps(p) && !g.getCorps(p).getPlayer().equals(getOwner().getOwner().getPlayer()))
					rightList.add(p);
			}
			for(int i=begin;i<=rightList.size();++i){
				positionList.add(rightList.get(i-1));
			}
			
			List<Integer> rightTopList = g.getLine(position, AbstractGround.Relative_RightTop, step, true, corps.getMoveType());
			if(!rightTopList.isEmpty()){
				Integer p = rightTopList.get(rightTopList.size()-1);
				p = g.getPosition(p, AbstractGround.Relative_RightTop);
				if(null!=p && null!=g.getCorps(p) && !g.getCorps(p).getPlayer().equals(getOwner().getOwner().getPlayer()))
					rightTopList.add(p);
			}
			for(int i=begin;i<=rightTopList.size();++i){
				positionList.add(rightTopList.get(i-1));
			}
			
			List<Integer> leftTopList = g.getLine(position, AbstractGround.Relative_LeftTop, step, true, corps.getMoveType());
			if(!leftTopList.isEmpty()){
				Integer p = leftTopList.get(leftTopList.size()-1);
				p = g.getPosition(p, AbstractGround.Relative_LeftTop);
				if(null!=p && null!=g.getCorps(p) && !g.getCorps(p).getPlayer().equals(getOwner().getOwner().getPlayer()))
					leftTopList.add(p);
			}
			for(int i=begin;i<=leftTopList.size();++i){
				positionList.add(leftTopList.get(i-1));
			}

			return positionList;
		}

	}

}
