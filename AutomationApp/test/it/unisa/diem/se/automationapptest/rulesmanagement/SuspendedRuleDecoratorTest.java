/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        // Creazione di una semplice implementazione fittizia di Rule
        baseRule = new Rule("BaseRule", null, null);  // Assumi che Trigger e Action siano null per il test
        suspendedRule = new SuspendedRuleDecorator(baseRule, 10); // 10 secondi di sospensione
    }

    @Test
    public void testSuspensionPeriod() {
        assertEquals("Il periodo di sospensione dovrebbe essere 10 secondi", 10, suspendedRule.getSuspensionPeriod());
    }

    @Test
    public void testLastExecutionTime() {
        long testTime = System.currentTimeMillis();
        suspendedRule.setLastExecutionTime(testTime);
        assertEquals("Il tempo dell'ultima esecuzione dovrebbe corrispondere al tempo impostato", testTime, suspendedRule.getLastExecutionTime());
    }

    @Test
    public void testIsReadyToExecute() {
        // Imposta il tempo dell'ultima esecuzione a un tempo nel passato
        suspendedRule.setLastExecutionTime(System.currentTimeMillis() - 15000); // 15 secondi fa
        assertTrue("La regola dovrebbe essere pronta per l'esecuzione", suspendedRule.isReadyToExecute());

        suspendedRule.setLastExecutionTime(System.currentTimeMillis());
        assertFalse("La regola non dovrebbe essere pronta per l'esecuzione immediatamente dopo l'ultima esecuzione", suspendedRule.isReadyToExecute());
    }
}
