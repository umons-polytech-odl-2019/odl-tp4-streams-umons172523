package be.ac.umons.exercice3;

import be.ac.umons.exercice2.Student;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Classroom {
    private Set<Student> students = new HashSet<>();

    public void addStudent(Student student) {
        Objects.requireNonNull(student, "student may not be null");

        if (students.contains(student)) {
            throw new DuplicateStudentException(student.getRegistrationNumber());
        } else {
            students.add(student);
        }

    }

    public double averageScore()
    {
        return students.stream()
                .flatMapToInt(student -> student.getScoreByCourse().values().stream().mapToInt(Integer::intValue))//1)
                .average()
                .orElse(0.0);
                // on fait un flux d'etudiant
                // a chaque etudiant on fait une map et on crée donc un nvx  flux
                // on fait correspondre a chaque elment la valeur
                // de ces valeur on fait un nvx stream pour pouvoir traiter les données
                 //on transfomre les valeurs en int
                // la ligne1) donne au final un entier
                //.flatMapToInt() pour associer le resultat d'un student avec un flux (on l applanit)
                //on a au final une serie de valeur entiere dont on calcul la moyenne et si pas de valeur on renvoie 0.0(double)
                //----->pour s'assurrer que la valeur retourne tjs qqchose


                //note si on fait un programme et qu'on veut retourner une erreur on note -1

      /*  double sum = 0;
        int cpt = 0;
        for (Student student : students) {
            for (Map.Entry<String, Integer> courses : student.getScoreByCourse().entrySet()) {
                sum += courses.getValue();
                cpt++;
            }
        }
        return (sum / cpt);

       */
    }

    public int countStudents() {
        return students.size();
    }

    public List<Student> topScorers(String course, int n) {

        Map<Student, OptionalInt> scoreByStudent = students.stream()
            .collect(Collectors.toMap(Function.identity(), student -> student.getScore(course)));
        return scoreByStudent.entrySet().stream()
            .filter(entry -> entry.getValue().isPresent())
            .sorted(Map.Entry.comparingByValue((o1, o2) -> -Integer.compare(o1.getAsInt(), o2.getAsInt())))
            .limit(n)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    public List<Student> successfulStudents()
    {
        return students.stream()
                .filter(Student::isSuccessful)
                .sorted(Comparator.comparingDouble(student -> -student.averageScore()))
                .collect(Collectors.toList());

        // on filtre les etudiants qui ont réussi, on utilise un booléens avec isSuccessful
        //-student.averageScore() on a mis ça pour avoir un ordre décroissant facilement
        //on rassemble les résultats, on transforme en liste d'élément

        /* Set<Student> studentSet = new TreeSet<>(
                Comparator.comparingDouble(student -> -student.averageScore()));

        for (Student s : students) {
            if (s.isSuccessful()) {
                studentSet.add(s);
            }
        }

        List<Student> studentList = new ArrayList<>();
        for (Student s : studentSet)
            studentList.add(s);
        return studentList;

        */

    }
}
