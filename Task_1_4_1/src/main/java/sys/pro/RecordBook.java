package sys.pro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class RecordBook {
    ArrayList<Subject> subjects;

    RecordBook() {
        this.subjects = new ArrayList<>();
    }

    private int lastSemester() {
        if (this.subjects == null) {
            return 0;
        }

        OptionalInt ret = this.subjects.stream()
                .mapToInt(Subject::getSemester)
                .max();

        return ret.orElse(0);
    }

    private static boolean isCredit(Subject subject) {
        return subject.type() == SubjectType.CREDIT;
    }

    private static boolean isDiplomaWork(Subject subject) {
        return subject.type() == SubjectType.QUALWORK;
    }

    private static boolean gradable(Subject subject) {
        return subject.type() == SubjectType.DIFFCREDIT
                || subject.type() == SubjectType.EXAM;
    }

    public void passSubject(int semester, String name, SubjectType type, Mark mark) {
        subjects.add(new Subject(semester, name, type, mark));
    }

    public double getAverageGrade() {
        return this.subjects.stream()
                .filter(RecordBook::gradable)
                .mapToInt(subject -> subject.getMark().getValue())
                .average()
                .orElse(0);
    }

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

    public boolean canGetRedDiploma() {
        Map<String, Subject> latestGrades = this.subjects.stream()
                        .collect(Collectors.toMap(
                                Subject::getName,
                                s -> s,
                                BinaryOperator.maxBy(Comparator.comparingInt(Subject::getSemester))));

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
