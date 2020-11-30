package com.meng;

/**
 * @author: 司徒灵羽
 **/
import com.meng.modules.AimMessage;
import com.meng.modules.BaseModule;
import com.meng.modules.Dice;
import com.meng.modules.DynamicWordStock;
import com.meng.modules.GroupCounterChart;
import com.meng.modules.NumberProcess;
import com.meng.modules.MessageRefuse;
import com.meng.modules.Morning;
import com.meng.modules.Repeater;
import com.meng.modules.Report;
import com.meng.modules.QuestionAndAnswer;
import com.meng.modules.ReflexCommand;
import java.util.NoSuchElementException;
import com.meng.modules.AdminMessage;
import com.meng.modules.MemberChange;

public enum Modules {
    main_switch(null, true),
    recall(null, true),
    AdminMessage(AdminMessage.class, false),
    AimMessage(AimMessage.class, false),
    Dice(Dice.class, true),
    DynamicWordStock(DynamicWordStock.class, true),
    GroupCounterChart(GroupCounterChart.class, true),
    MemberChange(MemberChange.class, true),
    MessageRefuse(MessageRefuse.class, false),
    Morning(Morning.class, true),
    NumberProcess(NumberProcess.class, true),
    QuestionAndAnswer(QuestionAndAnswer.class, true),
    ReflexCommand(ReflexCommand.class, false),
    Repeater(Repeater.class, true),
    Report(Report.class, true);

    private Modules(Class<? extends BaseModule> cls,boolean allowSwitch){
        this.cls = cls;
        this.allowSwitch = allowSwitch;
    }

    private Class<? extends BaseModule> cls;
    private boolean allowSwitch;

    public Class<? extends BaseModule> value(){
        return cls;
    }

    public boolean isAllowSwitch(){
        return allowSwitch;
    }

    public static Modules get(String s){
        for(Modules mds : values()){
            if(mds.toString().equals(s)){
                return mds;
            }
        }
        return null;
    }

    public static <T extends BaseModule> Modules get(T obj){
        for(Modules mos : values()){
            Class<? extends BaseModule> clz = mos.cls;
            if(clz == null){
                continue;
            }
            if(clz == obj.getClass()){
                return mos;
            }
        }
        throw new NoSuchElementException();
    }

    public static <T extends BaseModule> Modules get(Class<? extends BaseModule> obj){
        for(Modules mos : values()){
            if(mos.cls == obj){
                return mos;
            }
        }
        throw new NoSuchElementException();
    }
}
