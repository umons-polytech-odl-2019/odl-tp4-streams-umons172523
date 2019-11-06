package be.ac.umons.exercice2;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class Student
{

    private final String name;
    private final String registrationNumber;
    private Map<String, Integer> scoreByCourse = new HashMap<>();

    public Student(String name, String registrationNumber) {
        this.name = requireNonNull(name, "name may not be null");
        this.registrationNumber = requireNonNull(registrationNumber, "registration number may not be null");
    }

    public void setScore(String course, int score)
    {
        requireNonNull(course, "course may not be null");
        if (score < 0 || score > 20)
            throw new IllegalArgumentException("score must be between 0 and 20");
        scoreByCourse.put(course, score);
    }

    public OptionalInt getScore(String course)
    {
        Integer nullableScore = scoreByCourse.get(course);
        return nullableScore != null ? OptionalInt.of(nullableScore) : OptionalInt.empty();
    }

    public double averageScore()
    {
        return scoreByCourse.values().stream().mapToInt(Integer::intValue).average().orElse(0.0);
        // on récupère les valeurs de la map (m.values()) et puis on les transforme en flux
        // et c'est valeur on les transforme en entier .mapToInt(Integer::intValue)
        //on calcule la moyenne
        //on pourrait préciser que c'est un double qu'on récupère .getDouble()
        //mais on préfère préciser que si la valeur est fausse on retourne 0

        /*int count = 0;
        double totalScore = 0.0;
        for (Integer score : scoreByCourse.values())
         {
            count++;
            totalScore += score;
        }
        return totalScore / count;

         */
    }

    public String bestCourse()
    {
        return scoreByCourse.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .findFirst().map(Map.Entry::getKey).toString();
                //on recupere les données, les valeurs de la mapSet
                // on trie par ordre decroissant avec .sorted()
                // Note: si on ne met rien dans les () il fait un tri croisssant
                //Map.Entry.comparingByValue( permet de faire un operateur pour comparer les valeurs),
                // les couples de la map sont comparé 2à2
                // on utilise l operateur sur un ordre inverse
                // en clair on fait un tri
                // .findFirst() On cherche donc le 1er celui qui a donc le meilleur score
                //.map(Map.Entry::getKey) On récupere la clé de celui qu'on a trouvé(le 1er)
                //On transforme en string car on avait des valeurs

        /*String bestCourse = "";
        Integer bestScore = 0;

        for (Map.Entry<String, Integer> e : scoreByCourse.entrySet())
        {
            if (e.getValue() > bestScore) {
                bestCourse = e.getKey();
                bestScore = e.getValue();
            }
        }

        return bestCourse;
         */
    }

    public int bestScore()
    {
        return scoreByCourse.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .findFirst().map(Map.Entry::getValue).orElse(0);
        //On fait la meme chose que prédédemment
        //on prend les entrées
        //on transforme en flux
        //on trie et on compare dans le sens inverse
        // on prend le 1er qui est le meilleur score
        //on prend la clés de celui qu'on a trouvé
        // on précise que si ça fct pas on retourne 0(attention pas0.0 car ce n'est pas un double)
        //pas besoin de mettre mapToInt() car on a d'office un int


        /*int bestScore = 0;
        for (Map.Entry<String, Integer> entry : scoreByCourse.entrySet()) {
            if (entry.getValue() > bestScore)
                bestScore = entry.getValue();
        }
        return bestScore;

         */

    }

    public Set<String> failedCourses()
    {
       return scoreByCourse.entrySet().stream()
               .filter(entry->entry.getValue()<12)
               //.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) pas besoin
               .map(Map.Entry::getKey)
               .collect(Collectors.toCollection(LinkedHashSet::new));
            // on prend les données
            //on crée un flux
            //on garde les éléments<12 ====> en clair on filtre
            //on récupere les clés
            //.map operation de correspondance différent de Map = la map
            //on transforme la collection générique en string (detecter implicitement) on pourrait le spéifier en mettant linkedHashSet<string>
            //car on a créée une nvlle collection de type Hashset
            //LinkedHashSet garde ordre d'insertion
        /*
        List<Map.Entry<String, Integer>> filteredEntries = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : scoreByCourse.entrySet()) {
            if (entry.getValue() < 12) {
                filteredEntries.add(entry);
            }
        }
        Collections.sort(filteredEntries, (o1, o2) -> -o1.getValue().compareTo(o2.getValue()));
        LinkedHashSet<String> failedCourses = new LinkedHashSet<>();
        for (Map.Entry<String, Integer> entry : filteredEntries) {
            failedCourses.add(entry.getKey());
        }
        return failedCourses;

         */


    }

    public boolean isSuccessful() {
        return averageScore() >= 12 && failedCourses().size() < 3;
    }

    public Set<String> attendedCourses()
    {
        return scoreByCourse.keySet().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
        //LinkedHashSet garde ordre d'insertion comme ici string trie alphabétique de la classe de base
        /*
        Set<String> courses = new LinkedHashSet<String>();
        for (String courseName : scoreByCourse.keySet())
            courses.add(courseName);
        return courses;

         */
    }

    public String getName() {
        return name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Map<String, Integer> getScoreByCourse() {
        return scoreByCourse;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getName());
        sb.append(" (").append(getRegistrationNumber()).append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(registrationNumber, student.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }
}
