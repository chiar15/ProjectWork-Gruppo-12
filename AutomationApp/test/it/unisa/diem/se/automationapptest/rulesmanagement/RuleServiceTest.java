package it.unisa.diem.se.automationapptest.rulesmanagement;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleService;

public class RuleServiceTest {

    private RuleService ruleService;

    @Before
    public void setUp() {
        ruleService = RuleService.getInstance();
        // Assicurati che la lista delle regole sia pulita prima di ogni test
        ruleService.getRuleList().clear();
    }

    @Test
    public void testCreateRuleAndRetrieveList() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", "TriggerEnum.TIMETRIGGER"); // Assicurati che questi valori siano corretti
        triggerData.put("time", "10:00");

        String projectDirectory = System.getProperty("user.dir");
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", "ActionEnum.AUDIOACTION"); // Assicurati che questi valori siano corretti
        actionData.put("filePath", projectDirectory + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav");

        ruleService.createRule("TestRule", triggerData, actionData);

        CopyOnWriteArrayList<Rule> ruleList = ruleService.getRuleList();
        assertNotNull(ruleList);
        assertEquals(1, ruleList.size());

        Rule rule = ruleList.get(0);
        assertEquals("TestRule", rule.getName());
        ruleService.getRuleList().clear();
    }
}
