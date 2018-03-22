package teamk.xtrex;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import teamk.xtrex.SpeechModel.LanguageEnum;

public class SpeechMain {
    public static LanguageEnum language;
    private static SpeechModel speechModel = null;
    //private final static String APIKEY = "524ef33fdf7447a2a64cb38e0d70d1f6";
	private final static String APIKEY = "7d6100f349c24081906cae7f4cb1d0d9";
	private final static String FORMAT = "riff-16khz-16bit-mono-pcm";
	private final static Integer RENEW_RATE = 10;
	private final static Integer RENEW_PERIOD = 0;
	private final static Integer BING_API_SLEEPTIME_MILLISECONDS = 3750;
	
    private static String accessToken = null;

    public static void main(String[] args) {
        Speech speech = Speech.getSpeechInstance();
        SpeechModel model = Speech.getSpeechModelInstance();
        model.setLanguage(LanguageEnum.SPANISH);
        language = model.getLanguage();
        String[] directions = new String[] {"Internet Connection Offline", "Speech Is Unavailable Right Now", "Speech Is Back Online", "G P S Connection Lost", "Invalid Destination", "Recalculating Route", "Internet Connection Re-established", "You have reached your destination", "GPS Connection Acquired"};
        directions = new String[] {"Internetverbindung offline", "Sprache ist momentan nicht verfügbar", "Sprache ist wieder online", "GPS-Verbindung verloren", "Ungültiges Ziel", "Route neu berechnen", "Internetverbindung wiederhergestellt", "Sie haben Ihr Ziel erreicht Ziel "," GPS-Verbindung erworben "};
        model.setLanguage(LanguageEnum.GERMAN);
        language = model.getLanguage();
        parseDirections(directions);
    }

    public static void parseDirections(final String[] directions) {
		if (directions == null) return;
		// create new thread to generate speech for all the directions
		Thread thread = new Thread(new Runnable() {

			public void run() {

				for (int i = 0; i < directions.length; i++) {
					if (accessToken == null) {
						setAccessToken();
					}
					final byte[] speech = generateSpeech(accessToken, directions[i], 
						language.getLanguageCode(), language.getGender(), language.getArtist(), FORMAT);
					System.out.println(speech == null);
						// write the audio file of the speech to a file
					writeData(speech, directions[i] + language.getName() + ".wav");
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
		return new String(response);
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
	private static byte[] generateSpeech( String token,  String text
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
	 * @author David Wakeling
	 * @author Conor Spilsbury
	 * 
	 * @param buffer is the byte array of the generated speech 
	 * @param name is the name of the file to save
	 */
	private static void writeData(byte[] buffer, String name) {
		if (buffer == null && !Speech.getSpeechAvailability() && MapModel.getInternetAvailability()) {
			Speech.playAudioNotification(Speech.NotificationsEnum.SpeechUnavailable);
			Speech.setSpeechAvailability(false);
			return;
		} 
		if (!Speech.getSpeechAvailability()) {
			Speech.playAudioNotification(Speech.NotificationsEnum.SpeechOnline);
			Speech.setSpeechAvailability(true);
		}
		try {
			File             file = new File("audio/Notifications/" + name.replaceAll(" ", ""));
			FileOutputStream fos  = new FileOutputStream(file);
			DataOutputStream dos  = new DataOutputStream(fos); 
			dos.write(buffer);
			dos.flush();
			dos.close();
		} catch (Exception ex) {
			Speech.playAudioNotification(Speech.NotificationsEnum.SpeechUnavailable);
            Speech.setSpeechAvailability(false);
            ex.printStackTrace();
		}
	}


}