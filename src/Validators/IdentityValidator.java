package Validators;

import Exceptions.InvalidIdentificationException;
import java.util.ArrayList;

public class IdentityValidator {
    private static final ArrayList<String> identity = new ArrayList<>();

    public static boolean validate(String id) throws InvalidIdentificationException
    {
        if( (id.isEmpty()) || (id.startsWith(" ")) )
            throw new InvalidIdentificationException("given id:'" + id + "' is not valid identity.");
        if (identity.contains(id))
            throw new InvalidIdentificationException("given id:'" + id +"' is already in use");
        return true;
    }

    public static void insert(String id) { identity.add(id); }
    public static void remove(String id) { identity.remove(id); }
}
