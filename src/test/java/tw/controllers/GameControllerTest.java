package tw.controllers;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import tw.commands.InputCommand;
import tw.core.Answer;
import tw.core.Game;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.generator.AnswerGenerator;
import tw.views.GameView;

/**
 * 在GameControllerTest文件中完成GameController中对应的单元测试
 */
public class GameControllerTest {

    private GameController gameController;
    private AnswerGenerator answerGenerator;
    private InputCommand inputGuess;
    private Answer answer;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws OutOfRangeAnswerException {
        answer = new Answer();
        answerGenerator = mock(AnswerGenerator.class);
        inputGuess = mock(InputCommand.class);
        Answer actualAnswer = new Answer();
        actualAnswer.setNumList(Arrays.asList("1","2","3","4"));
        when(answerGenerator.generate()).thenReturn(actualAnswer);
        gameController = new GameController(new Game(answerGenerator),new GameView());
        System.setOut(new PrintStream(outContent));
    }

    private String systemOut() { return outContent.toString(); }

    @Test
    public void should_print_beginMsg_when_begin_game() throws IOException {
        gameController.beginGame();
        assertThat(systemOut().startsWith("------Guess Number Game, You have 6 chances to guess!  ------")).isTrue();
    }

    @Test
    public void should_print_GuessHistory_and_failMsg_when_all_guess_wrong() throws IOException {
        answer.setNumList(Arrays.asList("5","7","6","8"));
        when(inputGuess.input()).thenReturn(answer);
        gameController.play(inputGuess);
        assertThat(systemOut().contains(
            "[Guess Numbers: 5 7 6 8, Guess Result: 0A0B]\r\n" +
                "[Guess Numbers: 5 7 6 8, Guess Result: 0A0B]\r\n" +
                "[Guess Numbers: 5 7 6 8, Guess Result: 0A0B]\r\n" +
                "[Guess Numbers: 5 7 6 8, Guess Result: 0A0B]\r\n" +
                "[Guess Numbers: 5 7 6 8, Guess Result: 0A0B]\r\n" +
                "[Guess Numbers: 5 7 6 8, Guess Result: 0A0B]\r\n" +
                "Game Status: fail")).isTrue();
        verify(inputGuess, times(6)).input();
    }

    @Test
    public void should_print_GuessHistory_and_failMsg_when_guess_3_right_numbers_but_1_in_wrong_position() throws IOException {
        answer.setNumList(Arrays.asList("1","3","6","4"));
        when(inputGuess.input()).thenReturn(answer);
        gameController.play(inputGuess);
        assertThat(systemOut().contains(
            "[Guess Numbers: 1 3 6 4, Guess Result: 2A1B]\r\n[Guess Numbers: 1 3 6 4, Guess Result: 2A1B]\r\n" +
                "[Guess Numbers: 1 3 6 4, Guess Result: 2A1B]\r\n[Guess Numbers: 1 3 6 4, Guess Result: 2A1B]\r\n" +
                "[Guess Numbers: 1 3 6 4, Guess Result: 2A1B]\r\n[Guess Numbers: 1 3 6 4, Guess Result: 2A1B]\r\n" +
                "Game Status: fail")).isTrue();
        verify(inputGuess, times(6)).input();
    }

    @Test
    public void should_print_GuessHistory_and_success_when_one_times_guess_right() throws IOException {
        answer.setNumList(Arrays.asList("1","2","3","4"));
        when(inputGuess.input()).thenReturn(answer);
        gameController.play(inputGuess);
        assertThat(systemOut().contains("[Guess Numbers: 1 2 3 4, Guess Result: 4A0B]\r\nGame Status: success")).isTrue();
        verify(inputGuess, times(1)).input();
    }

}