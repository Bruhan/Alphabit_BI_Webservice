package com.facility.management.usecases.attendance.dao;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.persistence.models.ProjectWorkerList;
import com.facility.management.persistence.models.StaffAttendance;
import com.facility.management.usecases.attendance.dto.*;
import com.facility.management.usecases.attendance.exception.ShouldNotBeNullException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository("AttendanceDao")
public class AttendanceDaoImpl implements AttendanceDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Value("${app.attendance.default-image}")
    String defaultImagePath;

    @Override
    public List<AttendanceDTO> getAttendanceByCriteria(String plant, String date, String startDate, String endDate, String empNo) throws IOException {

        Session session = sessionFactory.openSession();
        String sql = null;
        List<AttendanceDTO> attendanceDTOList = new ArrayList<>();
        try {
            if(date.isEmpty() && startDate.isEmpty() && endDate.isEmpty() && empNo.isEmpty()) {
                sql = "SELECT ATT.ID as attendanceId, ATT.PLANT as plant, ATT.EMPNO as empNo, \n" +
                        "  (SELECT CONCAT(FNAME, ' ', LNAME) FROM "+ plant +"_EMP_MST WHERE EMPNO = EMP.EMPNO) as empName,\n" +
                        "  ATT.DATE as date, ATT.IN_TIME as inTime, ATT.OUT_TIME as outTime, ATT.IN_TIME_LOCATION as inTimeLocation,\n" +
                        "  ATT.OUT_TIME_LOCATION as outTimeLocation, ATT.IN_TIME_FACE_PATH as inTimeFacePath,\n" +
                        "  ATT.OUT_TIME_FACE_PATH as outTimeFacePath FROM " + plant + "_ATTENDANCE_SUMMARY ATT LEFT JOIN " + plant + "_EMP_MST EMP ON ATT.EMPNO = EMP.EMPNO\n" +
                        "  WHERE DATE LIKE '%%' OR DATE BETWEEN '' AND '' OR ATT.EMPNO LIKE '%%'";

                Query query = session.createSQLQuery(sql);
                List<Object[]> rows = query.list();
                for (Object[] row : rows) {
                    AttendanceDTO dto = new AttendanceDTO();
                    dto.setAttendanceId((int) row[0]);
                    dto.setPlant((String) row[1]);
                    dto.setEmpNo((String) row[2]);
                    dto.setEmpName((String) row[3]);
                    dto.setDate((String) row[4]);
                    dto.setInTime((String) row[5]);
                    dto.setOutTime((String) row[6]);
                    dto.setInTimeLocation((String) row[7]);
                    dto.setOutTimeLocation((String) row[8]);

                    if(row[9] == null) {
                        throw new ShouldNotBeNullException("InTimeFacePath should not be null");
                    }

                    dto.setInTimeFacePath((String) row[9]);
                    Resource inTimeResource = getResourceOrDefault((String) row[9], defaultImagePath);

                    byte[] inTimeImageBytes = Files.readAllBytes(inTimeResource.getFile().toPath());
                    dto.setInTimeFace(inTimeImageBytes);

                    if(row[10] != null) {
                        dto.setOutTimeFacePath((String) row[10]);
                        Resource outTimeResource = getResourceOrDefault((String) row[10], defaultImagePath);

                        byte[] outTimeImageBytes = Files.readAllBytes(outTimeResource.getFile().toPath());
                        dto.setOutTimeFace(outTimeImageBytes);
                    }

                    attendanceDTOList.add(dto);
                }

            }
            else {
                sql = "SELECT ATT.ID as attendanceId, ATT.PLANT as plant, ATT.EMPNO as empNo, \n" +
                        "  (SELECT CONCAT(FNAME, ' ', LNAME) FROM "+plant+"_EMP_MST WHERE EMPNO = EMP.EMPNO) as empName,\n" +
                        "  ATT.DATE as date, ATT.IN_TIME as inTime, ATT.OUT_TIME as outTime, ATT.IN_TIME_LOCATION as inTimeLocation,\n" +
                        "  ATT.OUT_TIME_LOCATION as outTimeLocation, ATT.IN_TIME_FACE_PATH as inTimeFacePath,\n" +
                        "  ATT.OUT_TIME_FACE_PATH as outTimeFacePath FROM "+plant+"_ATTENDANCE_SUMMARY ATT LEFT JOIN " + plant + "_EMP_MST EMP ON ATT.EMPNO = EMP.EMPNO\n" +
                        "  WHERE DATE = :date OR DATE BETWEEN :startDate AND :endDate OR ATT.EMPNO = :empNo";

                Query query = session.createSQLQuery(sql);
                query.setParameter("date", date);
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
                query.setParameter("empNo", empNo);

                List<Object[]> rows = query.list();
                for (Object[] row : rows) {
                    AttendanceDTO dto = new AttendanceDTO();
                    dto.setAttendanceId((int) row[0]);
                    dto.setPlant((String) row[1]);
                    dto.setEmpNo((String) row[2]);
                    dto.setEmpName((String) row[3]);
                    dto.setDate((String) row[4]);
                    dto.setInTime((String) row[5]);
                    dto.setOutTime((String) row[6]);
                    dto.setInTimeLocation((String) row[7]);
                    dto.setOutTimeLocation((String) row[8]);

                   if(row[9] == null) {
                       throw new ShouldNotBeNullException("InTimeFacePath should not be null");
                   }

                    dto.setInTimeFacePath((String) row[9]);
                    Resource inTimeResource = getResourceOrDefault((String) row[9], defaultImagePath);

                    byte[] inTimeImageBytes = Files.readAllBytes(inTimeResource.getFile().toPath());
                    dto.setInTimeFace(inTimeImageBytes);


                    if(row[10] != null) {
                        dto.setOutTimeFacePath((String) row[10]);
                        Resource outTimeResource = getResourceOrDefault((String) row[10], defaultImagePath);

                        byte[] outTimeImageBytes = Files.readAllBytes(outTimeResource.getFile().toPath());
                        dto.setOutTimeFace(outTimeImageBytes);
                    }

                    attendanceDTOList.add(dto);
                }

            }

            return attendanceDTOList;

        } catch (ShouldNotBeNullException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    private Resource getResourceOrDefault(String facePath, String defaultImagePath) {
        Path path = Paths.get(facePath);
        if (Files.exists(path)) {
            return new FileSystemResource(facePath);
        } else {
            return new FileSystemResource(defaultImagePath);
        }
    }

    @Override
    public AttendanceDTO getAttendanceById(String plant, int id) throws IOException {
        Session session = sessionFactory.openSession();
        AttendanceDTO attendanceDTO = null;

        try {
            String sql = "  SELECT TOP 1 ATT.ID as attendanceId, ATT.PLANT as plant, ATT.EMPNO as empNo, \n" +
                    "  (SELECT CONCAT(FNAME, ' ', LNAME) FROM " + plant + "_EMP_MST WHERE EMPNO = EMP.EMPNO) as empName,\n" +
                    "  ATT.DATE as date, ATT.IN_TIME as inTime, ATT.OUT_TIME as outTime, ATT.IN_TIME_LOCATION as inTimeLocation,\n" +
                    "  ATT.OUT_TIME_LOCATION as outTimeLocation, ATT.IN_TIME_FACE_PATH as inTimeFacePath,\n" +
                    "  ATT.OUT_TIME_FACE_PATH as outTimeFacePath FROM " + plant + "_ATTENDANCE_SUMMARY ATT LEFT JOIN " + plant + "_EMP_MST EMP ON ATT.EMPNO = EMP.EMPNO\n" +
                    "  WHERE ATT.ID = :id";
            Query query = session.createSQLQuery(sql);
            query.setParameter("id", id);

            Object[] row = (Object[]) query.uniqueResult();

            if (row != null) {
                attendanceDTO = new AttendanceDTO();
                attendanceDTO.setAttendanceId((Integer) row[0]);
                attendanceDTO.setPlant((String) row[1]);
                attendanceDTO.setEmpNo((String) row[2]);
                attendanceDTO.setEmpName((String) row[3]);
                attendanceDTO.setDate((String) row[4]);
                attendanceDTO.setInTime((String) row[5]);
                attendanceDTO.setOutTime((String) row[6]);
                attendanceDTO.setInTimeLocation((String) row[7]);
                attendanceDTO.setOutTimeLocation((String) row[8]);
                attendanceDTO.setInTimeFacePath((String) row[9]);
                attendanceDTO.setOutTimeFacePath((String) row[10]);

                Resource inTimeResource = getResourceOrDefault((String) row[9], defaultImagePath);

                byte[] inTimeImageBytes = Files.readAllBytes(inTimeResource.getFile().toPath());
                attendanceDTO.setInTimeFace(inTimeImageBytes);

                Resource outTimeResource = getResourceOrDefault((String) row[10], defaultImagePath);

                byte[] outTimeImageBytes = Files.readAllBytes(outTimeResource.getFile().toPath());
                attendanceDTO.setOutTimeFace(outTimeImageBytes);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

        return attendanceDTO;
    }

    @Override
    public Integer saveAttendance(String plant, AttendanceDTO attendanceDTO) throws ParseException {
        Session session = sessionFactory.openSession();
        Integer val = 0;

        try {
            String sql = "INSERT INTO [" + plant + "_ATTENDANCE_SUMMARY] (PLANT, EMPNO, IN_TIME, OUT_TIME, IN_TIME_LOCATION," +
                    "OUT_TIME_LOCATION, IN_TIME_FACE_PATH, OUT_TIME_FACE_PATH, DATE, CRAT, CRBY, UPAT, UPBY) " +
                    "VALUES (:plant, :empNo, :inTime, :outTime, :inTimeLocation, :outTimeLocation, :inTimeFacePath, :outTimeFacePath," +
                    ":date, :crAt, :crBy, :upAt, :upBy)";

            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("empNo", attendanceDTO.getEmpNo());
            query.setParameter("inTime", attendanceDTO.getInTime());
            query.setParameter("outTime", attendanceDTO.getOutTime());
            query.setParameter("inTimeLocation", attendanceDTO.getInTimeLocation());
            query.setParameter("outTimeLocation", attendanceDTO.getOutTimeLocation());
            query.setParameter("inTimeFacePath", attendanceDTO.getInTimeFacePath());
            query.setParameter("outTimeFacePath", attendanceDTO.getOutTimeFacePath());
            query.setParameter("date", attendanceDTO.getDate());
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());
            query.setParameter("crBy", attendanceDTO.getEmpNo());
            query.setParameter("upAt", null);
            query.setParameter("upBy", null);

            val = query.executeUpdate();
            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
        }

        return val;
    }

    @Override
    public Integer saveStaffAttendance(String plant, StaffAttendance staffAttendance) throws ParseException {
        Session session = sessionFactory.openSession();
        Integer val = 0;
        try {
            if(!staffAttendance.getAttendanceDate().isEmpty() && !staffAttendance.getAttendanceTime().isEmpty()) {
                String sql = "INSERT INTO [" + plant + "_Staffattendance] (PLANT, EMPID, Att_Date, Att_Time, ShiftStatus," +
                        "Location_Lat, Location_Long) " +
                        "VALUES (:plant, :empId, :attDate, :attTime, :shiftStatus, :locationLat, :locationLong)";

                session.beginTransaction();
                Query query = session.createSQLQuery(sql);
                query.setParameter("plant", plant);
                query.setParameter("empId", staffAttendance.getEmpId());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date parsedDate = dateFormat.parse(staffAttendance.getAttendanceDate());

                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
                java.util.Date parsedDateTime = dateTimeFormat.parse(staffAttendance.getAttendanceTime());

                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());  // For attendanceDate
                Timestamp sqlTimestamp = new Timestamp(parsedDateTime.getTime());

                query.setParameter("attDate", sqlDate);
                query.setParameter("attTime", sqlTimestamp);
                query.setParameter("shiftStatus", staffAttendance.getShiftStatus());
                query.setParameter("locationLat", staffAttendance.getLocationLat());
                query.setParameter("locationLong", staffAttendance.getLocationLong());

                val = query.executeUpdate();
                session.getTransaction().commit();
            } else {
                String sql = "INSERT INTO [" + plant + "_Staffattendance] (PLANT, Att_Date, EMPID, ShiftStatus) " +
                        "VALUES (:plant, :attDate, :empId, :shiftStatus)";

                session.beginTransaction();
                Query query = session.createSQLQuery(sql);
                query.setParameter("plant", plant);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date parsedDate = dateFormat.parse(staffAttendance.getAttendanceDate());

                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());


                query.setParameter("attDate", sqlDate);
                query.setParameter("empId", staffAttendance.getEmpId());
                query.setParameter("shiftStatus", staffAttendance.getShiftStatus());

                val = query.executeUpdate();
                session.getTransaction().commit();
            }


        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
        }

        return val;
    }

    @Override
    public Integer updateAttendance(String plant, StaffAttendanceDTO attendanceDTO) throws ParseException {
        Session session = sessionFactory.openSession();
        Integer val = 0;

        try {

            if(attendanceDTO.getAttendanceTime() != null) {

                String sql = "UPDATE [" + plant + "_Staffattendance] " +
                        "SET Att_Date = :attDate, Att_Time = :attTime, ShiftStatus = :shiftStatus, " +
                        "Location_Lat = :locationLat, Location_Long = :locationLong " +
                        "WHERE ID = :id";
                DateTimeCalc dateTimeCalc = new DateTimeCalc();

                session.beginTransaction();
                Query query = session.createSQLQuery(sql);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date parsedDate = dateFormat.parse(attendanceDTO.getAttendanceDate());

                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
                java.util.Date parsedDateTime = dateTimeFormat.parse(attendanceDTO.getAttendanceTime());

                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());  // For attendanceDate
                Timestamp sqlTimestamp = new Timestamp(parsedDateTime.getTime());

                query.setParameter("attDate", sqlDate);
                query.setParameter("attTime", sqlTimestamp);
                query.setParameter("shiftStatus", attendanceDTO.getShiftStatus());
                query.setParameter("locationLat", attendanceDTO.getLocationLat());
                query.setParameter("locationLong", attendanceDTO.getLocationLong());
                query.setParameter("id", attendanceDTO.getId());

                val = query.executeUpdate();
                session.getTransaction().commit();
            } else {
                String sql = "UPDATE [" + plant + "_Staffattendance] " +
                        "SET Att_Date = :attDate, ShiftStatus = :shiftStatus, " +
                        "Location_Lat = :locationLat, Location_Long = :locationLong " +
                        "WHERE ID = :id";
                DateTimeCalc dateTimeCalc = new DateTimeCalc();

                session.beginTransaction();
                Query query = session.createSQLQuery(sql);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date parsedDate = dateFormat.parse(attendanceDTO.getAttendanceDate());


                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

                query.setParameter("attDate", sqlDate);
                query.setParameter("shiftStatus", attendanceDTO.getShiftStatus());
                query.setParameter("locationLat", attendanceDTO.getLocationLat());
                query.setParameter("locationLong", attendanceDTO.getLocationLong());
                query.setParameter("id", attendanceDTO.getId());

                val = query.executeUpdate();
                session.getTransaction().commit();
            }


        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
        }

        return val;
    }


    @Override
    public List<AttendanceDTO> getTodayAttendance(String plant) throws IOException {

        Session session = sessionFactory.openSession();
        String sql = null;
        List<AttendanceDTO> attendanceDTOList = new ArrayList<>();
        try {
            sql = "SELECT ATT.ID as attendanceId, ATT.PLANT as plant, ATT.EMPNO as empNo, " +
                    "  (SELECT CONCAT(FNAME, ' ', LNAME) FROM "+plant+"_EMP_MST WHERE EMPNO = EMP.EMPNO) as empName, " +
                    "  ATT.DATE as date, ATT.IN_TIME as inTime, ATT.OUT_TIME as outTime, ATT.IN_TIME_LOCATION as inTimeLocation, " +
                    "  ATT.OUT_TIME_LOCATION as outTimeLocation, ATT.IN_TIME_FACE_PATH as inTimeFacePath, " +
                    "  ATT.OUT_TIME_FACE_PATH as outTimeFacePath FROM "+plant+"_ATTENDANCE_SUMMARY ATT LEFT JOIN " + plant + "_EMP_MST EMP ON ATT.EMPNO = EMP.EMPNO " +
                    "  WHERE DATE = :date";

            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            Query query = session.createSQLQuery(sql);
            query.setParameter("date", dateTimeCalc.getTodayDMYDate());

            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                AttendanceDTO dto = new AttendanceDTO();
                dto.setAttendanceId((int) row[0]);
                dto.setPlant((String) row[1]);
                dto.setEmpNo((String) row[2]);
                dto.setEmpName((String) row[3]);
                dto.setDate((String) row[4]);
                dto.setInTime((String) row[5]);
                dto.setOutTime((String) row[6]);
                dto.setInTimeLocation((String) row[7]);
                dto.setOutTimeLocation((String) row[8]);
                dto.setInTimeFacePath((String) row[9]);
                dto.setOutTimeFacePath((String) row[10]);

                Resource inTimeResource = new FileSystemResource((String) row[9]);

                byte[] inTimeImageBytes = Files.readAllBytes(inTimeResource.getFile().toPath());
                dto.setInTimeFace(inTimeImageBytes);

                Resource outTimeResource = new FileSystemResource((String) row[10]);

                byte[] outTimeImageBytes = Files.readAllBytes(outTimeResource.getFile().toPath());
                dto.setOutTimeFace(outTimeImageBytes);

                attendanceDTOList.add(dto);
            }

            return attendanceDTOList;

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    @Override
    public StaffAttendanceDTO getStaffAttendanceByEmpId(String plant, int id, String attendanceDate, int isExecutive) throws ParseException {
        Session session = sessionFactory.openSession();
        String sql = null;
        StaffAttendanceDTO staffAttendanceDTO = null;
        DateTimeCalc dateTimeCalc = new DateTimeCalc();
        try {
            if(isExecutive == 0) {
                if(attendanceDate.equals(dateTimeCalc.getTodayDateNormalFormat())) {
                    sql = "SELECT TOP (1) PLANT, ID, EMPID, Att_Date, Att_Time, ShiftStatus, Location_Lat, Location_Long FROM " + plant + "_Staffattendance " +
                            "WHERE PLANT = :plant AND EMPID = :empId AND (Att_Date = CAST(GETDATE() AS DATE)) ORDER BY Att_Time DESC";
                } else {
                    sql = "SELECT TOP (1) PLANT, ID, EMPID, Att_Date, Att_Time, ShiftStatus, Location_Lat, Location_Long FROM " + plant + "_Staffattendance " +
                            "WHERE PLANT = :plant AND EMPID = :empId AND (Att_Date = CAST(GETDATE() AS DATE) OR Att_Date = CAST(GETDATE() - 1 AS DATE)) ORDER BY Att_Time DESC";
                }

                Query query = session.createSQLQuery(sql);
                query.setParameter("plant", plant);
                query.setParameter("empId", id);

                Object[] row = (Object[]) query.uniqueResult();

                if (row != null) {
                    staffAttendanceDTO = new StaffAttendanceDTO();
                    staffAttendanceDTO.setPlant((String) row[0]);
                    staffAttendanceDTO.setId((BigInteger) row[1]);
                    staffAttendanceDTO.setEmpId((BigInteger) row[2]);
                    staffAttendanceDTO.setAttendanceDate(row[3] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[3]) : null);
                    staffAttendanceDTO.setAttendanceTime(row[4] != null ? new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format((java.util.Date) row[4]) : null);
                    staffAttendanceDTO.setShiftStatus((String) row[5]);
                    staffAttendanceDTO.setLocationLat((String) row[6]);
                    staffAttendanceDTO.setLocationLong((String) row[7]);

                }

            } else {
                sql = "SELECT TOP (1) PLANT, ID, EMPID, Att_Date, Att_Time, ShiftStatus, Location_Lat, Location_Long FROM " + plant + "_Staffattendance " +
                        "WHERE PLANT = :plant AND EMPID = :empId AND Att_Date = CAST(:date AS DATE) ORDER BY Att_Time DESC";

                Query query = session.createSQLQuery(sql);
                query.setParameter("plant", plant);
                query.setParameter("empId", id);

                String inputDateStr = attendanceDate; // "04-04-2025"
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date formattedDate = inputFormat.parse(inputDateStr);

                query.setParameter("date", formattedDate);

                Object[] row = (Object[]) query.uniqueResult();

                if (row != null) {
                    staffAttendanceDTO = new StaffAttendanceDTO();
                    staffAttendanceDTO.setPlant((String) row[0]);
                    staffAttendanceDTO.setId((BigInteger) row[1]);
                    staffAttendanceDTO.setEmpId((BigInteger) row[2]);
                    staffAttendanceDTO.setAttendanceDate(row[3] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[3]) : null);
                    staffAttendanceDTO.setAttendanceTime(row[4] != null ? new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format((java.util.Date) row[4]) : null);
                    staffAttendanceDTO.setShiftStatus((String) row[5]);
                    staffAttendanceDTO.setLocationLat((String) row[6]);
                    staffAttendanceDTO.setLocationLong((String) row[7]);

                }

            }


        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
        return staffAttendanceDTO;
    }

    @Override
    public List<StaffAttendanceDTO> getStaffAttendanceListByEmpIdAndDate(String plant, int empId, String date) throws ParseException {
        Session session = sessionFactory.openSession();
        String sql = null;
        List<StaffAttendanceDTO> staffAttendanceDTOList = null;
        try {
            sql = "SELECT TOP (2) PLANT, ID, EMPID, Att_Date, Att_Time, ShiftStatus, Location_Lat, Location_Long FROM " + plant + "_Staffattendance " +
                    "WHERE PLANT = :plant AND EMPID = :empId AND Att_Date = :date";

            Query query = session.createSQLQuery(sql);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            query.setParameter("plant", plant);
            query.setParameter("empId", empId);

            if(!date.isEmpty()) {
                java.util.Date parsedDate = dateFormat.parse(date);
                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                query.setParameter("date", sqlDate);
            } else {
                query.setParameter("date", "");
            }

            List<Object[]> rows = query.list();

            staffAttendanceDTOList = new ArrayList<>();

           for(Object[] row: rows) {
                StaffAttendanceDTO staffAttendanceDTO = new StaffAttendanceDTO();
                staffAttendanceDTO.setPlant((String) row[0]);
                staffAttendanceDTO.setId((BigInteger) row[1]);
                staffAttendanceDTO.setEmpId((BigInteger) row[2]);
                staffAttendanceDTO.setAttendanceDate(row[3] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[3]) : null);
                staffAttendanceDTO.setAttendanceTime(row[4] != null ? new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format((java.util.Date) row[4]) : null);
                staffAttendanceDTO.setShiftStatus((String) row[5]);
                staffAttendanceDTO.setLocationLat((String) row[6]);
                staffAttendanceDTO.setLocationLong((String) row[7]);

                staffAttendanceDTOList.add(staffAttendanceDTO);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
        return staffAttendanceDTOList;
    }

    @Override
    public List<StaffAttendanceDetailDTO> getStaffAttendanceByCriteria(String plant, String projectNo, String date, String startDate, String endDate, Integer empId) throws ParseException {
        Session session = sessionFactory.openSession();
        String sql = null;
        List<StaffAttendanceDetailDTO> staffAttendanceDetailDTOList = new ArrayList<>();
        try {
            if(date.isEmpty() && startDate.isEmpty() && endDate.isEmpty() && empId == null) {

                sql = "WITH FilteredAttendance AS (SELECT ATT.EMPID, ATT.Att_Date, MAX(CASE WHEN ATT.ShiftStatus = 'EOUT' THEN ATT.Att_Time END) AS EOUT_Att_Time, " +
                        "MIN(CASE WHEN ATT.ShiftStatus = 'MIN' THEN ATT.Att_Time END) AS MIN_Att_Time,  MIN(CASE WHEN ATT.ShiftStatus = 'MIN' OR ATT.ShiftStatus = 'EOUT' OR ATT.ShiftStatus = 'ABSENT' OR ATT.ShiftStatus = 'WEEKOFF' OR ATT.ShiftStatus = 'HOLIDAY' OR ATT.ShiftStatus = 'COMPOFF' THEN ATT.ShiftStatus END) AS ShiftStatus, " +
                        " (SELECT CONCAT(FNAME, ' ', LNAME) FROM " + plant + "_EMP_MST WHERE ID = EMP.ID) AS empName, (SELECT EMPNO " +
                        " FROM "+ plant +"_EMP_MST WHERE ID = EMP.ID) AS empNo, MAX(CASE WHEN ATT.ShiftStatus = 'EOUT' THEN ATT.Location_Lat END) AS EOUT_Location_Lat, " +
                        " MAX(CASE WHEN ATT.ShiftStatus = 'EOUT' THEN ATT.Location_Long END) AS EOUT_Location_Long, MIN(CASE WHEN ATT.ShiftStatus = 'MIN' THEN ATT.Location_Lat END) AS MIN_Location_Lat, " +
                        " MIN(CASE WHEN ATT.ShiftStatus = 'MIN' THEN ATT.Location_Long END) AS MIN_Location_Long, (SELECT CATLOGPATH FROM " + plant + "_EMP_MST WHERE ID = EMP.ID) AS CatalogPath" +
                        " FROM " + plant +"_Staffattendance ATT LEFT JOIN " + plant +"_EMP_MST EMP ON ATT.EMPID = EMP.ID" +
                        " LEFT JOIN " + plant + "_PROJECT_WORKERLIST WRK ON ATT.EMPID = WRK.EMPID" +
                        " WHERE (ATT.Att_Date LIKE '%%' OR ATT.Att_Date BETWEEN '' AND '' OR ATT.EMPID LIKE '%%') AND WRK.PROJECTNO = :projectNo AND WRK.STATUS = :status" +
                        " GROUP BY ATT.EMPID, ATT.Att_Date, EMP.PLANT, EMP.ID" +
                        ") SELECT * FROM FilteredAttendance";

                Query query = session.createSQLQuery(sql);
                query.setParameter("projectNo", projectNo);
                query.setParameter("status", 1);
                List<Object[]> rows = query.list();
                for (Object[] row : rows) {
                    StaffAttendanceDetailDTO dto = new StaffAttendanceDetailDTO();
                    dto.setEmpId((BigInteger) row[0]);
                    dto.setAttendanceDate(row[1] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[1]) : "");
                    dto.setEOutAttendanceTime(row[2] != null ? new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format((java.util.Date) row[2]) : "");
                    dto.setMinAttendanceTime(row[3] != null ? new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format((java.util.Date) row[3]) : "");
                    dto.setShiftStatus((String) row[4]);
                    dto.setEmployeeName((String) row[5]);
                    dto.setEmployeeNo((String) row[6]);
                    dto.setEOutLocationLat(row[7] != null && !((String) row[7]).isEmpty() ? (String) row[7] : "0.0");
                    dto.setEOutLocationLong(row[8] != null && !((String) row[8]).isEmpty() ?(String) row[8] : "0.0");
                    dto.setMinLocationLat(row[9] != null && !((String) row[9]).isEmpty() ? (String) row[9] : "0.0");
                    dto.setMinLocationLong(row[10] != null && !((String) row[10]).isEmpty() ? (String) row[10] : "0.0");
                    dto.setCatalogPath((String) row[11]);

                    staffAttendanceDetailDTOList.add(dto);
                }

            }
            else {

                sql = "WITH FilteredAttendance AS (SELECT ATT.EMPID, ATT.Att_Date, MAX(CASE WHEN ATT.ShiftStatus = 'EOUT' THEN ATT.Att_Time END) AS EOUT_Att_Time, " +
                        "MIN(CASE WHEN ATT.ShiftStatus = 'MIN' THEN ATT.Att_Time END) AS MIN_Att_Time,  MIN(CASE WHEN ATT.ShiftStatus = 'MIN' OR ATT.ShiftStatus = 'EOUT' OR ATT.ShiftStatus = 'ABSENT' OR ATT.ShiftStatus = 'WEEKOFF' OR ATT.ShiftStatus = 'HOLIDAY' OR ATT.ShiftStatus = 'COMPOFF' THEN ATT.ShiftStatus END) AS ShiftStatus, " +
                        " (SELECT CONCAT(FNAME, ' ', LNAME) FROM " + plant + "_EMP_MST WHERE ID = EMP.ID) AS empName, (SELECT EMPNO " +
                        " FROM "+ plant +"_EMP_MST WHERE ID = EMP.ID) AS empNo, MAX(CASE WHEN ATT.ShiftStatus = 'EOUT' THEN ATT.Location_Lat END) AS EOUT_Location_Lat, " +
                        " MAX(CASE WHEN ATT.ShiftStatus = 'EOUT' THEN ATT.Location_Long END) AS EOUT_Location_Long, MIN(CASE WHEN ATT.ShiftStatus = 'MIN' THEN ATT.Location_Lat END) AS MIN_Location_Lat, " +
                        " MIN(CASE WHEN ATT.ShiftStatus = 'MIN' THEN ATT.Location_Long END) AS MIN_Location_Long, (SELECT CATLOGPATH FROM " + plant + "_EMP_MST WHERE ID = EMP.ID) AS CatalogPath" +
                        " FROM " + plant +"_Staffattendance ATT LEFT JOIN " + plant +"_EMP_MST EMP ON ATT.EMPID = EMP.ID" +
                        " LEFT JOIN " + plant + "_PROJECT_WORKERLIST WRK ON ATT.EMPID = WRK.EMPID" +
                        " WHERE (ATT.Att_Date = :date OR ATT.Att_Date BETWEEN :startDate AND :endDate OR ATT.EMPID = :empId) AND WRK.PROJECTNO = :projectNo AND (WRK.PROJECTIN_DATE <= :date AND ISNULL(WRK.PROJECTOUT_DATE, GETDATE()) >= :date)" +
                        " GROUP BY ATT.EMPID, ATT.Att_Date, EMP.PLANT, EMP.ID" +
                        ") SELECT * FROM FilteredAttendance";

                Query query = session.createSQLQuery(sql);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                query.setParameter("projectNo", projectNo);
//                query.setParameter("status", 1);

                if(!date.isEmpty()) {
                    java.util.Date parsedDate = dateFormat.parse(date);
                    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                    query.setParameter("date", sqlDate);
                } else {
                    query.setParameter("date", "");
                }

                if(!startDate.isEmpty()) {
//                    java.util.Date parsedStartDate = dateFormat.parse(startDate);
//                    java.sql.Date sqlStartDate = new java.sql.Date(parsedStartDate.getTime());
                    query.setParameter("startDate", startDate);
                } else {
                    query.setParameter("startDate", "");
                }

                if(!endDate.isEmpty()) {
//                    java.util.Date parsedEndDate = dateFormat.parse(endDate);
//                    java.sql.Date sqlEndDate = new java.sql.Date(parsedEndDate.getTime());
                    query.setParameter("endDate", endDate);
                } else {
                    query.setParameter("endDate", "");
                }

                query.setParameter("empId", empId == null ? "" : empId);

                List<Object[]> rows = query.list();
                for (Object[] row : rows) {
                    StaffAttendanceDetailDTO dto = new StaffAttendanceDetailDTO();
                    dto.setEmpId((BigInteger) row[0]);
                    dto.setAttendanceDate(row[1] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[1]) : "");
                    dto.setEOutAttendanceTime(row[2] != null ? new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format((java.util.Date) row[2]) : "");
                    dto.setMinAttendanceTime(row[3] != null ? new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss").format((java.util.Date) row[3]) : "");
                    dto.setShiftStatus((String) row[4]);
                    dto.setEmployeeName((String) row[5]);
                    dto.setEmployeeNo((String) row[6]);
                    dto.setEOutLocationLat(row[7] != null && !((String) row[7]).isEmpty() ? (String) row[7] : "0.0");
                    dto.setEOutLocationLong(row[8] != null && !((String) row[8]).isEmpty() ?(String) row[8] : "0.0");
                    dto.setMinLocationLat(row[9] != null && !((String) row[9]).isEmpty() ? (String) row[9] : "0.0");
                    dto.setMinLocationLong(row[10] != null && !((String) row[10]).isEmpty() ? (String) row[10] : "0.0");
                    dto.setCatalogPath((String) row[11]);

                    staffAttendanceDetailDTOList.add(dto);
                }

            }

            return staffAttendanceDetailDTOList;

        } catch (ShouldNotBeNullException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean checkWorkerInProjectWorkers(String plant, ProjectWorkerRequestDTO projectWorkerRequestDTO) {
        boolean recordExists = false;
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT COUNT(*) FROM " + plant + "_PROJECT_WORKERLIST WHERE PLANT = :plant AND EMPCODE = :empCode AND PROJECTNO = :projectNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("empCode", projectWorkerRequestDTO.getEmpNo());
            query.setParameter("projectNo", projectWorkerRequestDTO.getCurrentProjectNo());

            Long count = ((Number) query.uniqueResult()).longValue();

            if (count > 0) {
                recordExists = true;
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
        return recordExists;
    }

    @Override
    public boolean checkWorkerInOtherProjects(String plant, String empNo, String projectNo) {
        boolean recordExists = false;
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT COUNT(*) FROM " + plant + "_PROJECT_WORKERLIST WHERE plant = :plant AND EMPCODE = :empCode AND PROJECTNO != :projectNo AND STATUS = :status";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("empCode", empNo);
            query.setParameter("projectNo", projectNo);
            query.setParameter("status", 1);

            Long count = ((Number) query.uniqueResult()).longValue();

            if (count > 0) {
                recordExists = true;
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
        return recordExists;
    }

    @Override
    public List<ProjectWorkerList> getWorkerInOtherProjects(String plant, String empNo, String projectNo) {
        List<ProjectWorkerList> projectWorkerLists = null;
        Session session = sessionFactory.openSession();
        try {
            String sql = "SELECT [PLANT] ,[ID] ,[PROJECTNO] ,[EMPID] ,[EMPCODE] ,[EMPNAME] ,[PROJECTIN_DATE] " +
                    ",[PROJECTOUT_DATE] ,[STATUS] FROM " + plant + "_PROJECT_WORKERLIST " +
                    "WHERE plant = :plant AND EMPCODE = :empCode AND PROJECTNO != :projectNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("empCode", empNo);
            query.setParameter("projectNo", projectNo);

            List<Object[]> rows = query.list();

            projectWorkerLists = new ArrayList<>();
            for(Object[] row: rows) {
                ProjectWorkerList projectWorkerList = ProjectWorkerList.builder()
                        .plant((String) row[0])
                        .id((int) row[1])
                        .projectNo((String) row[2])
                        .empId((BigInteger) row[3])
                        .empCode((String) row[4])
                        .empName((String) row[5])
                        .projectInDate(row[6] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[6]) : null)
                        .projectOutDate(row[7] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[7]) : null)
                        .status(row[8] != null ? (Byte) row[8] : 0)
                        .build();

                projectWorkerLists.add(projectWorkerList);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
        return projectWorkerLists;
    }

    @Override
    public FinRecycleProjectDTO getFinRecycleProject(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        FinRecycleProjectDTO finRecycleProjectDTO = null;
        try {
            String sql = "SELECT PLANT, ID, CUSTNO, PROJECT, PROJECT_NAME, PROJECT_STATUS " +
                    "FROM " + plant + "_FINRECYCLEPROJECT WHERE PLANT = :plant AND PROJECT = :projectNo";

            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectNo);

            Object[] row = (Object[]) query.uniqueResult();

            if (row != null) {

                finRecycleProjectDTO = new FinRecycleProjectDTO();
                finRecycleProjectDTO.setPlant((String) row[0]);
                finRecycleProjectDTO.setId((int) row[1]);
                finRecycleProjectDTO.setCustNo((String) row[2]);
                finRecycleProjectDTO.setProjectNo((String) row[3]);
                finRecycleProjectDTO.setProjectName((String) row[4]);
                finRecycleProjectDTO.setProjectStatus(((String) row[5]).toLowerCase());

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

        return finRecycleProjectDTO;
    }

    @Override
    public Integer saveProjectWorker(String plant, ProjectWorkerRequestDTO projectWorkerRequestDTO) throws ParseException {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            String sql = "INSERT INTO [" + plant + "_PROJECT_WORKERLIST] (PLANT, PROJECTNO, EMPID, EMPCODE, EMPNAME, " +
                    "PROJECTIN_DATE, STATUS, CRAT) VALUES (:plant, :projectNo, :empId, :empCode, :empName, :projectInDate, " +
                    ":status, :crAt)";

            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectWorkerRequestDTO.getCurrentProjectNo());
            query.setParameter("empId", projectWorkerRequestDTO.getEmpId());
            query.setParameter("empCode", projectWorkerRequestDTO.getEmpNo());
            query.setParameter("empName", projectWorkerRequestDTO.getEmpName());

            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date parsedDate = inputFormat.parse(projectWorkerRequestDTO.getDate());

            query.setParameter("projectInDate", parsedDate);
            query.setParameter("status", 1);
            query.setParameter("crAt", dateTimeCalc.getTodayDateTime());


            result = query.executeUpdate();
            session.getTransaction().commit();


        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public List<ProjectWorkerDTO> getAllProjectWorkers(String plant, String projectNo) {
        Session session = sessionFactory.openSession();
        List<ProjectWorkerDTO> projectWorkerDTOList = null;
        try {
            String sql = "SELECT [PLANT] ,[ID] ,[PROJECTNO] ,[EMPID] ,[EMPCODE] ,[EMPNAME] ,[STATUS] FROM " + plant + "_PROJECT_WORKERLIST " +
                    "WHERE plant = :plant AND PROJECTNO = :projectNo";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("projectNo", projectNo);

            List<Object[]> rows = query.list();

            projectWorkerDTOList = new ArrayList<>();
            for(Object[] row: rows) {
                ProjectWorkerDTO projectWorkerDTO = new ProjectWorkerDTO();
                projectWorkerDTO.setPlant((String) row[0]);
                projectWorkerDTO.setId((int) row[1]);
                projectWorkerDTO.setProjectNo((String) row[2]);
                projectWorkerDTO.setEmpId((BigInteger) row[3]);
                projectWorkerDTO.setEmpCode((String) row[4]);
                projectWorkerDTO.setEmpName((String) row[5]);
//                projectWorkerDTO.setProjectInDate(row[6] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[6]) : null);
//                projectWorkerDTO.setProjectOutDate(row[7] != null ? new SimpleDateFormat("dd-MM-yyyy").format((java.util.Date) row[7]) : null);
                projectWorkerDTO.setStatus(row[6] != null ? (Byte) row[6] : 0);

                projectWorkerDTOList.add(projectWorkerDTO);
            }

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
        }
        return projectWorkerDTOList;
    }

    @Override
    public Integer toggleProjectWorker(String plant, ToggleProjectWorkerDTO toggleProjectWorkerDTO) throws ParseException {
        Session session = sessionFactory.openSession();
        Integer result = 0;
        try {
            if(toggleProjectWorkerDTO.getStatus() == 0) {
                String sql = "UPDATE [" + plant + "_PROJECT_WORKERLIST] SET STATUS = :status, PROJECTOUT_DATE = :projectOutDate, " +
                        "UPAT = :upAt WHERE PROJECTNO = :projectNo AND EMPID = :empId AND PLANT = :plant";

                session.beginTransaction();
                Query query = session.createSQLQuery(sql);
                DateTimeCalc dateTimeCalc = new DateTimeCalc();

                query.setParameter("plant", plant);
                query.setParameter("projectNo", toggleProjectWorkerDTO.getCurrentProjectNo());
                query.setParameter("empId", toggleProjectWorkerDTO.getEmpId());
                query.setParameter("status", toggleProjectWorkerDTO.getStatus());

                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date parsedDate = inputFormat.parse(toggleProjectWorkerDTO.getDate());

                query.setParameter("projectOutDate", parsedDate);
                query.setParameter("upAt", dateTimeCalc.getTodayDateTime());

                result = query.executeUpdate();
                session.getTransaction().commit();
            } else if (toggleProjectWorkerDTO.getStatus() == 1) {
                String sql = "UPDATE [" + plant + "_PROJECT_WORKERLIST] SET STATUS = :status, PROJECTIN_DATE = :projectInDate, " +
                        "UPAT = :upAt WHERE PROJECTNO = :projectNo AND EMPID = :empId AND PLANT = :plant";

                session.beginTransaction();
                Query query = session.createSQLQuery(sql);
                DateTimeCalc dateTimeCalc = new DateTimeCalc();

                query.setParameter("plant", plant);
                query.setParameter("projectNo", toggleProjectWorkerDTO.getCurrentProjectNo());
                query.setParameter("empId", toggleProjectWorkerDTO.getEmpId());
                query.setParameter("status", toggleProjectWorkerDTO.getStatus());

                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date parsedDate = inputFormat.parse(toggleProjectWorkerDTO.getDate());

                query.setParameter("projectInDate", parsedDate);
                query.setParameter("upAt", dateTimeCalc.getTodayDateTime());

                result = query.executeUpdate();
                session.getTransaction().commit();
            }

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public List<AttendanceCalendarResponseDTO> hasAttendance(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException {
        Session session = sessionFactory.openSession();
        List<AttendanceCalendarResponseDTO> result = new ArrayList<>();
        try {

            for(String date : calendarRequestDTO.getDateList()) {
                String sql = "  SELECT COUNT(*) AS RESULT FROM " + plant + "_Staffattendance Att " +
                        "  LEFT JOIN " + plant + "_PROJECT_WORKERLIST WRK ON WRK.EMPID = Att.EMPID " +
                        "  WHERE WRK.PROJECTNO = :projectNo AND Att.Att_Date = :date AND (WRK.PROJECTIN_DATE <= :date AND ISNULL(WRK.PROJECTOUT_DATE, GETDATE()) >= :date)";

                Query query = session.createSQLQuery(sql);
                query.setParameter("projectNo", projectNo);

                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date parsedDate = inputFormat.parse(date);
                query.setParameter("date", parsedDate);

                Number row = (Number) query.uniqueResult();

                AttendanceCalendarResponseDTO attendanceCalendarResponseDTO = new AttendanceCalendarResponseDTO();
                attendanceCalendarResponseDTO.setDate(date);
                attendanceCalendarResponseDTO.setHasAttendance(row.intValue());

                result.add(attendanceCalendarResponseDTO);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

        return result;
    }

}
