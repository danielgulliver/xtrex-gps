package teamk.xtrex;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * ************* SANITISE DIRECTIONS
 */


/**
 * The model class behind turning the directions from text to speech, 
 * and playing that generated audio file.
 * 
 * @author ConorSpilsbury, 2018.
 * @version Sprint 3.
 */
public class SpeechModel {
    private static SpeechModel speechModel = null;
    //private final static String APIKEY = "524ef33fdf7447a2a64cb38e0d70d1f6";
	private final static String APIKEY = "7d6100f349c24081906cae7f4cb1d0d9";
	private final static String FORMAT = "riff-16khz-16bit-mono-pcm";
	private final static Integer RENEW_RATE = 10;
	private final static Integer RENEW_PERIOD = 0;
	private final static Integer BING_API_SLEEPTIME_MILLISECONDS = 200;
	private static LanguageEnum language;
	
    private static String accessToken = null;
    public enum LanguageEnum {
		OFF("Off","en-GB","en-GB","en-GB",""),
		ENGLISH("English", "en-GB", "en-GB", "Female", "(en-GB, Susan, Apollo)"),
		FRENCH("Français", "fr-FR", "fr", "Male", "(fr-FR, Paul, Apollo)"),
		GERMAN("Deutsch", "de-DE", "de", "Male", "(de-DE, Stefan, Apollo)"),
		ITALIAN("Italiano", "it-IT", "it","Male",	"(it-IT, Cosimo, Apollo)"),
		SPANISH("Español", "es-ES","es", "Female", "(es-ES, HelenaRUS)");

		private String name;
		private String microsoftLanguageCode;
		private String googleLanguageCode;
		private String gender;
		private String artist; 

		private LanguageEnum(String name, String microsoftLanguageCode, String googleLanguageCode, String gender, String artist) {
			this.name = name;
			this.microsoftLanguageCode = microsoftLanguageCode;
			this.googleLanguageCode = googleLanguageCode;
			this.gender = gender;
			this.artist = artist;
		}

		public String getName() {
			return name;
		}

		public String getGoogleCode() {
			return googleLanguageCode;
		}

		public String getMicrosoftCode() {
			return microsoftLanguageCode;
		}

		public String getGender() {
			return gender;
		}

		public String getArtist() {
			return artist;
		}		
    }

    /**
     * Initialise the speech Model and set a schedule execution service to 
     * renew a new access token to the Bing Speech API once every 10 minutes.
     */
    public SpeechModel() {
		// create a Scheduled Executer Service to renew API Key every 10 mins
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable(){
            public void run() {
                setAccessToken();
            }
		},RENEW_PERIOD,RENEW_RATE,TimeUnit.MINUTES);
		// default the language to english
		language = LanguageEnum.ENGLISH;
    }
    
    /**
	 * Set the language of the speech. Default is English.
	 * 
	 * @param index of the the language in the list of supported languages.
	 */
	public void setLanguage(LanguageEnum lang) {
		language = lang;
    }
    
    /**
	 * For each direction in the string array generate the .wav file for it. 
	 * The wav file is named after the index of the corresponding direction in the array.
	 * 
	 * @param directions is an array of strings containing all the directions that need to have speech generated for.
	 */
	public void parseDirections(final String[] directions) {
		if (directions == null) return;
		// create new thread to generate speech for all the directions
		Thread thread = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < directions.length; i++) {
					// synthesise speech for each direction
					final byte[] speech = generateSpeech( getAccessToken(),  directions[i],  language.getMicrosoftCode()
						, language.getGender(), language.getArtist(), FORMAT);

					// write the audio file of the speech to a file
					writeData(speech, String.valueOf(i) + ".wav");
					try {
						// sleep thread to avoid hitting maximum rate for bing api 
						Thread.sleep(BING_API_SLEEPTIME_MILLISECONDS); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				}
			}
		});
		thread.start();
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
		if (response != null) {
			System.out.println("Access token ok");
		
				return new String(response);
			
		} else {
			System.out.println("error renewing token");
			return null;
		}
    }
    
    /**
     * Sets the access token for the Bing Speech API using the 
     * renewAccessToken(String key) method. 
     */
    private static void setAccessToken() {
        String token = renewAccessToken(APIKEY);
        accessToken = token;
    }

    /**
     * Retrieve the current accessToken.
     * 
     * @return The current access token for Bing Speech API.
     */
    private String getAccessToken() {
        return accessToken;
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
	static byte[] generateSpeech( String token,  String text
                              , String lang,   String gender
                              , String artist, String format ) {
		final String method = "POST";
		final String url = "https://speech.platform.bing.com/synthesize";
		final byte[] body
		= ( "<speak version='1.0' xml:lang='en-us'>"
			+ "<voice xml:lang='" + lang   + "' "
			+ "xml:gender='"      + gender + "' "
			+ "name='Microsoft Server Speech Text to Speech Voice "
			+ artist + "'>"
			+ text
			+ "</voice></speak>" ).getBytes(); 
		final String[][] headers
		= { { "Content-Type"             , "application/ssml+xml"        }
			, { "Content-Length"           , String.valueOf( body.length ) }
			, { "Authorization"            , "Bearer " + token             }
			, { "X-Microsoft-OutputFormat" , format                        }
			};
		byte[] response = HttpConnect.httpConnect( method, url, headers, body );
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
	 * get the current language of the system
	 * 
	 * @return the current language of the system
	 */
	public String getLanguage() {
		return language.getName();
	}

    /**
     * Lazy instantiate the SpeechModel
     */
    public static SpeechModel getInstance() {
        if (speechModel == null) {
            speechModel = new SpeechModel();
        }
        return speechModel;
	}
	
	/**
     * Get the language code of the language the speech is currently set to.
     * 
     * @return language code of the current language.
     */
	public String getLanguageCode() {
		return language.getGoogleCode();
	}
}