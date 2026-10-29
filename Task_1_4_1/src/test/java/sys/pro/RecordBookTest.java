package sys.pro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RecordBookTest {

    private RecordBook book;

    @BeforeEach
    void setUp() {
        book = new RecordBook();
    }

    private void pass(int semester, String name, SubjectType type, Mark mark) {
        book.passSubject(semester, name, type, mark);
    }

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

    @Test
    void averageGradeEmptyBookIsZero() {
        assertEquals(0.0, book.getAverageGrade(), 1e-9);
    }

    @Test
    void averageGradeUsesOnlyGradableSubjects() {
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);
        pass(1, "DiffCredit", SubjectType.DIFFCREDIT, Mark.FOUR);
        pass(1, "Credit", SubjectType.CREDIT, Mark.CREDIT);
        pass(1, "QualWork", SubjectType.QUALWORK, Mark.FIVE);

        assertEquals(4.5, book.getAverageGrade(), 1e-9);
    }

    @Test
    void canGoOnFreeStudyingFalseForFirstSemester() {
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGoOnFreeStudying());
    }

    @Test
    void canGoOnFreeStudyingTrueWhenLastAndPreviousGradesAreGoodAndCreditsAreGood() {
        pass(1, "Credit", SubjectType.CREDIT, Mark.CREDIT);
        pass(1, "Exam 1", SubjectType.EXAM, Mark.FIVE);
        pass(2, "DiffCredit 2", SubjectType.DIFFCREDIT, Mark.FOUR);

        assertTrue(book.canGoOnFreeStudying());
    }

    @Test
    void canGoOnFreeStudyingFalseWhenGradableHasThreeInCheckedSemesters() {
        pass(1, "Exam 1", SubjectType.EXAM, Mark.THREE);
        pass(2, "Exam 2", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGoOnFreeStudying());
    }

    @Test
    void canGoOnFreeStudyingFalseWhenAnyCreditIsNoCredit() {
        pass(1, "Credit", SubjectType.CREDIT, Mark.NO_CREDIT);
        pass(2, "Exam 2", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGoOnFreeStudying());
    }

    @Test
    void canGoOnFreeStudyingIgnoresGradableBeforePreviousSemester() {
        pass(1, "Exam 1", SubjectType.EXAM, Mark.THREE);
        pass(2, "Exam 2", SubjectType.EXAM, Mark.FOUR);
        pass(3, "Exam 3", SubjectType.EXAM, Mark.FIVE);

        assertTrue(book.canGoOnFreeStudying());
    }

    @Test
    void canGetRedDiplomaTrueForGoodBookBeforeEighthSemester() {
        pass(1, "Credit", SubjectType.CREDIT, Mark.CREDIT);
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);
        pass(1, "QualWork", SubjectType.QUALWORK, Mark.FIVE);

        assertTrue(book.canGetRedDiploma());
    }

    @Test
    void canGetRedDiplomaTrueWithoutQualWorkBeforeEighthSemester() {
        pass(1, "Credit", SubjectType.CREDIT, Mark.CREDIT);
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);

        assertTrue(book.canGetRedDiploma());
    }

    @Test
    void canGetRedDiplomaFalseWhenCreditIsNoCredit() {
        pass(1, "Credit", SubjectType.CREDIT, Mark.NO_CREDIT);
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGetRedDiploma());
    }

    @Test
    void canGetRedDiplomaFalseWhenGradableHasThree() {
        pass(1, "Exam 1", SubjectType.EXAM, Mark.THREE);
        pass(2, "Exam 2", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGetRedDiploma());
    }

    @Test
    void canGetRedDiplomaFalseWhenQualWorkIsNotFive() {
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);
        pass(1, "QualWork", SubjectType.QUALWORK, Mark.FOUR);

        assertFalse(book.canGetRedDiploma());
    }

    @Test
    void canGetRedDiplomaTrueAtExactly75PercentFromEighthSemester() {
        pass(8, "Exam A", SubjectType.EXAM, Mark.FIVE);
        pass(8, "DiffCredit B", SubjectType.DIFFCREDIT, Mark.FIVE);
        pass(8, "Exam C", SubjectType.EXAM, Mark.FIVE);
        pass(8, "DiffCredit D", SubjectType.DIFFCREDIT, Mark.FOUR);

        assertTrue(book.canGetRedDiploma());
    }

    @Test
    void canGetRedDiplomaFalseBelow75PercentFromEighthSemester() {
        pass(8, "Exam A", SubjectType.EXAM, Mark.FIVE);
        pass(8, "DiffCredit B", SubjectType.DIFFCREDIT, Mark.FIVE);
        pass(8, "Exam C", SubjectType.EXAM, Mark.FOUR);
        pass(8, "DiffCredit D", SubjectType.DIFFCREDIT, Mark.FOUR);

        assertFalse(book.canGetRedDiploma());
    }

    @Test
    void canGetIncreasedScholarshipFalseForFirstSemester() {
        pass(1, "Exam", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGetIncreasedScholarship());
    }

    @Test
    void canGetIncreasedScholarshipTrueWhenAllLastSemesterMarksAreExcellent() {
        pass(1, "Old Exam", SubjectType.EXAM, Mark.THREE);
        pass(2, "Credit", SubjectType.CREDIT, Mark.CREDIT);
        pass(2, "Exam", SubjectType.EXAM, Mark.FIVE);

        assertTrue(book.canGetIncreasedScholarship());
    }

    @Test
    void canGetIncreasedScholarshipFalseWhenLastSemesterHasNotExcellentMark() {
        pass(2, "Exam", SubjectType.EXAM, Mark.FOUR);

        assertFalse(book.canGetIncreasedScholarship());
    }

    @Test
    void canGetIncreasedScholarshipFalseWhenLastSemesterCreditIsNoCredit() {
        pass(2, "Credit", SubjectType.CREDIT, Mark.NO_CREDIT);
        pass(2, "Exam", SubjectType.EXAM, Mark.FIVE);

        assertFalse(book.canGetIncreasedScholarship());
    }
}