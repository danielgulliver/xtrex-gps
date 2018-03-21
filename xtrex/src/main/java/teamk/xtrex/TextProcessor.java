package teamk.xtrex;

/**
 * Process and make amendments to text download from maps API.
 * 
 * @author Conor Spilsbury
 * @version sprint 3
 */
public class TextProcessor {
    /**
     * Remove html tags from a string
     */
    public static String removeHTMLTags(String line) {
        return line.replaceAll("<.*?>","");
    }

    /**
     * Expand abbrevations such as "N" or "Rd." from the google maps output.
     * 
     * <Abbreviation>(?![a-zA-Z]) will select all occurences of <abbreviation> which 
     * is not followed by more characters. i.e. will select <abbreviation> when it
     * is surrounded by white space or if it is at the end of a sentence.
     * 
     * (?<![a-zA-Z])<Abbreviation> will select all occurences of <abbreviation> which are not
     * preceded by characters
     */
    public static String expandAbbreviations(String line) {
        line.replaceAll("(?<![a-zA-Z])RD(?![a-zA-Z])", "Road");
        line.replaceAll("(?<![a-zA-Z])Ln(?![a-zA-Z])", "Lane");
        line.replaceAll("(?<![a-zA-Z])Mt(?![a-zA-Z])", "Mount");
        line.replaceAll("(?<![a-zA-Z])Ct(?![a-zA-Z])", "Court");
        line.replaceAll("(?<![a-zA-Z])Plz(?![a-zA-Z])", "Plaza");
        line.replaceAll("(?<![a-zA-Z])Pl(?![a-zA-Z])", "Place");
        line.replaceAll("(?<![a-zA-Z])St(?![a-zA-Z])", "Street");
        line.replaceAll("(?<![a-zA-Z])Vlg(?![a-zA-Z])", "Village");
        line.replaceAll("(?<![a-zA-Z])Aly(?![a-zA-Z])", "Alley");
        line.replaceAll("(?<![a-zA-Z])Xing(?![a-zA-Z])", "Crossing");
        line.replaceAll("(?<![a-zA-Z])Bldg(?![a-zA-Z])", "Building");
        line.replaceAll("(?<![a-zA-Z])Ctr(?![a-zA-Z])", "Center");
        line.replaceAll("(?<![a-zA-Z])Byp(?![a-zA-Z])", "Bypass");
        line.replaceAll("(?<![a-zA-Z])Xrd(?![a-zA-Z])", "Crossroad");
        line.replaceAll("(?<![a-zA-Z])Lk(?![a-zA-Z])", "Lake");
        line.replaceAll("(?<![a-zA-Z])Mtn(?![a-zA-Z])", "Mountain");
        line.replaceAll("(?<![a-zA-Z])Ofc(?![a-zA-Z])", "Office");
        line.replaceAll("(?<![a-zA-Z])Pt(?![a-zA-Z])", "Point");
        line.replaceAll("(?<![a-zA-Z])Ml(?![a-zA-Z])", "Mill");
        line.replaceAll("(?<![a-zA-Z])Fld(?![a-zA-Z])", "Field");
        line.replaceAll("(?<![a-zA-Z])Cir(?![a-zA-Z])", "Circle");
        line.replaceAll("(?<![a-zA-Z])Crk(?![a-zA-Z])", "Creek");
        line.replaceAll("(?<![a-zA-Z])Bnd(?![a-zA-Z])", "Bend");
        line.replaceAll("(?<![a-zA-Z])Ave(?![a-zA-Z])", "Avenue");
        line.replaceAll("(?<![a-zA-Z])Apt(?![a-zA-Z])", "Apartment");
        

        // Cardinal directions
        line.replaceAll("(?<![a-zA-Z])NW(?![a-zA-Z])", "Northwest");
        line.replaceAll("(?<![a-zA-Z])N(?![a-zA-Z])", "North");
        line.replaceAll("(?<![a-zA-Z])NE(?![a-zA-Z])", "Northeast");
        line.replaceAll("(?<![a-zA-Z])E(?![a-zA-Z])", "East");
        line.replaceAll("(?<![a-zA-Z])SE(?![a-zA-Z])", "Southeast");
        line.replaceAll("(?<![a-zA-Z])S(?![a-zA-Z])", "South");
        line.replaceAll("(?<![a-zA-Z])SW(?![a-zA-Z])", "Southwest");
        line.replaceAll("(?<![a-zA-Z])W(?![a-zA-Z])", "West");

        return line;
    }
}