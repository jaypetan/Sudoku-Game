package sudoku;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;


public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = this.getClass().getClassLoader().getResource("Victory.wav");
        soundURL[1] = this.getClass().getClassLoader().getResource("negative_beeps-6008.wav");
        soundURL[2] = this.getClass().getClassLoader().getResource("Success.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audio);
            
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        } catch (LineUnavailableException e) {
           e.printStackTrace();
        }
    }

    public void play() {
    	if (clip != null) {
            clip.start();
        }
    }

    public void loop() {
    	if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
    	if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close(); // Close the clip to release resources
        }
    }



}