package vrwiimote;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

import javax.swing.JTextArea;

public class TextAreaHandler extends StreamHandler {
    JTextArea textArea = null;

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void publish(LogRecord record) {
        super.publish(record);
        flush();

        if (textArea != null) {
            textArea.append(getFormatter().format(record));
        }
    }
}