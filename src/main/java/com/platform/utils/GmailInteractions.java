package com.platform.utils;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import javax.mail.*;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

public class GmailInteractions {

    List<String> links = new ArrayList<>();
    private  static final String emailId = "qa.automation@ost.com";
    private  static final String password = "alliswellqa";


    public String readEmail(String recipientMail, String subject)
    {

        String emailBody = null;
        Session session = Session.getDefaultInstance(new Properties( ));
        Store store = null;
        Folder inbox = null;
      //  boolean emailFound = false;

        outerloop:
        for(int j = 0; j<20; j++) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                store = session.getStore("imaps");

                store.connect("imap.googlemail.com", 993, emailId, password);
                inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_WRITE);
                // inbox.search()


                out.println("test " + j);
                // Fetch unseen messages from inbox folder
                Message[] messages = inbox.search(
                        new FlagTerm(new Flags(Flags.Flag.SEEN), false));
                Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
                SearchTerm st = new ReceivedDateTerm(ComparisonTerm.EQ, today);

                messages = inbox.search(st, messages);

                // Sort messages from recent to oldest
                Arrays.sort(messages, (m1, m2) -> {
                    try {
                        return m2.getSentDate().compareTo(m1.getSentDate());
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                });

                for (Message message : messages) {
                    if (message.getAllRecipients()[0].toString().equalsIgnoreCase(recipientMail) && message.getSubject().toLowerCase().contains(subject.toLowerCase())) {
                        out.println(
                                "sendDate: " + message.getSentDate()
                                        + " subject:" + message.getSubject());

                        Address[] address = message.getAllRecipients();
                        out.println("To account: " + address[0].toString());

                        Object content = message.getContent();
                        // check for string
                        // then check for multipart
                        if (content instanceof String) {
                            out.println("String: " + content);
                            emailBody = content.toString();
                        } else if (content instanceof Multipart) {
                            Multipart multiPart = (Multipart) content;
                            int multiPartCount = multiPart.getCount();
                            for (int i = 0; i < multiPartCount; i++) {
                                BodyPart bodyPart = multiPart.getBodyPart(i);
                                Object o;
                                o = bodyPart.getContent();
                                if (o instanceof String) {
                                    out.println("Multipart: " + o);
                                    emailBody = o.toString();
                                }
                            }
                        }
                        //emailFound = true;
                        break outerloop;
                    } else {

                    }
                }
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try
                {
                    out.println("Closing gmail connections");
                    if (inbox != null && inbox.isOpen()) { inbox.close(true); }
                    if (store != null) { store.close(); }
                }
                catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }

        return emailBody;
    }

    public String getActivateLink(String emailBody) {
        Pattern linkPattern = Pattern.compile(" <a\\b[^>]*href=\"([^\"]*)[^>]*>(.*?)</a>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher pageMatcher = linkPattern.matcher(emailBody);
        while (pageMatcher.find()) {
            links.add(pageMatcher.group(1));
        }
        out.println(links);
        return links.get(1);
    }
}
