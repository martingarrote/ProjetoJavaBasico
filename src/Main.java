import builders.StudentsBuilder;
import entities.Student;

import java.util.List;

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
                System.out.printf("%d - %s : Média = %f\n", s.getCode(), s.getName(), average);
            }
        }
    }

    static void getReprovedStudents(List<Student> studentList) {
        for (Student s : studentList) {
            var average = (s.getTestOne() + s.getTestTwo() + s.getTestThree())/3;
            var missing = 7 - average;
            if (average < 7) {
                System.out.printf("%d - %s : Média = %f (Faltou = %f)\n", s.getCode(), s.getName(), average, missing);
            }
        }
    }

    static void getMaxGradeStudents(List<Student> studentList) {
        var count = 0;
        for (Student s : studentList) {
            var average = (s.getTestOne() + s.getTestTwo() + s.getTestThree())/3;
            if (average == 10) {
                count++;
                System.out.printf("%d - %s\n", s.getCode(), s.getName());
            }
        }
        if (count == 0) {
            System.out.println("Nenhum estudante alcançou a nota máxima.");
        }
    }
}
