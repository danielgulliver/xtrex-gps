package teamk.xtrex;

import java.io.File;

import teamk.xtrex.SpeechModel.LanguageEnum;

public class SpeechMain {
    public static void main(String[] args) {
        Speech speech = Speech.getSpeechInstance();
        SpeechModel model = Speech.getSpeechModelInstance();
        model.setLanguage(LanguageEnum.SPANISH);
        String[] directions = new String[] {"false", "Internet Connection Offline"}; //"Speech Is Unavailable Right Now", "Speech Is Back Online", "G P S Connection Lost", "Invalid Destination", "Recalculating Route", "Internet Connection Re-established", "You have reached your destination", "GPS Connection Acquired"};
                  //directions = new String[] {"false", "Conexión a Internet fuera de línea", "El habla no está disponible en este momento", "El habla está de vuelta en línea", "Conexión de GPS perdida",  "Destino inválido", "Recalcular la ruta", "Se restableció la conexión a Internet", "Ha llegado a su destino"};
        speech.parseDirections(directions);
    }
}