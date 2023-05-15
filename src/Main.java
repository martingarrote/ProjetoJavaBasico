import builders.StudentsBuilder;
import entities.Student;

import java.util.*;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        var allStudents = StudentsBuilder.getAllStudents();

        var userInput = new Scanner(System.in);

        var working = true;

        while (working) {
            System.out.println(
                    """
                            \n1 - Obter os estudantes que foram aprovados, ou seja, obtiveram média igual ou superior a 7.0
                            2 - Obter os estudantes que foram reprovados, ou seja, não alcançaram a média
                            3 - Obter os estudantes que obtiveram nota máxima
                            4 - Obter o estudante que obteve a menor nota
                            5 - Obter o top 3 de estudantes com maiores notas
                            6 - Obter o top 3 de estudantes com menores notas
                            7 - Obter a média de todos os estudantes em ordem decrescente
                            8 - Sair
                            """
            );
            System.out.println("O que você deseja fazer?");

            var actionNumber = userInput.nextInt();
            switch (actionNumber) {
                case 1 -> getApprovedStudents(allStudents);
                case 2 -> getReprovedStudents(allStudents);
                case 3 -> getMaxGradeStudents(allStudents);
                case 4 -> getLowestGradeStudents(allStudents);
                case 5, 6 -> System.out.println("Não implementado");
                case 7 -> getStudentsAverage(allStudents);
                case 8 -> {
                    System.out.println("Encerrando...");
                    working = false;
                }
                default -> System.out.println("Número inválido, tente novamente.");
            }
        }
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

    static float getHighestGrade(Student s) {
        var highestGrade = s.getTestOne();
        if (s.getTestTwo() > highestGrade) {
            highestGrade = s.getTestTwo();
        }
        if (s.getTestThree() > highestGrade) {
            highestGrade = s.getTestThree();
        }
        return highestGrade;
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

    static int compareHighestStudentGrades(Student s1, Student s2) {
        var s1Highest = getHighestGrade(s1);
        var s2Highest = getHighestGrade(s2);

        if (s1Highest > s2Highest) {
            return 1;
        } else if (s1Highest < s2Highest) {
            return -1;
        }
        return 0;
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
