package tw.core;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tw.core.exception.OutOfRangeAnswerException;

/**
 * 在AnswerTest文件中完成Answer中对应的单元测试
 */
public class AnswerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Answer answer;
    private Answer rightAnswer = new Answer();

    @Before
    public void setUp() {
        answer = new Answer();
        rightAnswer.setNumList(Arrays.asList("1","2","3","4"));
    }

    @Test
    public void should_return_right_answer_when_input_string_validate_legal() {
        answer.setNumList(Arrays.asList("1","2","3","4"));
        assertEquals(answer.toString(), "1 2 3 4");
    }

    @Test
    public void should_throw_OutOfRangeAnswerException_when_answer_contain_same_numbers() throws OutOfRangeAnswerException {
        exception.expect(OutOfRangeAnswerException.class);
        exception.expectMessage("Answer format is incorrect");
        answer.setNumList(Arrays.asList("1", "2", "3","3"));
        answer.validate();
    }

    @Test
    public void should_throw_OutOfRangeAnswerException_when_answer_contain_numbers_more_than_9() throws OutOfRangeAnswerException {
        exception.expect(OutOfRangeAnswerException.class);
        exception.expectMessage("Answer format is incorrect");
        answer.setNumList(Arrays.asList("10", "2", "3","11"));
        answer.validate();
    }

    @Test
    public void should_return_record_0_and_0_when_answer_all_wrong() {
        answer.setNumList(Arrays.asList("9","8","7","6"));
        assertArrayEquals(new int[]{0,0}, rightAnswer.check(answer).getValue());
    }

    @Test
    public void should_return_record_in_4_and_0_when_answer_all_right() {
        answer.setNumList(Arrays.asList("1","2","3","4"));
        assertArrayEquals(new int[]{4,0}, rightAnswer.check(answer).getValue());
    }

    @Test
    public void should_return_record_in_0_and_4_when_answer_all_in_wrong_position() {
        answer.setNumList(Arrays.asList("4","3","2","1"));
        assertArrayEquals(new int[]{0,4}, rightAnswer.check(answer).getValue());
    }

    @Test
    public void should_return_record_in_2_and_1_when_input_3_right_but_1_in_wrong_position() {
        answer.setNumList(Arrays.asList("1","2","4","7"));
        assertArrayEquals(new int[]{2,1}, rightAnswer.check(answer).getValue());
    }
}