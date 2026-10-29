package sys.pro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link RecordBook}.
 */
class RecordBookTest {

    private RecordBook book;

    /**
     * Creates a fresh {@link RecordBook} before each test.
     */
    @BeforeEach
    void setUp() {
        book = new RecordBook();
    }

    /**
     * Convenience helper that adds a subject result to the record book.
     *
     * @param semester the semester number
     * @param name the subject name
     * @param type the subject type
     * @param mark the received mark
     */
    private void pass(int semester, String name, SubjectType type, Mark mark) {
        book.passSubject(semester, name, type, mark);
    }


    /**
     * Verifies that passing a subject adds it to the record book with the given data.
     */
    @Test
    void passSubjectAddsSubject() {
        pass(3, "Math", SubjectType.EXAM, Mark.FIVE);

        assertEquals(1, book.subjects.size());
        Subject subject = book.subjects.getFirst();
        assertEquals(3, subject.getSemester());
        assertEquals("Math", subject.getName());
        assertEquals(SubjectType.EXAM, subject.type());
        assertEquals(Mark.FIVE, subject.getMark());
    }

    /**
     * Verifies that the average grade of an empty record book is zero.
     */
    @Test
    void averageGradeEmptyBookIsZero() {
        assertEquals(0.0, book.getAverageGrade(), 1e-9);
    }

    /**
     * Verifies that the average grade includes only gradable subjects.
     */
    @Test
    void averageGradeUsesOnlyGradableSubjects() {
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);
        pass(1, "DiffCredit", SubjectType.DIFFCREDIT, Mark.FOUR);
        pass(1, "Credit", SubjectType.CREDIT, Mark.CREDIT);
        pass(1, "QualWork", SubjectType.QUALWORK, Mark.FIVE);

