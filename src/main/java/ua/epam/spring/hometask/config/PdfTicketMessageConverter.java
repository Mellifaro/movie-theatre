package ua.epam.spring.hometask.config;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;
import java.io.IOException;
import java.io.OutputStream;

public class PdfTicketMessageConverter<T> extends AbstractHttpMessageConverter<T> {

    public PdfTicketMessageConverter() {
        super(MediaType.APPLICATION_PDF);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(T t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        outputMessage.getHeaders().setContentType(MediaType.APPLICATION_PDF);

        OutputStream os = outputMessage.getBody();

        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, outputMessage.getBody());
            writer.setViewerPreferences(PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage);

            // Build PDF document.
            document.open();
            if(t instanceof Iterable){
               ((Iterable) t).forEach(element -> {
                   try {
                       document.add(new Paragraph(element.toString()));
                   } catch (DocumentException e) {
                       e.printStackTrace();
                   }
               });
            }else {
                document.add(new Paragraph(t.toString()));
            }
            document.close();
            os.flush();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
