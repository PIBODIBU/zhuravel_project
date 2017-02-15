package main.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.PassportFileDAO;
import main.dao.impl.PassportFileDAOImpl;
import main.helper.FileUploader;
import main.hibernate.serializer.ErrorStatusSerializer;
import main.model.ErrorStatus;
import main.model.PassportFile;
import main.security.SecurityManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/documents")
public class PassportFileAPIController {
    @RequestMapping(value = "/{doc_id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteDocument(HttpSession session,
                                 HttpServletRequest request,
                                 @PathVariable("doc_id") Integer documentId) {
        SecurityManager securityManager = new SecurityManager(session);
        ErrorStatus errorStatus = new ErrorStatus(false);
        PassportFileDAO passportFileDAO = new PassportFileDAOImpl();
        PassportFile passportFile;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ErrorStatus.class, new ErrorStatusSerializer())
                .create();

        if (!securityManager.isUserLogged()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You are not logged in");
            return gson.toJson(errorStatus);
        }

        if (!securityManager.has(SecurityManager.ROLE_USER)) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You have no permission.");
            return gson.toJson(errorStatus);
        }

        passportFile = passportFileDAO.get(documentId);

        if (passportFile == null) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Bad passport file id");
            return gson.toJson(errorStatus);
        }

        if (!SecurityManager.PassportFiles.owns(passportFile, securityManager.getUser())) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("It's not your file");
            return gson.toJson(errorStatus);
        }

        if (!FileUploader.deleteByName(request.getServletContext(), passportFile.getFileName())) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Internal server error occurred");
            return gson.toJson(errorStatus);
        }

        passportFileDAO.delete(passportFile);

        return gson.toJson(errorStatus);
    }
}
