package teamk.xtrex;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import teamk.xtrex.SpeechModel.LanguageEnum;



/**
 * Used as the API for all speech related components. 
 * Instantiates all the elements of the Speech MVC and provide access
 * to the SpeechView and SpeechModel for the system front end.
 * 
 * @author Conor Spilsbury, 2018.
 * @version Sprint 3.
 */
public class Speech {
    private static final int MICROSECONDS_IN_MILISECOND = 1000;
	private static Speech speech = null;
    private static SpeechModel model;
    private static SpeechController controller;
    private static SpeechView view;
    private static boolean SpeechAvailability = true;

    public enum NotificationsEnum {
        SpeechUnavailable("SpeechUnavailable"),
        SpeechOnline("SpeechOnline"),
        Recalculating("Recalculating"),
        InvalidDestination("InvalidDestination"),
        InternetOffline("InternetOffline"),
        InternetEstablished("InternetEstablished"),
        GPSConnectionLost("GPSConnectionLost"),
        GPSAcquired("GPSAcquired"),
        DestinationReached("DestinationReached");

        private String name;

        private NotificationsEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

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

    /**
     * Lazy instantiation of the Speech class
     * 
     * @return instance of the SpeechModel class
     */
    public static SpeechModel getSpeechModelInstance() {
        if (speech == null) {
            speech = new Speech();
        }
        return model;
    }

    /**
     * parse the directions to generate speech for each direction
     * 
     * @param directions[] array of all directions 
     */
    public static void parseDirections(String[] directions) {
        model.parseDirections(directions);
    }

    /**
     * get the language of the device
     * 
     * @return the language of the device as an Enum
     */
    public SpeechModel.LanguageEnum getLanguage() {
        return model.getLanguage();
    }

    /**
     * Get the language code of the language the speech is currently set to.
     * 
     * @return language code of the current language.
     */
    public String getCountryCode() {
        return model.getCountryCode();
    }

    /**
	 * Play the audio file in a new thread
	 * 
	 * @param File is the file name of the audio file to play
	 */
	public static void playAudio(String fileName) {
        File file = new File("audio/" + fileName + ".wav");
        if (model.getLanguage() != LanguageEnum.OFF) {
            try {
                final AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
                Thread thread = new Thread(new Runnable() {
                    public void run() { 
                        Clip clip;
                        try {
                            clip = AudioSystem.getClip(null);
                            clip.open(audioIn);
                            clip.start();
                            Thread.sleep(clip.getMicrosecondLength()/MICROSECONDS_IN_MILISECOND);
                        } catch (LineUnavailableException e) {
                            Speech.setSpeechAvailability(false);
                        } catch (InterruptedException e) {
                            Speech.setSpeechAvailability(false);
                        }
                        catch (IOException e) {
                            Speech.setSpeechAvailability(false);
                        }
                    }
                });
                thread.start();
            } catch (UnsupportedAudioFileException e) {
                System.out.println("Unsupported audio file");
                Speech.setSpeechAvailability(false);
            } catch (IOException e) {
                Speech.setSpeechAvailability(false);
            }
        }
    }

    /**
     * play audio notifications
     * 
     * @param NotificationsEnum notification to be played
     */
    public static void playAudioNotification(NotificationsEnum notification) {
        playAudio("notifications/" + model.getLanguage() +
          notification.getName() + model.getLanguage().getName());
    }

    /**
     * Get the availability of speech
     * 
     * @return whether speech is available
     */
    public static boolean getSpeechAvailability() {
        return SpeechAvailability;
    }


    /**
     * set the availability of speech 
     * 
     * @param availability of the speech
     */
    public static void setSpeechAvailability(boolean availability) {
        SpeechAvailability = availability;
    }
}