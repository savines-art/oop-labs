package sys.pro;

/**
 * Immutable record representing a subject result.
 *
 * @param semester the semester number
 * @param name the subject name
 * @param type the subject type
 * @param mark the received mark
 */
public record Subject(int semester, String name, SubjectType type, Mark mark) {
    /**
     * Validates the semester number.
     *
     * @param semester the semester number
     * @param name the subject name
     * @param type the subject type
     * @param mark the received mark
     * @throws IllegalArgumentException if the semester number is outside the range 1 to 8
     */
    public Subject {
        if (semester < 1 || semester > 8) {
            throw new IllegalArgumentException("Got wrong semester number.");
        }
    }

    /**
     * Returns the mark received for this subject.
     *
     * @return the mark
     */
    public Mark getMark() {
        return this.mark;
    }

    /**
     * Returns the semester number of this subject.
     *
     * @return the semester number
     */
    public int getSemester() {
        return this.semester;
    }

    /**
     * Returns the type of this subject.
     *
     * @return the subject type
     */
    @Override
    public SubjectType type() {
        return type;
    }

    /**
     * Returns the name of this subject.
     *
     * @return the subject name
     */
    public String getName() {
        return this.name;
    }
}
