package tw.core.generator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tw.core.exception.OutOfRangeAnswerException;

/**
 * 在AnswerGeneratorTest文件中完成AnswerGenerator中对应的单元测试
 */
public class AnswerGeneratorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private AnswerGenerator answerGenerator;
    private RandomIntGenerator randomIntGenerator = new RandomIntGenerator();

    @Before
    public void setUp() {
        randomIntGenerator = mock(RandomIntGenerator.class);
        answerGenerator = new AnswerGenerator(randomIntGenerator);
    }

    @Test
    public void should_return_right_answer_when_numbers_validate_legal() throws OutOfRangeAnswerException {
        when(randomIntGenerator.generateNums(9, 4)).thenReturn("1 2 3 4");
        answerGenerator.generate();
        assertEquals(answerGenerator.generate().toString(), "1 2 3 4");
    }

    @Test
    public void should_throw_OutOfRangeAnswerException_when_contain_same_numbers() throws OutOfRangeAnswerException {
        exception.expect(OutOfRangeAnswerException.class);
        exception.expectMessage("Answer format is incorrect");
        when(randomIntGenerator.generateNums(9, 4)).thenReturn("3 2 3 2");
        answerGenerator.generate();
    }

    @Test
    public void should_throw_OutOfRangeAnswerException_when_contain_numbers_more_than_9() throws OutOfRangeAnswerException {
        exception.expect(OutOfRangeAnswerException.class);
        exception.expectMessage("Answer format is incorrect");
        when(randomIntGenerator.generateNums(9, 4)).thenReturn("10 2 3 11");
        answerGenerator.generate();
    }
}