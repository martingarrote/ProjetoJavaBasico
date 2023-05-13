import builders.StudentsBuilder;
import entities.Student;

import java.util.List;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        var allStudents = StudentsBuilder.getAllStudents();

        getApprovedStudents(allStudents);
        getReprovedStudents(allStudents);
        getMaxGradeStudents(allStudents);
    }

    static void getApprovedStudents(List<Student> studentsList) {
        for (Student s : studentsList) {
            var average = (s.getTestOne() + s.getTestTwo() + s.getTestThree())/3;
            if (average >= 7) {
                System.out.printf("%d - %s : Média = %.2f\n", s.getCode(), s.getName(), average);
            }
        }
    }

    static void getReprovedStudents(List<Student> studentsList) {
        for (Student s : studentsList) {
            var average = (s.getTestOne() + s.getTestTwo() + s.getTestThree())/3;
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
            System.out.println("Estudantes que obtiveram nota máxima:");
            for (Student s : studentsWithMaxGrade) {
                System.out.printf("%d - %s\n", s.getCode(), s.getName());
            }
        } else {
            System.out.println("Nenhum estudante obteve nota máxima");
        }
    }
}
