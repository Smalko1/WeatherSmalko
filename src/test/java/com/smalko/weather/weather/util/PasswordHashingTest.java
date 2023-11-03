package com.smalko.weather.weather.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PasswordHashingTest {

    @Test
    void hashPasswordAndCheckPasswordSuccessful() {
        String password = "1234";

        var hashPasswordOne = PasswordHashing.hashPassword(password);

        var checkPasswordOne = PasswordHashing.checkPassword(password, hashPasswordOne);
        assertThat(checkPasswordOne).isTrue();
    }

    @Test
    void hashPasswordAndCheckPasswordUnSuccessful() {
        String password = "1234";

        var hashPasswordOne = PasswordHashing.hashPassword(password);

        var checkPasswordOne = PasswordHashing.checkPassword("1235", hashPasswordOne);
        assertThat(checkPasswordOne).isFalse();
    }
}