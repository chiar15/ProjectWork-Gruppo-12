package it.unisa.diem.se.automationapptest.rulesmanagement;

import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.SuspendedRuleDecorator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SuspendedRuleDecoratorTest {

    private SuspendedRuleDecorator suspendedRule;
    private Rule baseRule;

    @Before
    public void setUp() {
        baseRule = new Rule("BaseRule", null, null); // Assume Trigger and Action are null for the test
        // Setting suspension period as 1 day (86400 seconds) + 1 hour (3600 seconds) + 30 minutes (1800 seconds)
        long suspensionPeriodSeconds = 91800; // Total = 90000 seconds
        suspendedRule = new SuspendedRuleDecorator(baseRule, suspensionPeriodSeconds);
    }

    @Test
    public void testSuspensionPeriod() {
        assertEquals("The suspension period should be 91800 seconds", 91800, suspendedRule.getSuspensionPeriod());
    }

    @Test
    public void testLastExecutionTime() {
        long testTime = System.currentTimeMillis();
        suspendedRule.setLastExecutionTime(testTime);
        assertEquals("The last execution time should match the set time", testTime, suspendedRule.getLastExecutionTime());
    }

    @Test
    public void testGetSimpleSuspensionPeriod() {
        String expected = "1 days 1 hours 30 minutes ";
        assertEquals("The simple suspension period representation should be correct", expected, suspendedRule.getSimpleSuspensionPeriod());
    }

    @Test
    public void testIsReadyToExecute() {
        suspendedRule.setLastExecutionTime(System.currentTimeMillis() - 91801 * 1000L); // Just over 91800 seconds ago
        assertTrue("The rule should be ready for execution", suspendedRule.isReadyToExecute());
    }
}
