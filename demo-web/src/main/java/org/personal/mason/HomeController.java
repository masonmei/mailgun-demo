package org.personal.mason;

import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    @Autowired
    public void setMailGunSender(MailGunSender mailGunSender){
        this.mailGunSender = mailGunSender;
    }

    @RequestMapping(value = {"/","/index","/home"},method = RequestMethod.GET)
    public String home(){
        return "index";
    }

    @RequestMapping(value = {"/sendEmail"},method = RequestMethod.POST)
    public String sendEmail(@Valid Mail mail, BindingResult result, Model model){
        try{
        if(model instanceof ModelMap && model.containsAttribute("msg")){
            ((ModelMap) model).remove("msg");
        }

        if(result.hasErrors()){
            return "/index";
        }
        List<File> attachments = null;
        File file = toFile(mail.getAttachment());
        if(file != null){
            attachments = Arrays.asList(file);
        }

        ClientResponse response = mailGunSender.sendHtmlEmail(mail.getFrom(),
                Arrays.asList(mail.getTo().split("[,\\s]+")),
                null,
                null,
                mail.getSubject(),
                mail.getContent(),
                attachments);

        if(response.getStatus() == 200){
            model.addAttribute("msg", "Mail has send to " + mail.getTo() + "Successfully!");
        }
        }catch ( Exception e){
            model.addAttribute("error", e.getMessage());
        }

        return "/index";
    }

    public static File toFile(MultipartFile multipartFile){
        if(multipartFile != null && !multipartFile.isEmpty()){
            File tempDirectory = FileUtils.getTempDirectory();
            File file = new File(tempDirectory, multipartFile.getOriginalFilename());
            try {
                if(file.exists()){
                    file.delete();
                }
                file.createNewFile();
                FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return file;
        }
        return null;
    }
}
