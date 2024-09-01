package com.eduardoschelive.deliverbills.filter.strategies;


import com.eduardoschelive.deliverbills.filter.exception.NoStrategyDefinedException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.invoke.MethodType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParsingStrategySelector {

    private static final Map<Class<?>, ParsingStrategy> STRATEGIES = Map.of(
            String.class, new StringParsingStrategy(),
            Boolean.class, new BooleanParsingStrategy(),
            BigDecimal.class, new BigDecimalParsingStrategy(),
            Integer.class, new IntegerParsingStrategy(),
            LocalDate.class, new LocalDateParsingStrategy()
    );

    private static Class<?> getWrapper(Class<?> expectedType) {
        return MethodType.methodType(expectedType).wrap().returnType();
    }

    public static ParsingStrategy getStrategy(Class<?> expectedType) {
        var wrapper = getWrapper(expectedType);
        var strategy = STRATEGIES.get(wrapper);

        if (strategy == null) {
            throw new NoStrategyDefinedException(wrapper.getSimpleName());
        }

        return strategy;
    }

}
