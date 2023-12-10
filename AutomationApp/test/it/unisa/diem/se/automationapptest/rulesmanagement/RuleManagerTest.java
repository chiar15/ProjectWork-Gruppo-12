package it.unisa.diem.se.automationapptest.rulesmanagement;

import it.unisa.diem.se.automationapp.action.ActionType;
import it.unisa.diem.se.automationapp.action.AudioAction;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleManager;
import it.unisa.diem.se.automationapp.trigger.TimeTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerType;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
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
        triggerData.put("type", TriggerType.TIME.toString());
        triggerData.put("time", "10:00");
        String projectDirectory = System.getProperty("user.dir");
        actionData = new HashMap<>();
        actionData.put("type", ActionType.AUDIO.toString());
        actionData.put("filePath", projectDirectory + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav");
    }

    @Test
    public void testCreateRuleAndRetrieveList() {
        Rule createdRule = ruleManager.createRule("TestRule", triggerData, actionData, 0);

        assertNotNull(createdRule);
        assertEquals("TestRule", createdRule.getName());

        CopyOnWriteArrayList<Rule> ruleList = ruleManager.getRuleList();
        assertNotNull(ruleList);
        assertEquals(1, ruleList.size());
        assertSame(createdRule, ruleList.get(0));
    }

    @Test
    public void testQueueOfferAndPoll() {
        Rule rule = ruleManager.createRule("QueueTestRule", triggerData, actionData, 0);
        ruleManager.queueOffer(rule);

        ConcurrentLinkedQueue<Rule> executionQueue = ruleManager.getExecutionQueue();
        assertNotNull("La coda di esecuzione non dovrebbe essere null", executionQueue);
        assertFalse("La coda di esecuzione non dovrebbe essere vuota", executionQueue.isEmpty());

        Rule polledRule = ruleManager.queuePoll();
        assertSame("La regola recuperata dalla coda dovrebbe essere la stessa che è stata inserita", rule, polledRule);
        assertTrue("La coda di esecuzione dovrebbe essere vuota dopo il poll", executionQueue.isEmpty());
    }
    
    @Test
    public void testDeleteRule() {
        Rule testRule = ruleManager.createRule("DeleteTestRule", triggerData, actionData, 0);

        assertNotNull("La regola creata non dovrebbe essere null", testRule);
        assertTrue("La lista delle regole dovrebbe contenere la regola creata", 
                   ruleManager.getRuleList().contains(testRule));

        ruleManager.queueOffer(testRule);
        assertTrue("La coda di esecuzione dovrebbe contenere la regola inserita",
                   ruleManager.getExecutionQueue().contains(testRule));

        ruleManager.deleteRule(testRule);

        assertFalse("La lista delle regole non dovrebbe più contenere la regola rimossa", 
                    ruleManager.getRuleList().contains(testRule));
        assertFalse("La coda di esecuzione non dovrebbe più contenere la regola rimossa", 
                    ruleManager.getExecutionQueue().contains(testRule));
    }

    @Test
    public void testDoesRuleNameExist() {
        ruleManager.createRule("ExistingRule", triggerData, actionData, 0);
        assertTrue("Il nome della regola dovrebbe esistere", ruleManager.doesRuleNameExist("ExistingRule"));
        assertFalse("Un nome di regola non esistente non dovrebbe esistere", ruleManager.doesRuleNameExist("NonExistingRule"));
    }

    @Test
    public void testCreateSuspendedRule() {
        Rule suspendedRule = ruleManager.createRule("SuspendedRule", triggerData, actionData, 1000);
        assertNotNull("La regola sospesa non dovrebbe essere null", suspendedRule);
    }
    
    @Test
    public void testQueueContainsRule() {
        Rule rule = ruleManager.createRule("ContainTestRule", triggerData, actionData, 0);
        assertFalse("La coda di esecuzione non dovrebbe contenere la regola prima dell'inserimento", 
                    ruleManager.queueContainsRule(rule));
        
        ruleManager.queueOffer(rule);
        assertTrue("La coda di esecuzione dovrebbe contenere la regola dopo l'inserimento", 
                   ruleManager.queueContainsRule(rule));
    }

    @Test
    public void testQueuePeek() {
        Rule rule = ruleManager.createRule("PeekTestRule", triggerData, actionData, 0);
        ruleManager.queueOffer(rule);

        Rule peekedRule = ruleManager.queuePeek();
        assertSame("La regola visualizzata dovrebbe essere la stessa inserita", rule, peekedRule);
        assertFalse("La coda di esecuzione non dovrebbe essere vuota dopo il peek", 
                    ruleManager.getExecutionQueue().isEmpty());
    }
}
