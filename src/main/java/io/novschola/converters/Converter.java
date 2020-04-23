package io.novschola.converters;

/**
 * Converter interface
 *
 * @param <F> Converter convert from this class
 * @param <T> Converter convert to this class
 * @author Kacper Szot
 */
public interface Converter<F, T> {
    /**
     * Method converts from class t to class f
     *
     * @param from object to convert
     * @return object of class T, converted from argument
     */
    T convert(F from);
}
