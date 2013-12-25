package org.personal.mason;

import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: domei
 * Date: 12/18/13
 * Time: 8:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping
public class HomeController {

    private MailGunSender mailGunSender;
    private TaskExecutor taskExecutor;

    @Autowired
    public void setMailGunSender(MailGunSender mailGunSender) {
        this.mailGunSender = mailGunSender;
    }

    @Autowired
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @RequestMapping(value = {"/index2"}, method = RequestMethod.GET)
    public String home2() {
        return "index2";
    }

    @RequestMapping(value = {"/", "/index", "/home"}, method = RequestMethod.GET)
    public String home() {
        return "index";
    }

    @RequestMapping(value = {"/sendEmail"}, method = RequestMethod.POST)
    public String sendEmail(@Valid Mail mail, BindingResult result, Model model) {
        try {
            if (model instanceof ModelMap && model.containsAttribute("msg")) {
                ((ModelMap) model).remove("msg");
            }

            if (result.hasErrors()) {
                return "/index";
            }

            taskExecutor.execute(new EmailTask(mail));

            model.addAttribute("msg", "Mail has scheduled to send Successfully!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "/index";
    }

    public static List<File> toFile(MultipartFile[] multipartFiles) {
        List<File> files = new ArrayList<File>();
        if (multipartFiles != null) {
            for (MultipartFile multipartFile : multipartFiles) {
                if (!multipartFile.isEmpty()) {
                    File tempDirectory = FileUtils.getTempDirectory();
                    File file = new File(tempDirectory, multipartFile.getOriginalFilename());
                    try {
                        if (file.exists()) {
                            file.delete();
                        }
                        file.createNewFile();
                        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
                        files.add(file);
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        }
        return files;
    }

    private class EmailTask implements Runnable {
        private final Mail mail;

        EmailTask(Mail mail) {
            this.mail = mail;
        }

        @Override
        public void run() {
            List<File> attachments = toFile(mail.getAttachment());
            if (attachments.isEmpty()) {
                attachments = null;
            }

            List<String> bcc = null;
            List<String> cc = null;

            if (mail.getCc() != null) {
                cc = Arrays.asList(mail.getCc().split("[,\\s]+"));
            }
            if (mail.getBcc() != null) {
                bcc = Arrays.asList(mail.getBcc().split("[,\\s]+"));
            }

            ClientResponse response = mailGunSender.sendHtmlEmail(mail.getFrom(),
                    Arrays.asList(mail.getTo().split("[,\\s]+")),
                    cc,
                    bcc,
                    mail.getSubject(),
                    mail.getContent(),
                    attachments);
        }
    }
}