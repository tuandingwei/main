package seedu.address.model.cap.person.rank;

import seedu.address.model.cap.person.DegreeClassification;

/**
 * Represents a Pass grade.
 */
public class Pass extends Rank {

    private static final String TITLE = "PASS";
    private static final double MAXIMUM_CAP = 2.99;
    private static final double MINIMUM_CAP = 2.00;
    private static final String imageFilePath = "/images/pass.png";

    public Pass() {
        super(DegreeClassification.PASS, TITLE);
    }

    public static double getMaximumCap() {
        return MAXIMUM_CAP;
    }

    public static double getMinimumCap() {
        return MINIMUM_CAP;
    }

    @Override
    public String getRankImageFilePath() {
        return imageFilePath;
    }

    /**
     * To check if the cap given is within the range of the catagorisation of this rank/ class.
     * @param cap CUMULATIVE AVERAGE POINT
     * @return
     */
    public static boolean isWithinRange(double cap) {
        if (cap >= MINIMUM_CAP && cap <= MAXIMUM_CAP) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return TITLE;
    }
}