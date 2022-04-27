package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

public class AnswerSender implements Runnable{
    private ObjectOutputStream out;
    private Object object;
    public AnswerSender(Object object, ObjectOutputStream out){
        this.object = object;
        this.out = out;
    }
    @Override
    public void run() {
        try {
            Server.LOGGER.log(Level.INFO, "Server sends answer");
            out.writeObject(object);
            out.flush();
        }
        catch (IOException e){
            Server.LOGGER.log(Level.WARNING, "Answer wasn`t sent.");
        }

    }
}
