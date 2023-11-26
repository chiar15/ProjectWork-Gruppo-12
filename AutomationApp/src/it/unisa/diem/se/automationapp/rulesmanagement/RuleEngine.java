/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

public class RuleEngine implements Runnable{
    private RuleService ruleService;

    public RuleEngine(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            for (Rule rule : ruleService.getRuleList()) {
                if (rule.isTriggered()) {
                    rule.execute();
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Ripristina lo stato di interruzione
                return; // Termina il ciclo e quindi il thread
            }
        }
    }
    
    
}
