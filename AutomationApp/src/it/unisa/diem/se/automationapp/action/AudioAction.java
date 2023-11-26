/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action;

import java.io.File;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;


public class AudioAction implements ActionInterface{
    private String filePath;
    
    public AudioAction(Map<String, String> actionData){
        this.filePath = actionData.get("filePath");
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public void execute()throws Exception{
        File audioFile = new File(filePath);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);

        try (Clip audioClip = (Clip) AudioSystem.getLine(info)) {
            audioClip.open(audioStream);

            // Aggiungi un LineListener per essere notificato quando la riproduzione è finita
            CountDownLatch latch = new CountDownLatch(1);
            audioClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    latch.countDown();
                }
            });

            audioClip.start();  // Avvia la riproduzione dell'audio
            latch.await();  // Attendi il completamento della riproduzione
            audioClip.stop();
        }
    } 
}

    
    
