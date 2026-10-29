package sys.pro;

public record Subject(int semester, String name, SubjectType type, Mark mark) {
    public Subject {
        if (semester < 1 || semester > 8) {
            throw new IllegalArgumentException("Got wrong semester number.");
        }
    }

    public Mark getMark() {
        return this.mark;
    }

    public int getSemester() {
        return this.semester;
    }

    @Override
    public SubjectType type() {
        return type;
    }

    public String getName() {
        return this.name;
    }
}
