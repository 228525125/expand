package org.cx.game.magic.skill;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.core.Player;
import org.cx.game.corps.Corps;
import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.TauntBuff;
import org.cx.game.widget.Ground;

/**
 * 嘲讽，这个类只是一个标记，嘲讽的逻辑写在AttackLock、AttackLockBuff、AttackTauntValidator、MoveTauntValidator、AttackCommand、MoveCommand中
 * 1、嘲讽触发后，使受影响的随从带上一个嘲讽buff，并且锁定嘲讽随从；
 * 2、当随从攻击具有嘲讽的敌人时，它不受锁定的影响；
 * 3、当随从被嘲讽时，无法攻击其他目标，只能攻击具有嘲讽的随从；
 * 4、嘲讽效果只能在近身时有效；
 * @author chenxian
 *
 */ 
public class TauntActive extends ActiveSkill {

	private final static Integer Taunt_ID = 10200006;
	
	public TauntActive(Integer id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	private List<Corps> getAffectedList() {
		List<Corps> ls = new ArrayList<Corps>();
		Player player = getOwner().getPlayer();
		Ground ground = player.getContext().getGround();
		Integer position = getOwner().getPosition();
		List<Integer> list = ground.areaForDistance(position, getRange(), Ground.Contain);
		list.remove(position);
		
		for(Integer p : list){
			Corps corps = (Corps) ground.getCorps(p);
			
			if(null==corps){
				continue;
			}
			
			if(corps.getPlayer().equals(player)){
				continue;
			}
			
			ls.add(corps);
			break;
		}
		
		return ls;
	}
	
	@Override
	public void useSkill(Object... objects) {
		// TODO Auto-generated method stub

		List<Corps> ls = getAffectedList();
		
		for(Corps corps : ls){
			corps.affected(this);
		}
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Corps corps = (Corps) objects[0];
		new TauntBuff((Corps) getOwner(),corps).effect();
	}
	
	@Override
	public void setCooldown(Integer cooldown) {
		// TODO Auto-generated method stub
		super.setCooldown(cooldown);
	}

	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
}
