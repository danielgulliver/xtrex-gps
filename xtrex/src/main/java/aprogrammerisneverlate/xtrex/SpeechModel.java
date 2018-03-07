package aprogrammerisneverlate.xtrex;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * The model class behind turning the directions from text to speech, 
 * and playing that generated audio file.
 * 
 * @author ConorSpilsbury, 2018.
 * @version Sprint 2.
 */
public class SpeechModel {
    private static SpeechModel speechModel = null;
    private final static String APIKEY = "524ef33fdf7447a2a64cb38e0d70d1f6";
	//private final static String APIKEY2 = "7d6100f349c24081906cae7f4cb1d0d9";
	private final static String FORMAT = "riff-16khz-16bit-mono-pcm";
    private static LanguageEnum language;
    private enum LanguageEnum {
		ENGLISH("English", "en-GB", "Female", "(en-GB, Susan, Apollo)"),
		FRENCH("French", "fr-FR", "Male", "(fr-FR, Paul, Apollo)"),
		GERMAN("German", "de-DE", "Male", "(de-DE, Stefan, Apollo)"),
		ITALIAN("Italian", "it-IT",	"Male",	"(it-IT, Cosimo, Apollo)"),
		SPANISH("Spanish", "es-ES", "Female", "(es-ES, HelenaRUS)");

		private String name;
		private String locale;
		private String gender;
		private String artist; 

		private LanguageEnum(String name, String locale, String gender, String artist) {
			this.name = name;
			this.locale = locale;
			this.gender = gender;
			this.artist = artist;
		}

		public String getName() {
			return name;
		}

		public String getLocale() {
			return locale;
		}

		public String getGender() {
			return gender;
		}

		public String getArtist() {
			return artist;
		}		
    }
    
    /**
	 * Set the language of the speech. Default is that there is no speech and language is set to null.
	 * 
	 * @param the index of the the language in the list of supported languages.
	 */
	public void setLanguage(Integer index) {
		switch(index) {
		case 1:
			language = LanguageEnum.ENGLISH;
			break;
		case 2:
			language = LanguageEnum.FRENCH;
			break;
		case 3:
			language = LanguageEnum.GERMAN;
			break;
		case 4:
			language = LanguageEnum.ITALIAN;
			break;
		case 5: 
			language = LanguageEnum.SPANISH;
			break;	
		default:
			language = null;
		}
    }
    
    /**
	 * For each direction in the string array generate the .wav file for it. 
	 * The wav file is named after the index of the corresponding direction in the array.
	 * 
	 * @param directions is an array of strings containing all the directions that need to have speech generated for.
	 */
	public void parseDirections(String[] directions) {
		if (this.getLanguage() != null) {
			final String token  = renewAccessToken(APIKEY);
			for (int i = 0; i < directions.length; i++) {
				byte[] speech = generateSpeech(token, directions[i], language.getLocale(), 
						language.getGender(), language.getArtist(), 
						FORMAT);
				writeData(speech, String.valueOf(i) + ".wav");
			}
		}
	}

	/**
	 * Renew an access token.
	 * 
	 * @author David Wakeling, 2018.
	 * 
	 * @param key is the API key to renew an access token.
	 * 
	 * @return The response from microsoft cognitive services. 
	 */
	private static String renewAccessToken(String key) {
		final String method = "POST";
		final String url = 
				"https://api.cognitive.microsoft.com/sts/v1.0/issueToken";
		final byte[] body = {}; 
		final String[][] headers
		= { { "Ocp-Apim-Subscription-Key", key                         }
		, { "Content-Length"           , String.valueOf( body.length ) }
		};
		byte[] response = HttpConnect.httpConnect(method, url, headers, body);
		return new String(response); 
	}


    /**
	 * Generate speech.
	 * 
	 * @author David Wakeling, 2018.
	 * 
	 * @param token is the access token for the bing speech API.
	 * @param text is the text to generate speech for.
	 * @param locale is the locale of the language to generate the speech in.
	 * @param gender is the gender of the person speaking.
	 * @param artist is the 'person' whose voice is being used.
	 * @param format is the format of the file the speech should be saved as.
	 * 
	 * @return byte[] of the generated speech
	 */
	private static byte[] generateSpeech(String token, String text
			, String locale, String gender
			, String artist, String format ) {
		final String method = "POST";
		final String url = "https://speech.platform.bing.com/synthesize";
		final byte[] body
		= ( "<speak version='1.0' xml:lang='en-us'>"
				+ "<voice xml:lang='" + locale   + "' "
				+ "xml:gender='"      + gender + "' "
				+ "name='Microsoft Server Speech Text to Speech Voice "
				+ artist + "'>"
				+ text
				+ "</voice></speak>" ).getBytes(); 
		final String[][] headers
		= { { "Content-Type"             , "application/ssml+xml"      }
		, { "Content-Length"           , String.valueOf( body.length ) }
		, { "Authorization"            , "Bearer " + token             }
		, { "X-Microsoft-OutputFormat" , format                        }
		};
		byte[] response = HttpConnect.httpConnect(method, url, headers, body);
		return response;
	} 

	/**
	 * Write data to file.
	 * 
	 * @author David Wakeling, 2018.
	 * 
	 * @param buffer is the byte array of the generated speech 
	 * @param name is the name of the file to save
	 */
	private static void writeData(byte[] buffer, String name) {
		try {
			File             file = new File(name);
			FileOutputStream fos  = new FileOutputStream(file);
			DataOutputStream dos  = new DataOutputStream(fos); 
			dos.write(buffer);
			dos.flush();
			dos.close();
		} catch (Exception ex) {
			System.out.println(ex); 
			System.exit(1); 
			return;
		}
	}

	/**
	 * Play the audio file
	 * 
	 * @param  fileNumber is the integer name of the audio file to play
	 */
	public static void playAudio(int fileNumber) {
		String filename = String.valueOf(fileNumber) + ".wav";
		File file = new File(filename);
		AudioInputStream audioIn;
		try {
			audioIn = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			Thread.sleep(clip.getMicrosecondLength());
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * get the current language of the system
	 * 
	 * @return the current language of the system
	 */
	public String getLanguage() {
		return language.getName();
	}

	/**
	 * For testing purposes!
	 */
	public static void printLang() {
		System.out.println(language.getName());
    }

    public static SpeechModel getInstance() {
        if (speechModel == null) {
            speechModel = new SpeechModel();
        }
        return speechModel;
    }
}