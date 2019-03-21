package com.epam.logistics.base.util.generator;

import com.epam.logistics.base.util.generator.exception.IllegalPriorityNameException;
import com.epam.logistics.base.util.generator.impl.IncrementalGenerator;
import com.epam.logistics.base.util.generator.impl.IncrementalPriorityGenerator;
import com.epam.logistics.base.util.generator.impl.Priority;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class IncrementalPriorityGeneratorTests {
    private static final int PERISHABLE_GOODS_INITIAL = 0;
    private static final int NORMAL_INITIAL = 2;

    private static final int PERISHABLE_GOODS_FIRST_GENERATION = 0;
    private static final int PERISHABLE_GOODS_SECOND_GENERATION = 1;
    private static final int NORMAL_FIRST_GENERATION = 2;
    private static final int NORMAL_SECOND_GENERATION = 3;

    @Test
    public void testGenerateNextShouldReturnTwoWhenGenerateToNormalPriorityFirstTime()
            throws IllegalPriorityNameException {
        List<Generator<Integer>> generators = Arrays.asList(
                new IncrementalGenerator(NORMAL_INITIAL),
                new IncrementalGenerator(PERISHABLE_GOODS_INITIAL)
        );

        PriorityGenerator<Integer> priorityGenerator = new IncrementalPriorityGenerator(generators);

        int actual = priorityGenerator.generateNext(Priority.NORMAL);

        Assert.assertEquals(NORMAL_FIRST_GENERATION, actual);
    }

    @Test
    public void testGenerateNextShouldReturnThreeWhenGenerateToNormalPrioritySecondTime()
            throws IllegalPriorityNameException {
        List<Generator<Integer>> generators = Arrays.asList(
                new IncrementalGenerator(NORMAL_INITIAL),
                new IncrementalGenerator(PERISHABLE_GOODS_INITIAL)
        );

        PriorityGenerator<Integer> priorityGenerator = new IncrementalPriorityGenerator(generators);
        priorityGenerator.generateNext(Priority.NORMAL);

        int actual = priorityGenerator.generateNext(Priority.NORMAL);

        Assert.assertEquals(NORMAL_SECOND_GENERATION, actual);
    }

    @Test
    public void testGenerateNextShouldReturnZeroWhenGenerateToPerishableGoodsPriorityFirstTime()
            throws IllegalPriorityNameException {
        List<Generator<Integer>> generators = Arrays.asList(
                new IncrementalGenerator(NORMAL_INITIAL),
                new IncrementalGenerator(PERISHABLE_GOODS_INITIAL)
        );

        PriorityGenerator<Integer> priorityGenerator = new IncrementalPriorityGenerator(generators);

        int actual = priorityGenerator.generateNext(Priority.PERISHABLE_GOODS);

        Assert.assertEquals(PERISHABLE_GOODS_FIRST_GENERATION, actual);
    }

    @Test
    public void testGenerateNextShouldReturnThreeWhenGenerateToPerishableGoodsPrioritySecondTime()
            throws IllegalPriorityNameException {
        List<Generator<Integer>> generators = Arrays.asList(
                new IncrementalGenerator(NORMAL_INITIAL),
                new IncrementalGenerator(PERISHABLE_GOODS_INITIAL)
        );

        PriorityGenerator<Integer> priorityGenerator = new IncrementalPriorityGenerator(generators);
        priorityGenerator.generateNext(Priority.PERISHABLE_GOODS);

        int actual = priorityGenerator.generateNext(Priority.PERISHABLE_GOODS);

        Assert.assertEquals(PERISHABLE_GOODS_SECOND_GENERATION, actual);
    }
}
