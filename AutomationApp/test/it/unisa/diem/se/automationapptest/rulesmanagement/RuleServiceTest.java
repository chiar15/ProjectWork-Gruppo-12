package it.unisa.diem.se.automationapptest.rulesmanagement;

import it.unisa.diem.se.automationapp.action.ActionEnum;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleService;
import it.unisa.diem.se.automationapp.trigger.TriggerEnum;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class RuleServiceTest {

    private RuleService ruleService;

    @Before
    public void setUp() {
        ruleService = RuleService.getInstance();
        ruleService.getRuleList().clear();
    }

    @Test
    public void testCreateRuleAndRetrieveList() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", TriggerEnum.TIMETRIGGER.name());
        triggerData.put("time", "10:00");

        String projectDirectory = System.getProperty("user.dir");
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", ActionEnum.AUDIOACTION.name());
        actionData.put("filePath", projectDirectory + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav");

        Rule createdRule = ruleService.createRule("TestRule", triggerData, actionData);

        assertNotNull(createdRule);
        assertEquals("TestRule", createdRule.getName());

        CopyOnWriteArrayList<Rule> ruleList = ruleService.getRuleList();
        assertNotNull(ruleList);
        assertEquals(1, ruleList.size());
        assertSame(createdRule, ruleList.get(0));
    }
}
