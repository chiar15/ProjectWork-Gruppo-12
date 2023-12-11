package it.unisa.diem.se.automationapp.action;

import it.unisa.diem.se.automationapp.action.exception.AudioExecutionException;
import it.unisa.diem.se.automationapp.action.exception.FileException;
import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.concurrent.CountDownLatch;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The AudioAction class represents an action that plays an audio file.
 * It implements the ActionInterface and provides functionality to execute audio playback.
 */
public class AudioAction implements ActionInterface {
    private String filePath;

    /**
     * Default constructor for AudioAction.
     */
    public AudioAction() {
    }

    /**
     * Constructor for AudioAction that sets the file path.
     *
     * @param filePath The path of the audio file to be played.
     */
    public AudioAction(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Retrieves the file path associated with this AudioAction.
     *
     * @return The file path.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path for the audio to be played.
     *
     * @param filePath The path of the audio file.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Executes the audio playback action.
     *
     * @throws AudioExecutionException If an error occurs during audio playback.
     * @throws InterruptedException   If the thread is interrupted while waiting for audio playback to finish.
     * @throws InvalidInputException  If the file path is null or empty.
     * @throws FileException          If an error occurs with the audio file, such as not found or access issues.
     */
    @Override
    public void execute() throws AudioExecutionException, InterruptedException, InvalidInputException, FileException {
        if (filePath == null || filePath.isEmpty()) {
            throw new InvalidInputException("The file path cannot be empty.");
        }

        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            try (Clip audioClip = (Clip) AudioSystem.getLine(info)) {
                audioClip.open(audioStream);

                // Add a LineListener to be notified when playback is finished
                CountDownLatch latch = new CountDownLatch(1);
                audioClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        latch.countDown();
                    }
                });

                audioClip.start(); // Start audio playback
                latch.await(); // Wait for playback completion
                audioClip.stop();
            } catch (LineUnavailableException e) {
                throw new AudioExecutionException("Error while playing the selected audio file");
            } catch (InterruptedException e) {
                throw new InterruptedException(e.getMessage());
            }
        } catch (UnsupportedAudioFileException e) {
            throw new AudioExecutionException("Audio file format is not supported");
        } catch (NoSuchFileException e) {
            throw new FileException("The selected audio file was not found.");
        } catch (IOException e) {
            throw new FileException("Cannot access the selected audio file");
        }
    }

    /**
     * Provides a string representation of the action being performed.
     *
     * @return A string describing the action.
     */
    @Override
    public String toString() {
        return "Playing the audio located at path: " + filePath;
    }
}