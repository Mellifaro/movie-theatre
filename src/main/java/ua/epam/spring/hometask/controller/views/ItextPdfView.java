package ua.epam.spring.hometask.controller.views;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ua.epam.spring.hometask.domain.User;

public class ItextPdfView extends AbstractITextPdfView {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd LLLL yyyy");

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) model.get("users");

        PdfPTable table = new PdfPTable(4);
        table.setWidths(new int[]{10, 60, 30, 50});

        table.addCell("id");
        table.addCell("First name");
        table.addCell("Last Day");
        table.addCell("Birthday");

        for (User user : users){
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getFirstName());
            table.addCell(user.getLastName());
            table.addCell(user.getBirthday().format(FORMATTER));
        }
        document.add(table);
    }
}