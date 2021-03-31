package ru.nsu.sd.MockBuddy.internal.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * Mark a field as a mock.
 *
 * Allows shorthand mock creation.
 * Minimizes repetitive mock creation code.
 * Makes the test class more readable.
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Mock { }