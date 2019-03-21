package com.epam.logistics.base.util.generator;

import com.epam.logistics.base.util.generator.impl.IncrementalGenerator;
import org.junit.Assert;
import org.junit.Test;

public class IncrementalGeneratorTests {
    private static final int INITIAL = 0;

    private static final int FIRST_GENERATION = 0;
    private static final int SECOND_GENERATION = 1;

    @Test
    public void testGenerateNextShouldReturnZeroWhenGenerateFirstTime() {
        Generator<Integer> generator = new IncrementalGenerator(INITIAL);

        int actual = generator.generateNext();

        Assert.assertEquals(FIRST_GENERATION, actual);
    }

    @Test
    public void testGenerateNextShouldReturnOneWhenGenerateSecondTime() {
        Generator<Integer> generator = new IncrementalGenerator(INITIAL);
        generator.generateNext();

        int actual = generator.generateNext();

        Assert.assertEquals(SECOND_GENERATION, actual);
    }
}
