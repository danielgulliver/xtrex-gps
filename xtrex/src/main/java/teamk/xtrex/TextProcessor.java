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
        line = line.replaceAll("(?<![a-zA-Z])RD(?![a-zA-Z])", "Road");
        line = line.replaceAll("(?<![a-zA-Z])Ln(?![a-zA-Z])", "Lane");
        line = line.replaceAll("(?<![a-zA-Z])Mt(?![a-zA-Z])", "Mount");
        line = line.replaceAll("(?<![a-zA-Z])Ct(?![a-zA-Z])", "Court");
        line = line.replaceAll("(?<![a-zA-Z])Plz(?![a-zA-Z])", "Plaza");
        line = line.replaceAll("(?<![a-zA-Z])Pl(?![a-zA-Z])", "Place");
        line = line.replaceAll("(?<![a-zA-Z])St(?![a-zA-Z])", "Street");
        line = line.replaceAll("(?<![a-zA-Z])Vlg(?![a-zA-Z])", "Village");
        line = line.replaceAll("(?<![a-zA-Z])Aly(?![a-zA-Z])", "Alley");
        line = line.replaceAll("(?<![a-zA-Z])Xing(?![a-zA-Z])", "Crossing");
        line = line.replaceAll("(?<![a-zA-Z])Bldg(?![a-zA-Z])", "Building");
        line = line.replaceAll("(?<![a-zA-Z])Ctr(?![a-zA-Z])", "Center");
        line = line.replaceAll("(?<![a-zA-Z])Byp(?![a-zA-Z])", "Bypass");
        line = line.replaceAll("(?<![a-zA-Z])Xrd(?![a-zA-Z])", "Crossroad");
        line = line.replaceAll("(?<![a-zA-Z])Lk(?![a-zA-Z])", "Lake");
        line = line.replaceAll("(?<![a-zA-Z])Mtn(?![a-zA-Z])", "Mountain");
        line = line.replaceAll("(?<![a-zA-Z])Ofc(?![a-zA-Z])", "Office");
        line = line.replaceAll("(?<![a-zA-Z])Pt(?![a-zA-Z])", "Point");
        line = line.replaceAll("(?<![a-zA-Z])Ml(?![a-zA-Z])", "Mill");
        line = line.replaceAll("(?<![a-zA-Z])Fld(?![a-zA-Z])", "Field");
        line = line.replaceAll("(?<![a-zA-Z])Cir(?![a-zA-Z])", "Circle");
        line = line.replaceAll("(?<![a-zA-Z])Crk(?![a-zA-Z])", "Creek");
        line = line.replaceAll("(?<![a-zA-Z])Bnd(?![a-zA-Z])", "Bend");
        line = line.replaceAll("(?<![a-zA-Z])Ave(?![a-zA-Z])", "Avenue");
        line = line.replaceAll("(?<![a-zA-Z])Apt(?![a-zA-Z])", "Apartment");
        

        // Cardinal directions
        line = line.replaceAll("(?<![a-zA-Z])NW(?![a-zA-Z])", "Northwest");
        line = line.replaceAll("(?<![a-zA-Z])N(?![a-zA-Z])", "North");
        line = line.replaceAll("(?<![a-zA-Z])NE(?![a-zA-Z])", "Northeast");
        line = line.replaceAll("(?<![a-zA-Z])E(?![a-zA-Z])", "East");
        line = line.replaceAll("(?<![a-zA-Z])SE(?![a-zA-Z])", "Southeast");
        line = line.replaceAll("(?<![a-zA-Z])S(?![a-zA-Z])", "South");
        line = line.replaceAll("(?<![a-zA-Z])SW(?![a-zA-Z])", "Southwest");
        line = line.replaceAll("(?<![a-zA-Z])W(?![a-zA-Z])", "West");

        return line;
    }

    public static String replaceCodes(String line) {
        line = line.replaceAll("/327", "");
        return line;
    }
}