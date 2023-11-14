package seedu.address.commons.util;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.numbers.fraction.BigFraction;

/**
 * Class representing precise numbers.
 */
public class FractionUtil {

    /**
     * Creates new fraction from decimal string.
     *
     * @param s Decimal string input.
     * @return Fraction created.
     */
    public static BigFraction parseFraction(String s) {
        String[] parts = s.replace(" ", "").split("\\.");
        switch (parts.length) {
        case 1:
            return BigFraction.of(new BigInteger(parts[0]));
        case 2:
            return BigFraction.of(new BigInteger(parts[0] + parts[1]),
                    new BigInteger("1" + "0".repeat(parts[1].length())));
        default:
            throw new NumberFormatException();
        }
    }

    /**
     * Converts fraction to string representation.
     *
     * @param fraction The fraction to be converted.
     * @param decimalPlaces The number of decimal places of precision.
     * @return The string representation of the fraction.
     */
    public static String toString(BigFraction fraction, int decimalPlaces) {
        assert decimalPlaces >= 0;
        return fraction.bigDecimalValue(decimalPlaces, RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * Returns the sum of a list of fractions.
     *
     * @param list The list of fractions to be summed.
     * @return The sum of the fractions in the list.
     */
    public static BigFraction sum(List<BigFraction> list) {
        BigFraction s = BigFraction.ZERO;
        for (BigFraction i : list) {
            s = s.add(i);
        }
        return s;
    }

}
