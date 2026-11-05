package sys.pro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Stores subject results and evaluates academic status.
 */
public class RecordBook {
    /**
     * List of subject results in this record book.
     */
    ArrayList<Subject> subjects;

    /**
     * Creates an empty record book.
     */
    RecordBook() {
        this.subjects = new ArrayList<>();
    }

    /**
     * Returns the maximum semester number among all subjects.
     *
     * @return the last semester number, or {@code 0} if the record book is empty
     */
    private int lastSemester() {
        if (this.subjects == null) {
            return 0;
        }

        OptionalInt ret = this.subjects.stream()
                .mapToInt(Subject::getSemester)
                .max();

        return ret.orElse(0);
    }

    /**
     * Checks whether the subject is a credit.
     *
     * @param subject the subject to check
     * @return {@code true} if the subject type is {@link SubjectType#CREDIT};
     *         otherwise {@code false}
     */
    private static boolean isCredit(Subject subject) {
        return subject.type() == SubjectType.CREDIT;
    }

    /**
     * Checks whether the subject is a qualifying work.
     *
     * @param subject the subject to check
     * @return {@code true} if the subject type is {@link SubjectType#QUALWORK};
     *         otherwise {@code false}
     */
    private static boolean isDiplomaWork(Subject subject) {
        return subject.type() == SubjectType.QUALWORK;
    }

    /**
     * Checks whether the subject has a numeric grade.
     *
     * @param subject the subject to check
     * @return {@code true} if the subject type is {@link SubjectType#DIFFCREDIT}
     *         or {@link SubjectType#EXAM}; otherwise {@code false}
     */
    private static boolean gradable(Subject subject) {
        return subject.type() == SubjectType.DIFFCREDIT
                || subject.type() == SubjectType.EXAM
                || subject.type() == SubjectType.QUALWORK;
    }

    /**
     * Adds a subject result to the record book.
     *
     * @param semester the semester number
     * @param name the subject name
     * @param type the subject type
     * @param mark the received mark
     */
    public void passSubject(int semester, String name, SubjectType type, Mark mark) {
        subjects.add(new Subject(semester, name, type, mark));
    }

    /**
     * Calculates the average grade over all gradable subjects.
     *
     * @return the average numeric grade, or {@code 0.0} if there are no gradable subjects
     */
    public double getAverageGrade() {
        return this.subjects.stream()
                .filter(RecordBook::gradable)
                .mapToInt(subject -> subject.getMark().getValue())
                .average()
                .orElse(0);
    }

    /**
     * Determines whether the student can continue on free studying.
     *
     * <p>The result is {@code true} when the record book contains at least two semesters,
     * all gradable subjects in the last and previous semesters have good marks, and all
     * credits are passed.</p>
     *
     * @return {@code true} if the student can continue on free studying; otherwise {@code false}
     */
    public boolean canGoOnFreeStudying() {
        int last = this.lastSemester();
        if (last < 2) {
            return false;
        }
        int prev = last - 1;
        boolean goodGrades = this.subjects.stream()
                .filter(RecordBook::gradable)
                .filter(subject -> subject.getSemester() >= prev)
                .allMatch(subject -> subject.getMark().isGood());

        boolean allCredits = this.subjects.stream()
                .filter(RecordBook::isCredit)
                .allMatch(subject -> subject.getMark().isGood());

        return goodGrades && allCredits;
    }

    /**
     * Determines whether the student can receive a red diploma.
     *
     * <p>The result is {@code true} when all credits are passed, no gradable subject has
     * mark {@link Mark#THREE}, the qualifying work is absent or marked {@link Mark#FIVE},
     * and, from the eighth semester onward, at least 75 percent of gradable subjects are
     * marked {@link Mark#FIVE}.</p>
     *
     * @return {@code true} if the student can receive a red diploma; otherwise {@code false}
     */
    public boolean canGetRedDiploma() {
        Map<String, Subject> latestGrades = this.subjects.stream()
                        .collect(Collectors.toMap(
                                Subject::getName,
                                s -> s,
                                BinaryOperator.maxBy(Comparator
                                        .comparingInt(Subject::getSemester))));

        boolean allCredits = this.subjects.stream()
                .filter(RecordBook::isCredit)
                .noneMatch(subject -> subject.getMark() == Mark.NO_CREDIT);

        boolean goodGrades = this.subjects.stream()
                .filter(RecordBook::gradable)
                .noneMatch(subject -> subject.getMark() == Mark.THREE);

        if (!(allCredits && goodGrades)) {
            return false;
        }

        boolean goodDiplomaWork = this.subjects.stream()
                .filter(RecordBook::isDiplomaWork)
                .anyMatch(subject -> subject.getMark().getValue() == 5)
                || this.subjects.stream()
                .noneMatch(RecordBook::isDiplomaWork);

        if (!goodDiplomaWork) {
            return false;
        }

        if (this.lastSemester() < 8) {
            return true;
        }

        long total = subjects.stream()
                .filter(RecordBook::gradable)
                .count();
        long fives = this.subjects.stream()
                .filter(subject -> subject.getMark() == Mark.FIVE)
                .count();
        double percent = total == 0 ? 0.0 : (fives * 100.0) / total;

        return percent >= 75;
    }

    /**
     * Determines whether the student can receive an increased scholarship.
     *
     * <p>The result is {@code true} when the record book contains at least two semesters
     * and all marks in the last semester are excellent.</p>
     *
     * @return {@code true} if the student
     *     can receive an increased scholarship; otherwise {@code false}.
     */
    public boolean canGetIncreasedScholarship() {
        int last = this.lastSemester();
        if (last < 2) {
            return false;
        }
        return this.subjects.stream()
                .filter(subject -> subject.getSemester() == last)
                .allMatch(subject -> subject.getMark().isExcellent());
    }
}
