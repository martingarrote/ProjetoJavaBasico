import builders.StudentsBuilder;
import entities.Student;

import java.util.*;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        var allStudents = StudentsBuilder.getAllStudents();

        getApprovedStudents(allStudents);
        getReprovedStudents(allStudents);
        getMaxGradeStudents(allStudents);
        getLowestGradeStudents(allStudents);
        getStudentsAverage(allStudents);
    }

    static void getApprovedStudents(List<Student> studentsList) {
        for (Student s : studentsList) {
            var average = (s.getTestOne() + s.getTestTwo() + s.getTestThree()) / 3;
            if (average >= 7) {
                System.out.printf("%d - %s : Média = %.2f\n", s.getCode(), s.getName(), average);
            }
        }
    }

    static void getReprovedStudents(List<Student> studentsList) {
        for (Student s : studentsList) {
            var average = (s.getTestOne() + s.getTestTwo() + s.getTestThree()) / 3;
            var missing = 7 - average;
            if (average < 7) {
                System.out.printf("%d - %s : Média = %.2f (Faltou = %.2f)\n", s.getCode(), s.getName(), average, missing);
            }
        }
    }

    static void getMaxGradeStudents(List<Student> studentsList) {
        Predicate<Student> maxGrade = (Student s) -> {
            return (s.getTestOne() == 10) || (s.getTestTwo() == 10) || (s.getTestThree() == 10);
        };
        var studentsWithMaxGrade = studentsList.stream().filter(maxGrade).toList();
        if (studentsWithMaxGrade.size() > 0) {
            for (Student s : studentsWithMaxGrade) {
                System.out.printf("%d - %s\n", s.getCode(), s.getName());
            }
        } else {
            System.out.println("Nenhum estudante obteve nota máxima");
        }
    }

    static float getLowestGrade(Student s) {
        var lowestGrade = s.getTestOne();
        if (s.getTestTwo() < lowestGrade) {
            lowestGrade = s.getTestTwo();
        }
        if (s.getTestThree() < lowestGrade) {
            lowestGrade = s.getTestThree();
        }
        return lowestGrade;
    }

    static void getLowestGradeStudents(List<Student> studentsList) {

        var lowestGradeStudents = new ArrayList<Student>();
        var lowestGrade = getLowestGrade(studentsList.get(0));

        for (Student s : studentsList) {
            var studentGrade = getLowestGrade(s);
            if (studentGrade < lowestGrade) {
                lowestGradeStudents.clear();
                lowestGradeStudents.add(s);
            } else if (studentGrade == lowestGrade) {
                lowestGradeStudents.add(s);
            }
        }
        for (Student s : lowestGradeStudents) {
            System.out.printf("%d - %s : Nota: %.2f\n", s.getCode(), s.getName(), getLowestGrade(s));
        }
    }

    static int compareStudentsAverage(Student s1, Student s2) {
        var s1Average = (s1.getTestOne() + s1.getTestTwo() + s1.getTestThree()) / 3;
        var s2Average = (s2.getTestOne() + s2.getTestTwo() + s2.getTestThree()) / 3;
        if (s1Average > s2Average) {
            return 1;
        } else if (s1Average < s2Average) {
            return -1;
        }
        return 0;
    }

    static void getStudentsAverage(List<Student> studentsList) {
        var orderedStudentsList = new ArrayList<>(studentsList);
        orderedStudentsList.sort(Main::compareStudentsAverage);

        for (Student s : orderedStudentsList) {
            var average = (s.getTestOne() + s.getTestTwo() + s.getTestThree()) / 3;
            System.out.printf("Posição %d - %d - %s : Média = %.2f\n",
                    orderedStudentsList.indexOf(s) + 1, s.getCode(), s.getName(), average);
        }
    }
}
