package com.owner.process.usecases.project.finprojectworkquotdetlevelroom;

import com.owner.process.persistence.models.FinProjectWorkQuotDetLevelRoom;
import com.owner.process.usecases.project.projectpojo.FinProjectWorkQuotDetLevelRoomPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinProjectWorkQuotDetLevelRoomRepository extends JpaRepository<FinProjectWorkQuotDetLevelRoom,Integer> {

    @Query(value="SELECT * FROM ##plant##WORKQUOTDET_LEVELROOM WHERE WKEY = ?1 ORDER BY FLOOR_ID ASC",nativeQuery = true)
    List<FinProjectWorkQuotDetLevelRoom> getFinProjectWorkQuotDetLevelRoombyWkey(String wkey);

    @Query(value="SELECT A.*,B.FLOOR_NAME,C.ROOM_NAME FROM ##plant##WORKQUOTDET_LEVELROOM AS A LEFT JOIN ##plant##PROJECTFLOOR AS B ON A.FLOOR_ID=B.ID LEFT JOIN ##plant##PROJECTFLOORROOMS AS C ON C.ID = A.ROOM_ID " +
            "WHERE A.WKEY = ?1 ORDER BY FLOOR_ID ASC",nativeQuery = true)
    List<FinProjectWorkQuotDetLevelRoomPojo> getProWQDetLevelRoombyWkeypojo(String wkey);
}
