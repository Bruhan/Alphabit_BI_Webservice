package com.facility.management.usecases.firebase_messaging;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.facility.management.helpers.common.results.ResultsDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.persistence.models.Note;
import com.facility.management.persistence.models.UserInfo;
import com.facility.management.usecases.auth.AuthRepository;
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
        ResultsDao resultsDao = new ResultsDao();
        resultsDao.setResults("ok");
        resultsDao.setMessage("Fcm Key updated");
        resultsDao.setPageNumber(1);
        resultsDao.setPageSize(1);
        return new ResponseEntity<>(resultsDao, HttpStatus.OK);
    }
}
