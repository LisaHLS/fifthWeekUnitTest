package tw.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.generator.AnswerGenerator;
import tw.core.model.GuessResult;

/**
 * 在GameTest文件中完成Game中对应的单元测试
 */


public class GameTest {

    private Game game;
    private AnswerGenerator answerGenerator;
    private Answer guessAnswer;

    @Before
    public void setUp() throws OutOfRangeAnswerException {
        Answer actualAnswer = new Answer();
        actualAnswer.setNumList(Arrays.asList("1","2","3","4"));
        answerGenerator = mock(AnswerGenerator.class);
        when(answerGenerator.generate()).thenReturn(actualAnswer);
        game = new Game(answerGenerator);
        guessAnswer = new Answer();
    }

    @Test
    public void should_return_0A0B_when_guess_answer_all_wrong() {
        guessAnswer.setNumList(Arrays.asList("5","6","7","8"));
        GuessResult guessResult = game.guess(guessAnswer);
        assertEquals("0A0B", guessResult.getResult());
    }

    @Test
    public void should_return_4A0B_when_guess_answer_all_right() {
        guessAnswer.setNumList(Arrays.asList("1","2","3","4"));
        GuessResult guessResult = game.guess(guessAnswer);
        assertEquals("4A0B", guessResult.getResult());
    }

    @Test
    public void should_return_0A4B_when_guess_answer_all_in_wrong_positions() {
        guessAnswer.setNumList(Arrays.asList("2","3","4","1"));
        GuessResult guessResult = game.guess(guessAnswer);
        assertEquals("0A4B", guessResult.getResult());
    }

    @Test
    public void should_return_1A2B_when_guess_answer_3_right_but_2_wrong_position() {
        guessAnswer.setNumList(Arrays.asList("1","4","7","2"));
        GuessResult guessResult = game.guess(guessAnswer);
        assertEquals("1A2B", guessResult.getResult());
    }

    @Test
    public void should_checkStatus_return_SUCCESS_when_guess_right() {
        guessAnswer.setNumList(Arrays.asList("1","2","3","4"));
        game.guess(guessAnswer);
        assertEquals("success", game.checkStatus());
    }

    @Test
    public void should_checkStatus_return_SUCCESS_when_guess_right_at_6th() {
        for (int i = 0; i < 5; i++) {
            guessAnswer.setNumList(Arrays.asList("1","2","3","5"));
            game.guess(guessAnswer);
        }
        guessAnswer.setNumList(Arrays.asList("1","2","3","4"));
        game.guess(guessAnswer);
        assertEquals("success", game.checkStatus());
    }

    @Test
    public void should_checkStatus_return_CONTINUE_when_guess_wrong_and_guessTimes_less_than_MAX_TIMES() {
        for (int i = 0; i < 5; i++) {
            guessAnswer.setNumList(Arrays.asList("1","2","3","5"));
            game.guess(guessAnswer);
        }
        assertEquals("continue", game.checkStatus());
    }

    @Test
    public void should_checkStatus_return_FAIL_when_guess_wrong_and_guessTimes_is_6() {
        for (int i = 0; i < 6; i++) {
            guessAnswer.setNumList(Arrays.asList("1","2","3","5"));
            game.guess(guessAnswer);
        }
        assertEquals("fail", game.checkStatus());
    }

}
