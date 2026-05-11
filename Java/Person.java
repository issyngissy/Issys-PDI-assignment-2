/*********************************************************
 * Author: David McMeekin                                *
 * Date: 15 Apr 2021                                     *
 * Purpose: A Person for use in PDI                      *
 * NOTES: This is a basic implementation of a Person.    *
 * It is not a complete class as it does not actually    *
 * validate any of the input into the class fields.      *
 * As you use this class it would be appropriate to make *
 * changes to it so that validation does occur.          *
 *********************************************************/

import java.util.*;

public class Person
{   // Class fields
    private String firstName;
    private String familyName;
    private int dayOfBirth;
    private int birthMonth;
    private int birthYear;

    // With Parameters Constructor
    public Person(String pFirstName, String pFamilyName, int pDayOfBirth, int pBirthMonth, int pBirthYear)
    {
        firstName  = pFirstName;
        familyName = pFamilyName;
        dayOfBirth = pDayOfBirth;
        birthMonth = pBirthMonth;
        birthYear  = pBirthYear;
    }
    // Copy Constructor
    public Person(Person pPerson)
    {
        firstName  = pPerson.getFirstName();
        familyName = pPerson.getFamilyName();
        dayOfBirth = pPerson.getDayOfBirth();
        birthMonth = pPerson.getBirthMonth();
        birthYear  = pPerson.getBirthYear();
    }

    // Default Constructor
    public Person()
    {
        firstName  = "Mohamed";
        familyName = "Sallah";
        dayOfBirth = 15;
        birthMonth = 6;
        birthYear  = 1992;
    }

    // Following are the Accessors (getters)
    public String getFirstName()
    {
        return firstName;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public int getDayOfBirth()
    {
        return dayOfBirth;
    }

    public int getBirthMonth()
    {
        return birthMonth;
    }

    public int getBirthYear()
    {
        return birthYear;
    }

    //Following are the Mutators (setters)
    public void setFirstName(String pFirstName)
    {
        firstName = pFirstName;
    }

    public void setFamilyName(String pFamilyName)
    {
        familyName = pFamilyName;
    }

    public void setDayOfBirth(int pDayOfBirth)
    {
        dayOfBirth = pDayOfBirth; // How could this be validated?
    }

    public void setBirthMonth(int pBirthMonth)
    {
        birthMonth = pBirthMonth; // How could this be validated?
    }

    public void setBirthYear(int pBirthYear)
    {
        birthYear = pBirthYear; // How could this be validated?
    }

    public boolean equals(Object inObject) 
    { 
        boolean isEqual = false; 
        Person inPerson = null;
        if(inObject instanceof Person)
        {
            inPerson = (Person)inObject;
            if(firstName.equals(inPerson.getFirstName()))
              if(familyName.equals(inPerson.getFamilyName()))
                if(dayOfBirth == inPerson.getDayOfBirth())
                  if(birthMonth == inPerson.getBirthMonth())
                    if(birthYear == inPerson.getBirthYear())
                    isEqual = true; 
        }
        return isEqual; 
    }

    public String toString()
    {
        String personString;
        personString = "First name is " + firstName + "\nFamily name is " + familyName + "\nDate of birth is " + dayOfBirth + "/" + birthMonth + "/" + birthYear;
        return personString;
    }
}
