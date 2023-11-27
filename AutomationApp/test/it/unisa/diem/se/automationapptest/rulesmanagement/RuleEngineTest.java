/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.rulesmanagement;

import it.unisa.diem.se.automationapp.rulesmanagement.*;
import it.unisa.diem.se.automationapp.observer.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class RuleEngineTest {

    private RuleEngine ruleEngine;
    private EventBus fakeEventBus;
    private AtomicBoolean eventPublished;

    @Before
    public void setUp() {
        eventPublished = new AtomicBoolean(false);
        fakeEventBus = new EventBus() {
            @Override
            public <T> void publish(T event) {
                eventPublished.set(true);
            }
        };

        ruleEngine = new RuleEngine(fakeEventBus);
        RuleService.getInstance().getRuleList().clear(); // Pulisci la lista delle regole
    }

    @Test
    public void testExecuteRulesWhenTriggered() {
        RuleService.getInstance().getRuleList().add(new Rule("TestRule", () -> true, () -> {}));
        ruleEngine.executeRules();
        assertFalse(eventPublished.get()); // Nessun evento dovrebbe essere pubblicato se non ci sono errori
    }

    @Test
    public void testHandleException() {
        RuleService.getInstance().getRuleList().add(new Rule("TestRule", () -> true, () -> { throw new RuntimeException("Test error"); }));
        ruleEngine.executeRules();
        assertTrue(eventPublished.get()); // Un evento dovrebbe essere pubblicato a causa dell'eccezione
    }
}

