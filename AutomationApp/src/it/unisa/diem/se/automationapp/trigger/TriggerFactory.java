/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import it.unisa.diem.se.automationapp.trigger.triggercreation.DateTriggerCreation;
import it.unisa.diem.se.automationapp.trigger.triggercreation.DayOfMonthTriggerCreation;
import it.unisa.diem.se.automationapp.trigger.triggercreation.DayOfWeekTriggerCreation;
import it.unisa.diem.se.automationapp.trigger.triggercreation.FileDimensionTriggerCreation;
import it.unisa.diem.se.automationapp.trigger.triggercreation.FileExistsTriggerCreation;
import it.unisa.diem.se.automationapp.trigger.triggercreation.TimeTriggerCreation;
import it.unisa.diem.se.automationapp.trigger.triggercreation.TriggerCreationStrategy;
import java.util.HashMap;
import java.util.Map;

public class TriggerFactory {
    private static Map<String, TriggerCreationStrategy> strategies = new HashMap<>();

    static {
        strategies.put(TriggerType.TIME.toString(), new TimeTriggerCreation());
        strategies.put(TriggerType.DAYOFWEEK.toString(), new DayOfWeekTriggerCreation());
        strategies.put(TriggerType.DAYOFMONTH.toString(), new DayOfMonthTriggerCreation());
        strategies.put(TriggerType.DATE.toString(), new DateTriggerCreation());
        strategies.put(TriggerType.FILEEXISTS.toString(), new FileExistsTriggerCreation());
        strategies.put(TriggerType.FILEDIMENSION.toString(), new FileDimensionTriggerCreation());
        
    }

    public static TriggerInterface createTrigger(Map<String, String> triggerData) {
        String type = triggerData.get("type");
        TriggerCreationStrategy strategy = strategies.get(type);

        if (strategy != null) {
            return strategy.createTrigger(triggerData);
        } else {
            throw new IllegalArgumentException("Invalid action type: " + type);
        }
    }
}