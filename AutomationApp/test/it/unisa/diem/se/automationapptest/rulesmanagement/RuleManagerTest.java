package it.unisa.diem.se.automationapptest.rulesmanagement;

import it.unisa.diem.se.automationapp.action.ActionEnum;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleManager;
import it.unisa.diem.se.automationapp.trigger.TriggerEnum;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RuleManagerTest {

    private RuleManager ruleManager;
    private Map<String, String> triggerData;
    private Map<String, String> actionData;

    @Before
    public void setUp() {
        ruleManager = RuleManager.getInstance();
        ruleManager.getRuleList().clear();
        ruleManager.getExecutionQueue().clear();
        triggerData = new HashMap<>();
        triggerData.put("type", TriggerEnum.TIMETRIGGER.name());
        triggerData.put("time", "10:00");
        String projectDirectory = System.getProperty("user.dir");
        actionData = new HashMap<>();
        actionData.put("type", ActionEnum.AUDIOACTION.name());
        actionData.put("filePath", projectDirectory + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav");
    }

@Test
    public void testCreateRuleAndRetrieveList() {

        Rule createdRule = ruleManager.createRule("TestRule", triggerData, actionData);

        assertNotNull(createdRule);
        assertEquals("TestRule", createdRule.getName());

        CopyOnWriteArrayList<Rule> ruleList = ruleManager.getRuleList();
        assertNotNull(ruleList);
        assertEquals(1, ruleList.size());
        assertSame(createdRule, ruleList.get(0));
    }

    @Test
    public void testQueueOfferAndPoll() {

        Rule rule = ruleManager.createRule("QueueTestRule", triggerData, actionData);
        ruleManager.queueOffer(rule);

        ConcurrentLinkedQueue<Rule> executionQueue = ruleManager.getExecutionQueue();
        assertNotNull("La coda di esecuzione non dovrebbe essere null", executionQueue);
        assertFalse("La coda di esecuzione non dovrebbe essere vuota", executionQueue.isEmpty());

        Rule polledRule = ruleManager.queuePoll();
        assertSame("La regola recuperata dalla coda dovrebbe essere la stessa che è stata inserita", rule, polledRule);
        assertTrue("La coda di esecuzione dovrebbe essere vuota dopo il poll", executionQueue.isEmpty());
    }
}