        assertEquals((double) 14 / 3, book.getAverageGrade(), 1e-9);
    }

    /**
     * Verifies that free studying is not available for the first semester.
     */
    @Test
    void canGoOnFreeStudyingFalseForFirstSemester() {
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGoOnFreeStudying());
    }

    /**
     * Verifies that free studying is available when grades in the last and previous
     * semesters are good and all credits are passed.
     */
    @Test
    void canGoOnFreeStudyingTrueWhenLastAndPreviousGradesAreGoodAndCreditsAreGood() {
        pass(1, "Credit", SubjectType.CREDIT, Mark.CREDIT);
        pass(1, "Exam 1", SubjectType.EXAM, Mark.FIVE);
        pass(2, "DiffCredit 2", SubjectType.DIFFCREDIT, Mark.FOUR);

        assertTrue(book.canGoOnFreeStudying());
    }

    /**
     * Verifies that free studying is unavailable when a gradable subject in the
     * checked semesters has mark {@link Mark#THREE}.
     */
    @Test
    void canGoOnFreeStudyingFalseWhenGradableHasThreeInCheckedSemesters() {
        pass(1, "Exam 1", SubjectType.EXAM, Mark.THREE);
        pass(2, "Exam 2", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGoOnFreeStudying());
    }

    /**
     * Verifies that free studying is unavailable when any credit has mark
     * {@link Mark#NO_CREDIT}.
     */
    @Test
    void canGoOnFreeStudyingFalseWhenAnyCreditIsNoCredit() {
        pass(1, "Credit", SubjectType.CREDIT, Mark.NO_CREDIT);
        pass(2, "Exam 2", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGoOnFreeStudying());
    }

    /**
     * Verifies that free studying ignores gradable subjects before the previous semester.
     */
    @Test
    void canGoOnFreeStudyingIgnoresGradableBeforePreviousSemester() {
        pass(1, "Exam 1", SubjectType.EXAM, Mark.THREE);
        pass(2, "Exam 2", SubjectType.EXAM, Mark.FOUR);
        pass(3, "Exam 3", SubjectType.EXAM, Mark.FIVE);

        assertTrue(book.canGoOnFreeStudying());
    }

    /**
     * Verifies that a red diploma is available for a good record book before the eighth semester.
     */
    @Test
    void canGetRedDiplomaTrueForGoodBookBeforeEighthSemester() {
        pass(1, "Credit", SubjectType.CREDIT, Mark.CREDIT);
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);
        pass(1, "QualWork", SubjectType.QUALWORK, Mark.FIVE);

        assertTrue(book.canGetRedDiploma());
    }

    /**
     * Verifies that a red diploma
     * is available without a qualifying work before the eighth semester.
     */
    @Test
    void canGetRedDiplomaTrueWithoutQualWorkBeforeEighthSemester() {
        pass(1, "Credit", SubjectType.CREDIT, Mark.CREDIT);
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);

        assertTrue(book.canGetRedDiploma());
    }

    /**
     * Verifies that a red diploma is unavailable when a credit has mark
     * {@link Mark#NO_CREDIT}.
     */
    @Test
    void canGetRedDiplomaFalseWhenCreditIsNoCredit() {
        pass(1, "Credit", SubjectType.CREDIT, Mark.NO_CREDIT);
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGetRedDiploma());
    }

    /**
     * Verifies that a red diploma is unavailable when a gradable subject has mark
     * {@link Mark#THREE}.
     */
    @Test
    void canGetRedDiplomaFalseWhenGradableHasThree() {
        pass(1, "Exam 1", SubjectType.EXAM, Mark.THREE);
        pass(2, "Exam 2", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGetRedDiploma());
    }

    /**
     * Verifies that a red diploma is unavailable when the qualifying work is not marked
     * {@link Mark#FIVE}.
     */
    @Test
    void canGetRedDiplomaFalseWhenQualWorkIsNotFive() {
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);
        pass(1, "QualWork", SubjectType.QUALWORK, Mark.FOUR);

        assertFalse(book.canGetRedDiploma());
    }

    /**
     * Verifies that a red diploma is available at exactly 75 percent from the eighth semester.
     */
    @Test
    void canGetRedDiplomaTrueAtExactly75PercentFromEighthSemester() {
        pass(8, "Exam A", SubjectType.EXAM, Mark.FIVE);
        pass(8, "DiffCredit B", SubjectType.DIFFCREDIT, Mark.FIVE);
        pass(8, "Exam C", SubjectType.EXAM, Mark.FIVE);
        pass(8, "DiffCredit D", SubjectType.DIFFCREDIT, Mark.FOUR);

        assertTrue(book.canGetRedDiploma());
    }

    /**
     * Verifies that a red diploma is available at more than 75 percent from the eighth semester.
     */
    @Test
    void canGetRedDiplomaTrueAtMoreThan75PercentFromEighthSemester() {
        pass(8, "Exam A", SubjectType.EXAM, Mark.FIVE);
        pass(8, "DiffCredit B", SubjectType.DIFFCREDIT, Mark.FIVE);
        pass(8, "Exam C", SubjectType.EXAM, Mark.FIVE);
        pass(8, "DiffCredit D", SubjectType.DIFFCREDIT, Mark.FIVE);

        assertTrue(book.canGetRedDiploma());
    }

    /**
     * Verifies that a red diploma is unavailable below 75 percent from the eighth semester.
     */
    @Test
    void canGetRedDiplomaFalseBelow75PercentFromEighthSemester() {
        pass(8, "Exam A", SubjectType.EXAM, Mark.FIVE);
        pass(8, "DiffCredit B", SubjectType.DIFFCREDIT, Mark.FIVE);
        pass(8, "Exam C", SubjectType.EXAM, Mark.FOUR);
        pass(8, "DiffCredit D", SubjectType.DIFFCREDIT, Mark.FOUR);

        assertFalse(book.canGetRedDiploma());
    }

    /**
     * Verifies that a red diploma is available if there is no grades yet.
     */
    @Test
    void testIsEligibleForRedDiploma_Empty() {
        assertTrue(book.canGetRedDiploma());
    }

    /**
     * Verifies that an increased scholarship is unavailable for the first semester.
     */
    @Test
    void canGetIncreasedScholarshipFalseForFirstSemester() {
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGetIncreasedScholarship());
    }

    /**
     * Verifies that an increased scholarship is available when all marks in the last
     * semester are excellent.
     */
    @Test
    void canGetIncreasedScholarshipTrueWhenAllLastSemesterMarksAreExcellent() {
        pass(1, "Old Exam", SubjectType.EXAM, Mark.THREE);
        pass(2, "Credit", SubjectType.CREDIT, Mark.CREDIT);
        pass(2, "Exam", SubjectType.EXAM, Mark.FIVE);

        assertTrue(book.canGetIncreasedScholarship());
    }

    /**
     * Verifies that an increased scholarship is unavailable when the last semester
     * contains a non-excellent mark.
     */
    @Test
    void canGetIncreasedScholarshipFalseWhenLastSemesterHasNotExcellentMark() {
        pass(2, "Exam", SubjectType.EXAM, Mark.FOUR);

        assertFalse(book.canGetIncreasedScholarship());
    }

    /**
     * Verifies that an increased scholarship is unavailable when a credit in the last
     * semester has mark {@link Mark#NO_CREDIT}.
     */
    @Test
    void canGetIncreasedScholarshipFalseWhenLastSemesterCreditIsNoCredit() {
        pass(2, "Credit", SubjectType.CREDIT, Mark.NO_CREDIT);
        pass(2, "Exam", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGetIncreasedScholarship());
    }
}