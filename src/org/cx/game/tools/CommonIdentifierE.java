package org.cx.game.tools;

public class CommonIdentifierE extends CommonIdentifier {
	
	//---------------- Corps ----------------//

	public final static Integer Corps = 1007;   

	public final static Integer Stirps = 1004;   //种族
	public final static Integer Stirps_Human = 171;  //人族
	public final static Integer Stirps_Angel = 173;  //天神
	public final static Integer Stirps_Die = 172;    //亡灵
	public final static Integer Stirps_Daimon = 174;  //恶魔
	public final static Integer Stirps_Beast = 175;   //野兽
	public final static Integer Stirps_Insect = 176;  //昆虫
	public final static Integer Stirps_Plant = 177;   //植物
	public final static Integer Stirps_Machine = 178;  //机械
	public final static Integer Stirps_Dragon = 179;   //龙
	public final static Integer Stirps_Fish = 180;     //鱼
	
	public final static Integer Profession = 1006;     //职业
	public final static Integer Profession_Soldier = 301;  //战士
	public final static Integer Profession_Magic = 302;   //法师
	public final static Integer Profession_Pastor = 303;  //牧师
	public final static Integer Profession_Paladin = 304;  //圣骑士
	public final static Integer Profession_Hunter = 305;   //猎人
	public final static Integer Profession_Thief = 306;    //盗贼
	
	public final static Integer Attack_Mode_Near = 115;   //近战攻击
	public final static Integer Attack_Mode_Far = 116;    //远程攻击
	
	public final static Integer Death_Status_Live = 0;         //战斗
	public final static Integer Death_Status_Death = 1;        //死亡
	public final static Integer Death_Status_Exist = 2;        //存在
	
	public static final Integer Move_Type_Walk = 141;          //步行
	public static final Integer Move_Type_Equitation = 142;    //骑行
	public static final Integer Move_Type_Drive = 143;         //驾驶
	public static final Integer Move_Type_Fly = 144;           //飞行
	
	//--------------- Magic ------------------//
	
	public static final Integer Style_physical = 111;       //物理
	public static final Integer Style_Magic = 112;          //魔法
	
	public static final Integer Func_Astrict = 201;         //移动限制
	public static final Integer Func_Damage = 202;          //直接伤害
	public static final Integer Func_Call = 203;            //召唤
	public static final Integer Func_Cure = 204;            //增益
	public static final Integer Func_Loss = 205;            //损益
	public static final Integer Func_SustainedHarm = 206;   //持续伤害
	public static final Integer Func_Trick = 207;           //陷阱
	public static final Integer Func_Bump = 208;            //冲锋
	public static final Integer Func_Mystery = 209;         //秘术
	public static final Integer Func_Other = 299;           //其他功能
	
	public final static Integer Buff = 1009;
	public static final Integer Type_Benefit = 121;         //受益，不包含系统级的buff
	public static final Integer Type_Harm = 122;            //受损，不包含系统级的buff
	public static final Integer Type_Neutral = 123;         //中性
	
	public final static Integer Skill = 1008;
	
	public final static Integer Trick = 1013;
	
	//---------------- Landform ----------------//
	
	public static final Integer Landform = 1011;
	
	//---------------- Building ----------------//
	
	public static final Integer Building = 1010;
	
	public static final Integer Building_Bridge = 502001;  //桥
	public static final Integer Building_Smithyt = 503001; //铁匠铺
	public static final Integer Building_Hieron = 504001;  //神殿
	public static final Integer Building_Village = 505001; //村庄
	public static final Integer Building_Spatial = 506001; //传送站
	public static final Integer Building_Chengshi = 601001; //城市
	public static final Integer Building_Ganglou = 602001; //岗楼
	public static final Integer Building_Jianta = 603001; //箭塔
	public static final Integer Building_Shijiuta = 604001; //狮鹫塔
	public static final Integer Building_Bingying = 605001; //兵营
	public static final Integer Building_Siyuan = 606001; //寺院
	public static final Integer Building_Mapeng = 607001; //马棚
	public static final Integer Building_Xunlianchang = 608001; //训练场
	
	//---------------- NotifyInfo -----------------//
	
	public final static String Player_Bout = "Player_Bout";

	public final static String Ground_LoadMap = "Ground_LoadMap";
	
	public final static String Place_In = "Place_In";
	public final static String Place_Out = "Place_Out";
	
	public final static String Corps_Attack = "Corps_Attack";
	public final static String Corps_Attacked = "Corps_Attacked";
	public final static String Corps_Conjure = "Corps_Conjure";
	public final static String Corps_Affected = "Corps_Affected";
	public final static String Corps_Activate = "Corps_Activate";
	public final static String Corps_Call = "Corps_Call";
	public final static String Corps_Renew = "Corps_Renew";
	public final static String Corps_Death = "Corps_Death";
	public final static String Corps_Upgrade = "Corps_Upgrade";
	public final static String Corps_Swap = "Corps_Swap";
	public final static String Corps_Dodge = "Corps_Dodge";
	public final static String Corps_Chuck = "Corps_Chuck";
	public final static String Corps_Move = "Corps_Move";
	public final static String Corps_Pick = "Corps_Pick";
	public final static String Treasure_Picked = "Treasure_Picked";
	
	public final static String Corps_Hide = "Corps_Hide";
	public final static String Corps_Hp = "Corps_Hp";
	public final static String Corps_Armour = "Corps_Armour";
	public final static String Corps_Atk = "Corps_Atk";
	public final static String Corps_Def = "Corps_Def";
	public final static String Corps_Speed = "Corps_Speed";
	public final static String Corps_Lock = "Corps_Lock";
	public final static String Corps_Range = "Corps_Range";
	public final static String Corps_Energy = "Corps_Energy";
	public final static String Corps_Mode = "Corps_Mode";
	public final static String Corps_Type = "Corps_Type";
	public final static String Corps_Flee = "Corps_Flee";
	public final static String Corps_Consume = "Corps_Consume";
	public final static String Corps_EmpiricValue = "Corps_EmpiricValue";
	public final static String Corps_SkillCount = "Corps_SkillCount";
	public final static String Corps_Direction = "Corps_Direction";
	
	public final static String Building_Option_Spacing_Process = "Building_Option_Spacing_Process";
	public final static String Building_Option_Execute_Process = "Building_Option_Execute_Process";
	public final static String Building_Upgrade_Product = "Building_Upgrade_Product";
	public final static String Building_Transmit = "Building_Transmit";
	public final static String Building_Receive = "Building_Receive";
	
	public final static String Command_Error = "Command_Error";
	public final static String Command_Select = "Command_Select";
	public final static String Command_Show = "Command_Show";
	public final static String Command_Query_Call = "Command_Query_Call";
	public final static String Command_Query_Move = "Command_Query_Move";
	public final static String Command_Query_Attack = "Command_Query_Attack";
	public final static String Command_Query_Conjure = "Command_Query_Conjure";
	public final static String Command_Query_Swap = "Command_Query_Swap";
	public final static String Command_Query_Apply = "Command_Query_Apply";
	public final static String Command_Query_Execute = "Command_Query_Execute";
	public final static String Command_Query_Pick = "Command_Query_Pick";
	public final static String Command_Reload = "Command_Reload";
	public final static String Command_Switch = "Command_Switch";
	
	public final static String Context_Start = "Context_Start";
	public final static String Context_Deploy = "Context_Deploy";
	public final static String Context_Done = "Context_Done";
	public final static String Context_Finish = "Context_Finish";
}
