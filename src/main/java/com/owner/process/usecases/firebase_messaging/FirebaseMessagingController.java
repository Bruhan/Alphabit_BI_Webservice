package com.owner.process.usecases.firebase_messaging;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.owner.process.helpers.common.results.ResultsDTO;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.Note;
import com.owner.process.persistence.models.UserInfo;
import com.owner.process.usecases.auth.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${spring.base.path}")
public class FirebaseMessagingController {

    @Autowired
    FirebaseMessagingService firebaseMessagingService;
    @Autowired
    AuthRepository authRepository;
    @Autowired
    ClaimsSet claimsSet;

    @RequestMapping(value = "/send-notification", method = RequestMethod.POST)
    @ResponseBody
    public String sendNotification(@RequestBody Note note,
                                   @RequestParam String token) throws FirebaseMessagingException {
        return firebaseMessagingService.sendNotification(note, token);
    }

    @RequestMapping(value = "/notification-key/save", method = RequestMethod.POST)
    public ResponseEntity<?> savNoteKey(HttpServletRequest request
            , @RequestParam String token) throws Exception {

        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String userName = claimsDao.getSub();

        UserInfo userInfo = authRepository.findByUserId(userName);
        userInfo.setAppNotificationKey(token);
        firebaseMessagingService.setUserInfo(userInfo);
        //result
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults("ok");
        resultsDTO.setMessage("Fcm Key updated");
        resultsDTO.setPageNumber(1);
        resultsDTO.setPageSize(1);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }
}
