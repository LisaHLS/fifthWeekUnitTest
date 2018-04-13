package tw.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import tw.validator.InputValidator;

/**
 * 在InputValidatorTest文件中完成InputValidator中对应的单元测试
 */
public class InputValidatorTest {

    private InputValidator inputValidator;

    @Before
    public void setUp() {
        inputValidator = new InputValidator();
    }
    @Test
    public void should_return_false_when_numbers_less_than_4() {
        assertFalse(inputValidator.validate("1 2 3"));
    }

    @Test
    public void should_return_false_when_numbers_more_than_4() {
        assertFalse(inputValidator.validate("1 2 3 4 5"));
    }

    @Test
    public void should_return_false_when_all_number_greater_than_9() {
        assertFalse(inputValidator.validate("11 12 13 14"));
    }

    @Test
    public void should_return_false_when_one_number_greater_than_9() {
        assertFalse(inputValidator.validate("1 2 3 14"));
    }

    @Test
    public void should_return_false_when_have_same_numbers() {
        assertFalse(inputValidator.validate("2 1 2 1"));
    }

    @Test
    public void should_return_false_when_all_number_is_same() {
        assertFalse(inputValidator.validate("2 2 2 2"));
    }

    @Test
    public void should_return_true_when_right_format() {
        assertTrue(inputValidator.validate("1 2 3 4"));
    }
}
