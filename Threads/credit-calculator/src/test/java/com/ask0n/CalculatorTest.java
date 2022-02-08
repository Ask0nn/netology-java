package com.ask0n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    @Test
    public void testAreaWithTwoCathetus(){
        TriangleCalculator calculator = new TriangleCalculator();
        var result = calculator.areaWithTwoCathetus(5, 5);
        Assertions.assertEquals(result, 12.5);
    }

    @Test
    public void testAreaWithOneCathetus(){
        TriangleCalculator calculator = new TriangleCalculator();
        var result = calculator.areaWithOneCathetus(10, 5);
        Assertions.assertEquals(result, 21.7);
    }
}
