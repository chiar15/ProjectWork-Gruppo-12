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
}
