package com.hp.hpl.sparta;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/* compiled from: Document */
class EmptyEnumeration implements Enumeration {
    public boolean hasMoreElements() {
        return false;
    }

    EmptyEnumeration() {
    }

    public Object nextElement() {
        throw new NoSuchElementException();
    }
}
