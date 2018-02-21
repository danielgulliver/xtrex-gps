package aprogrammerisneverlate.xtrex;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class SpeechLogic {
	private final static String APIKEY = "5190ee0163b5498a94308ab1496ed04d";
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


	/*
	 * For each direction in the string array generate the .wav file for it. 
	 * The wav file is named after the index of the corresponding direction in the array.
	 * 
	 * @param directions is an array of strings containing all the directions that need to have speech generated for.
	 */
	public static void parseDirections(String[] directions) {
		final String token  = renewAccessToken(APIKEY);
		for (int i = 0; i < directions.length; i++) {
			byte[] speech = generateSpeech( token,  directions[i], language.getLocale(), language.getGender(), language.getArtist(), FORMAT);
			writeData(speech, String.valueOf(i));
		}
	}

	/*
	 * Renew an access token.
	 * 
	 * David Wakeling, 2018.
	 * 
	 * @param key is the api key to renew an access token
	 */
	private static String renewAccessToken( String key ) {
		final String method = "POST";
		final String url = 
				"https://api.cognitive.microsoft.com/sts/v1.0/issueToken";
		final byte[] body = {}; 
		final String[][] headers
		= { { "Ocp-Apim-Subscription-Key", key                         }
		, { "Content-Length"           , String.valueOf( body.length ) }
		};
		byte[] response = HttpConnect.httpConnect( method, url, headers, body);
		return new String(response); 
	}

	/*
	 * Generate speech.
	 * 
	 * David Wakeling, 2018.
	 * 
	 * @param token is the access token for the api
	 * @param text is the text to generate speech for
	 * @param locale is the locale of the language to generate the speech in
	 * @param gener is the gender of the person speaking 
	 * @param artist is the 'person' whose voice is being used
	 * @param format is the format of the file the speech should be saved as
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

	/*
	 * Write data to file.
	 * 
	 * David Wakeling, 2018.
	 * 
	 * @param buffer is the byte array of the generated speech 
	 * @param name is the name of the file to save
	 */
	private static void writeData(byte[] buffer, String name) {
		try {
			File             file = new File( name );
			FileOutputStream fos  = new FileOutputStream( file );
			DataOutputStream dos  = new DataOutputStream( fos ); 
			dos.write( buffer );
			dos.flush();
			dos.close();
		} catch ( Exception ex ) {
			System.out.println( ex ); System.exit( 1 ); return;
		}
	}

	/*
	 * Play the audio file
	 * 
	 * @param fileNumber is the audio file to play
	 */
	public static void playAudio(int fileNumber) {

	}

	/*
	 * Will make a decision on how to handle speech settings being off (thus index = 0).
	 * Should only be used by the front end speech class which will only give values 1 through 5,
	 * but this is sloppy and allows errors. This is my job for wednesday 21st.
	 */
	public static void setLanguage(int index) {
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
		}
	}

	/*
	 * For testing purposes
	 */
	public static void printLang() {
		System.out.println(language.getName());
	}
}