package teamk.xtrex;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Used as the API for all speech related components. 
 * Instantiates all the elements of the Speech MVC and provide saccess
 * to the SpeechView and SpeechModel for the system front end.
 * 
 * @author Conor Spilsbury, 2018.
 * @version Sprint 2.
 */
public class Speech {
    private static final int MICROSECONDS_IN_MILISECOND = 1000;
	private static Speech speech = null;
    private static SpeechModel model;
    private static SpeechController controller;
    private static SpeechView view;

    /**
     * Instantiate the Speech class which in turn instantiates each part of the 
     * MVC designed speech system. 
     */
    private Speech() {
        model = new SpeechModel();
        controller = new SpeechController(model);
        view = new SpeechView(controller);
    }

    /**
     * Lazy instantiation of the Speech class.
     * 
     * @return instance of the Speech class.
     */
    public static Speech getSpeechInstance() {
        if (speech == null) {
            speech = new Speech();
        }
        return speech;
    }

    /**
     * Lazy instantiation of the Speech class.
     * 
     * @return instance of the SpeechView class.
     */
    public static SpeechView getSpeechViewInstance() {
        if (speech == null) {
            speech = new Speech();
        }
        return view;
    }

    public static SpeechModel getSpeechModelInstance() {
        if (model == null) {
            model = new SpeechModel();
        }
        return model;
    }

    /**
     * parse the directions
     */
    public static void parseDirections(String[] directions) {
        model.parseDirections(directions);
    }

    /**
     * get the language of the device
     */
    public String getLanguage() {
        return model.getLanguage();
    }

    /**
     * Get the language code of the language the speech is currently set to.
     * 
     * @return language code of the current language.
     */
    public String getLanguageCode() {
        return model.getLanguageCode();
    }

    /**
	 * Play the audio file in a new thread
	 * 
	 * @param File is the file name of the audio file to play
	 */
	public static void playAudio(File file) {
		try {
			final AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			Thread thread = new Thread(new Runnable() {
				AudioInputStream ais = audioIn;
				public void run() { 
					Clip clip;
					try {
						clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
						Thread.sleep(clip.getMicrosecondLength()/MICROSECONDS_IN_MILISECOND);
					} catch (LineUnavailableException e1) {
						
					} catch (InterruptedException e) {
                        System.out.println("Interrupted");
					}
					catch (IOException e) {
						System.out.println("Wrong file name");
					}
				}
			});
			thread.start();
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Unsupported audio file");
		} catch (IOException e) {
			System.out.println("Wrong file name");
		}
    }
}