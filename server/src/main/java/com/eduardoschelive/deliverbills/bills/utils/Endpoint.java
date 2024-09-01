package com.eduardoschelive.deliverbills.bills.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Endpoint {
    private static final String VERSION = "/v1";

    public record Bills() {

        public static final String BASE_PATH = VERSION + "/bills";

    }

}
