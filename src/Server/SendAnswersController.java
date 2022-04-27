package Server;

import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendAnswersController {
    private ObjectOutputStream out;
    private ExecutorService executorService = Executors.newFixedThreadPool(6);
    public SendAnswersController(ObjectOutputStream out){
        this.out = out;
    }
    public void sendAnswer(Object answer){
        executorService.submit(new AnswerSender(answer, out));
    }
}

