/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.rulesmanagement;

import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleList;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class RuleListTest {

    private RuleList ruleList;
    private Rule testRule;

    @Before
    public void setUp() {
        ruleList = new RuleList();
        testRule = new Rule("TestRule", null, null); // Assumi che Trigger e Action siano null per il test
    }

    @Test
    public void testConstructorAndGetter() {
        assertTrue("The rule list should be initially empty", ruleList.getRules().isEmpty());
    }

    @Test
    public void testSetRules() {
        List<Rule> newRules = new ArrayList<>();
        newRules.add(testRule);
        ruleList.setRules(newRules);

        assertFalse("The rule list should not be empty after setting new rules", ruleList.getRules().isEmpty());
        assertSame("The rule in the list should be the one that was added", testRule, ruleList.getRules().get(0));
    }
    
        @Test
    public void testSetEmptyRuleList() {
        List<Rule> emptyRuleList = new ArrayList<>();
        ruleList.setRules(emptyRuleList);

        assertTrue("The rule list should be empty after setting an empty list", ruleList.getRules().isEmpty());
    }

    @Test
    public void testAddAndRemoveRule() {
        ruleList.getRules().add(testRule);
        assertFalse("The rule list should not be empty after adding a rule", ruleList.getRules().isEmpty());

        ruleList.getRules().remove(testRule);
        assertTrue("The rule list should be empty after removing the rule", ruleList.getRules().isEmpty());
    }

    @Test
    public void testSetNullRuleList() {
        ruleList.setRules(null);
        assertNull("The rule list should be null after setting to null", ruleList.getRules());
    }

    @Test
    public void testMultipleRulesHandling() {
        List<Rule> multipleRules = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            multipleRules.add(new Rule("Rule" + i, null, null));
        }
        ruleList.setRules(multipleRules);

        assertEquals("The rule list should have 5 rules", 5, ruleList.getRules().size());
    }
}
