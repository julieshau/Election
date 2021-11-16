/**
 * Klasa abstrakcyjna osoba ಠ_ಠ .
 */
public abstract class Person {
    protected String name;
    protected String surname;
    protected int district;

    public Person(String name, String surname, int district){
        this.name = name;
        this.surname = surname;
        this.district = district;
    }

    public abstract String toString();
}
