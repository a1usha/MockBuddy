package ru.nsu.sd.MockBuddy.internal.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Allows shorthand wrapping of field instances in an spy object.
 *
 * Example:
 * <pre><code class="java">
 *
 *
 *
 * </code></pre>
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Spy { }