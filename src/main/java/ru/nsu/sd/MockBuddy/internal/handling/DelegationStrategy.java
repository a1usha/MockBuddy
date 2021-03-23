package ru.nsu.sd.MockBuddy.internal.handling;

public enum DelegationStrategy {
    CALL_REAL_METHOD,
    RETURN_NULL,
    THROW_EXCEPTION,
    RETURN_CUSTOM
}
