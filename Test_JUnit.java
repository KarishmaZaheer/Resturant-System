package order;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class Test_JUnit {
    @Test
    public void test_JUnit() {
        System.out.println("test passed");
        String getOrderText="Steak Dinner";
        assertEquals("Steak Dinner", getOrderText);
    }
}