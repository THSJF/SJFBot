package com.meng.modules.mhy;

import com.meng.modules.mhy.honkai.third.ArmType;
import com.meng.modules.mhy.honkai.third.Attack;
import com.meng.modules.mhy.honkai.third.CharaName;
import com.meng.modules.mhy.honkai.third.HK3Equipment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Honkai3GameData {

    public static final List<HK3Equipment> equipments = Collections.unmodifiableList(new ArrayList<HK3Equipment>(){
            {
                add(new HK3Equipment(CharaName.KianaKaslana, "领域装·白练", ArmType.Machin, Attack.Physics));
                add(new HK3Equipment(CharaName.KianaKaslana, "女武神·游侠", ArmType.Machin, Attack.Physics));
                add(new HK3Equipment(CharaName.KianaKaslana, "天穹游侠", "女武神·游侠", ArmType.Machin, Attack.Physics).setShift());   
                add(new HK3Equipment(CharaName.KianaKaslana, "圣女祈祷", ArmType.Extra, Attack.Physics));  
                add(new HK3Equipment(CharaName.KianaKaslana, "白骑士·月光", ArmType.Biology, Attack.Physics));
                add(new HK3Equipment(CharaName.KianaKaslana, "空之律者", "白骑士·月光", ArmType.Biology, Attack.Physics).setAwaken());
                add(new HK3Equipment(CharaName.KallenKaslana, "圣仪装·今样", "领域装·白练", ArmType.Machin, Attack.Physics).setAwaken());
                add(new HK3Equipment(CharaName.KallenKaslana, "原罪猎人", "女武神·游侠", ArmType.Machin, Attack.Physics).setAwaken());
                add(new HK3Equipment(CharaName.KallenKaslana, "第六夜想曲", "圣女祈祷", ArmType.Extra, Attack.Ice).setAwaken());
                add(new HK3Equipment(CharaName.RaidenMei, "脉冲装·绯红", ArmType.Biology, Attack.Physics)); 
                add(new HK3Equipment(CharaName.RaidenMei, "女武神·强袭", ArmType.Biology, Attack.Physics));
                add(new HK3Equipment(CharaName.RaidenMei, "破晓强袭", "女武神·强袭", ArmType.Biology, Attack.Physics).setShift());
                add(new HK3Equipment(CharaName.RaidenMei, "脉冲装·绯红", ArmType.Biology, Attack.Physics));  
                add(new HK3Equipment(CharaName.RaidenMei, "影舞冲击", ArmType.Machin, Attack.Physics));
                add(new HK3Equipment(CharaName.RaidenMei, "断罪影舞", "影舞冲击", ArmType.Machin, Attack.Physics).setShift());
                add(new HK3Equipment(CharaName.RaidenMei, "雷电女王的鬼铠", ArmType.Extra, Attack.Thunder));
                add(new HK3Equipment(CharaName.RaidenMei, "雷之律者", "雷电女王的鬼铠", ArmType.Extra, Attack.Thunder).setAwaken());
                add(new HK3Equipment(CharaName.YaeSakura, "御神装·勿忘", "影舞冲击", ArmType.Machin, Attack.Ice).setAwaken());
                add(new HK3Equipment(CharaName.YaeSakura, "逆神巫女", "脉冲装·绯红", ArmType.Biology, Attack.Physics).setAwaken());
                add(new HK3Equipment(CharaName.YaeSakura, "真炎幸魂", "女武神·强袭", ArmType.Biology, Attack.Fire).setAwaken());
                add(new HK3Equipment(CharaName.YaeSakura, "夜隐重霞", ArmType.Biology, Attack.Thunder).setSp());
                add(new HK3Equipment(CharaName.BronyaZaychik, "女武神·战车", ArmType.Extra, Attack.Physics));
                add(new HK3Equipment(CharaName.BronyaZaychik, "驱动装·山吹", ArmType.Extra, Attack.Physics));
                add(new HK3Equipment(CharaName.BronyaZaychik, "彗星驱动", "驱动装·山吹", ArmType.Extra, Attack.Physics).setShift());
                add(new HK3Equipment(CharaName.BronyaZaychik, "银狼的黎明", "驱动装·山吹", ArmType.Extra, Attack.Physics).setAwaken());
                add(new HK3Equipment(CharaName.BronyaZaychik, "雪地狙击", ArmType.Biology, Attack.Physics));
                add(new HK3Equipment(CharaName.BronyaZaychik, "异度黒核侵蚀", "雪地狙击", ArmType.Biology, Attack.Physics).setAwaken());
                add(new HK3Equipment(CharaName.BronyaZaychik, "次元边界突破", ArmType.Machin, Attack.Physics));
                add(new HK3Equipment(CharaName.BronyaZaychik, "理之律者", "次元边界突破", ArmType.Machin, Attack.Ice).setAwaken());
                add(new HK3Equipment(CharaName.BronyaZaychik, "迷城骇兔", ArmType.Extra, Attack.Physics).setSp());
                add(new HK3Equipment(CharaName.MurataHimeko, "女武神·凯旋", ArmType.Biology, Attack.Physics));
                add(new HK3Equipment(CharaName.MurataHimeko, "融核装·深红", ArmType.Machin, Attack.Thunder));
                add(new HK3Equipment(CharaName.MurataHimeko, "真红骑士·月蚀", "融核装·深红", ArmType.Machin, Attack.Fire).setAwaken());
                add(new HK3Equipment(CharaName.MurataHimeko, "极地战刃", ArmType.Extra, Attack.Ice));
                add(new HK3Equipment(CharaName.MurataHimeko, "战场疾风", "极地战刃", ArmType.Biology, Attack.Physics).setAwaken());
                add(new HK3Equipment(CharaName.MurataHimeko, "血色玫瑰", ArmType.Extra, Attack.Fire));
                add(new HK3Equipment(CharaName.TheresaApocalypse, "女武神·誓约", ArmType.Extra, Attack.Thunder));
                add(new HK3Equipment(CharaName.TheresaApocalypse, "月下初拥", "女武神·誓约", ArmType.Extra, Attack.Physics).setAwaken());
                add(new HK3Equipment(CharaName.TheresaApocalypse, "处刑装·紫苑", ArmType.Machin, Attack.Physics));
                add(new HK3Equipment(CharaName.TheresaApocalypse, "暮光骑士·月煌", "处刑装·紫苑", ArmType.Machin, Attack.Physics).setShift());
                add(new HK3Equipment(CharaName.TheresaApocalypse, "朔夜观星", ArmType.Biology, Attack.Physics).setSp());
                add(new HK3Equipment(CharaName.TheresaApocalypse, "樱火轮舞", ArmType.Extra, Attack.Fire));
                add(new HK3Equipment(CharaName.TheresaApocalypse, "神恩颂歌", ArmType.Biology, Attack.Physics));
                add(new HK3Equipment(CharaName.FuHua, "白夜执事", ArmType.Biology, Attack.Physics));
                add(new HK3Equipment(CharaName.FuHua, "女武神·迅羽", ArmType.Extra, Attack.Physics));
                add(new HK3Equipment(CharaName.FuHua, "雾都迅羽", "女武神·迅羽", ArmType.Extra, Attack.Physics).setShift());
                add(new HK3Equipment(CharaName.FuHua, "影骑士·月轮", ArmType.Machin, Attack.Thunder));
                add(new HK3Equipment(CharaName.FuHua, "识之律者", "影骑士·月轮", ArmType.Biology, Attack.Physics).setAwaken());
                add(new HK3Equipment(CharaName.FuHua, "炽翎", ArmType.Extra, Attack.Fire));
                add(new HK3Equipment(CharaName.FuHua, "云墨丹心", "炽翎", ArmType.Extra, Attack.Fire).setAwaken());
                add(new HK3Equipment(CharaName.RitaRossweisse, "黯蔷薇", ArmType.Extra, Attack.Physics));
                add(new HK3Equipment(CharaName.RitaRossweisse, "猎袭装·影铁", ArmType.Machin, Attack.Thunder));
                add(new HK3Equipment(CharaName.RitaRossweisse, "苍骑士·月魂", ArmType.Biology, Attack.Ice));
                add(new HK3Equipment(CharaName.RitaRossweisse, "失落迷迭", ArmType.Quantum, Attack.Thunder));
                add(new HK3Equipment(CharaName.RozaliyaOlenyeva, "樱桃炸弹", ArmType.Extra, Attack.Physics));
                add(new HK3Equipment(CharaName.RozaliyaOlenyeva, "狂热蓝调△", "樱桃炸弹", ArmType.Extra, Attack.Physics).setShift());
                add(new HK3Equipment(CharaName.LiliyaOlenyeva, "蓝莓特攻", ArmType.Machin, Attack.Physics));
                add(new HK3Equipment(CharaName.SeeleVollerei, "幻海梦蝶", ArmType.Quantum, Attack.Physics));
                add(new HK3Equipment(CharaName.SeeleVollerei, "彼岸双生", ArmType.Quantum, Attack.Physics));
                add(new HK3Equipment(CharaName.SeeleVollerei, "魇夜星渊", ArmType.Quantum, Attack.Ice));
                add(new HK3Equipment(CharaName.Durandal, "女武神·荣光", ArmType.Quantum, Attack.Fire));
                add(new HK3Equipment(CharaName.Durandal, "辉骑士·月魄", ArmType.Machin, Attack.Physics));
                add(new HK3Equipment(CharaName.Durandal, "不灭星锚", ArmType.Biology, Attack.Fire));
                add(new HK3Equipment(CharaName.Asuka, "明日福音", ArmType.Biology, Attack.Physics).setSp());
                add(new HK3Equipment(CharaName.Fischl, "断罪皇女！！", ArmType.Biology, Attack.Thunder).setSp());
            }
        });

    public static final List<String> stage = Collections.unmodifiableList(new ArrayList<String>(){
            {
                add("主线");
                add("深渊");
                add("开放世界");
                add("记忆战场");
                add("万象虚境");
                add("模拟作战室");
                add("材料活动");
                add("编年史");
                add("深层档案");
            } 
        });

    public static HK3Equipment[] getEquipmentByCharaName(String charaName) {
        List<HK3Equipment> result = new ArrayList<>();
        for (HK3Equipment hkc : equipments) {
            CharaName name = hkc.getCharaName();
            if (name.toString().contains(charaName) || name.value().contains(charaName)) {
                result.add(hkc);
            }
        }
        return result.toArray(new HK3Equipment[0]);
    }

    public static HK3Equipment getEquipmentByName(String equipmentName) {
        for (HK3Equipment hkc : equipments) {
            if (hkc.getEquipmentName().contains(equipmentName)) {
                return hkc;
            }
        }
        return null;
    }
}
