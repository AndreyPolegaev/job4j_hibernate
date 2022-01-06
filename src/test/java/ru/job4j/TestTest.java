package ru.job4j;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TestTest {

    @Test
    public void whenStart() {

        ru.job4j.Test test = new ru.job4j.Test();
        int t = test.t1();
        assertThat(t, is(1));
    }
}