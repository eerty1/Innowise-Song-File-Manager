package com.innowise.model;

public final class ValidationConstraintsMessages {

    /*
    I know, that it is not a good practice to create "constants classes", but in my use case I think it is a suitable solution.
    This class won't grow significantly with further development, since it already covers most of the possible validation cases.
    Moreover, splitting these messages among domain classes smells, because I simply copy-paste them changing only one word
    */

    public static final String NOT_BLANK = "must not be blank";
    public static final String NOT_NULL = "must not be null";
    public static final String NOT_IN_RANGE = "not in range for {min} to {max}";

    private ValidationConstraintsMessages() {
    }
}

