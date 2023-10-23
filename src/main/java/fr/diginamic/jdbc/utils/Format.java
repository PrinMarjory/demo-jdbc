package fr.diginamic.jdbc.utils;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Format {
	
	public static String bigInt (int nb) {
		Locale local = new Locale("uk", "UK");
		String pattern = "#,###";
		
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(local);
		decimalFormat.applyPattern(pattern);
		
		return decimalFormat.format(nb);
	}
}
