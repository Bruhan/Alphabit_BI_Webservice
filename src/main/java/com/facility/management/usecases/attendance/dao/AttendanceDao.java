package com.facility.management.usecases.attendance.dao;

import com.facility.management.persistence.models.FinRecycleProject;
import com.facility.management.persistence.models.ProjectWorkerList;
import com.facility.management.persistence.models.StaffAttendance;
import com.facility.management.usecases.attendance.dto.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface AttendanceDao {
    List<AttendanceDTO> getAttendanceByCriteria(String plant, String date,String startDate,String endDate,String empNo) throws IOException;

    AttendanceDTO getAttendanceById(String plant, int id) throws IOException;

    Integer saveAttendance(String plant, AttendanceDTO attendanceDTO) throws ParseException;

    Integer saveStaffAttendance(String plant, StaffAttendance staffAttendance) throws ParseException;

    Integer updateAttendance(String plant, StaffAttendanceDTO attendanceDTO) throws ParseException;


    List<AttendanceDTO> getTodayAttendance(String plant) throws IOException;


    StaffAttendanceDTO getStaffAttendanceByEmpId(String plant, int id);

    List<StaffAttendanceDTO> getStaffAttendanceListByEmpIdAndDate(String plant, int id, String date) throws ParseException;

    List<StaffAttendanceDetailDTO> getStaffAttendanceByCriteria(String plant, String projectNo, String date, String startDate, String endDate, Integer empId) throws ParseException;

    boolean checkWorkerInProjectWorkers(String plant, ProjectWorkerRequestDTO projectWorkerRequestDTO);

    boolean checkWorkerInOtherProjects(String plant, String empNo, String projectNo);

    List<ProjectWorkerList> getWorkerInOtherProjects(String plant, String empNo, String projectNo);

    FinRecycleProjectDTO getFinRecycleProject(String plant, String projectNo);

    Integer saveProjectWorker(String plant, ProjectWorkerRequestDTO projectWorkerRequestDTO);

    List<ProjectWorkerDTO> getAllProjectWorkers(String plant, String projectNo);

    Integer toggleProjectWorker(String plant, ToggleProjectWorkerDTO toggleProjectWorkerDTO);

}
