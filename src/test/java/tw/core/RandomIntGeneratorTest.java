package tw.core;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tw.core.generator.RandomIntGenerator;

/**
 * 在RandomIntGeneratorTest文件中完成RandomIntGenerator中对应的单元测试
 */
public class RandomIntGeneratorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private RandomIntGenerator randomIntGenerator;

    @Before
    public void setUp() {
        randomIntGenerator = new RandomIntGenerator();
    }

    @Test
    public void should_throw_IllegalArgumentException_when_digitmax_less_than_numbersOfNeed(){
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Can't ask for more numbers than are available");
        randomIntGenerator.generateNums(3, 7);

    }

    @Test
    public void should_generate_randomNum_string_length_is_numbersOfNeed() {
        String generateNums = randomIntGenerator.generateNums(8, 4);
        assertEquals(4, generateNums.split(" ").length);
    }

    @Test
    public void should_each_generate_number_less_than_digitmax() {
        String generateNums = randomIntGenerator.generateNums(9, 4);
        assertTrue(Arrays.stream(generateNums.split(" ")).allMatch(item ->  Integer.parseInt(item) < 9));

    }

    @Test
    public void should_each_generate_number_is_different() {
        String generateNums = randomIntGenerator.generateNums(9, 4);
        assertEquals(4, Arrays.stream(generateNums.split(" ")).distinct().count());
    }

}