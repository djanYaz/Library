package com.librarymanagement.library.services.emailService;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class EmailService {
    private final SendGrid SendGrid;

    public EmailService() {
        SendGrid = new SendGrid("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");
    }

    public void WarnOfApproachingDeadline(String readerEmail, String bookName, String deadLineDate) throws IOException {
        Email from = new Email("admin@sacredKnowledge.com");
        String subject = "Трябва да върнете книгата си скоро!";
        Email to = new Email(readerEmail);
        Content content = new Content("text/plain", "Здравейте. Трябва да върнете книгата '"+bookName+"' до "+ deadLineDate);
        Mail mail = new Mail(from, subject, to, content);
        this.trySendEmail(mail);
    }

    public void WarnOfOverdueBook(String readerEmail, String bookName, Date deadLineDate) throws IOException{
        Email from = new Email("admin@sacredKnowledge.com");
        String subject = "Трябва да върнете книгата си скоро!";
        Email to = new Email(readerEmail);
        Content content = new Content("text/plain", "Здравейте. Трябва да върнете книгата '"+bookName+"' до "+ deadLineDate);
        Mail mail = new Mail(from, subject, to, content);
        this.trySendEmail(mail);
    }

    public void WarnOfAdministrativeSanction(String readerEmail, String bookName, Date deadLineDate) throws IOException {
        Email from = new Email("admin@sacredKnowledge.com");
        String subject = "Трябва да върнете книгата си скоро!";
        Email to = new Email(readerEmail);
        Content content = new Content("text/plain", "Здравейте. Просрочихте връщането на книгата '"+bookName+"', като крайния срок беше: "+ deadLineDate+". Моля върнете книгата - " +
                "в противен случай ще последват административни наказания!");
        Mail mail = new Mail(from, subject, to, content);
        this.trySendEmail(mail);
    }
    public void InformUserOfBorrowingSuccess(String readerEmail, String bookName, Date deadLineDate) throws IOException{
        Email from = new Email("vaianakiev@tu-sofia.bg");
        String subject = "Трябва да върнете книгата си скоро!";
        Email to = new Email(readerEmail);
        Content content = new Content("text/plain", "Здравейте. Заехте книгата '"+bookName+"', като крайния срок за връщането й е : "+ deadLineDate+".");
        Mail mail = new Mail(from, subject, to, content);
        this.trySendEmail(mail);
    }
    private void trySendEmail(Mail mail) throws IOException{
        try{
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = this.SendGrid.api(request);
        }
        catch(IOException ioException){
            throw ioException;
        }
    }
}
