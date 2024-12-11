package com.facility.management.usecases.attendance;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.persistence.models.FinRecycleProject;
import com.facility.management.persistence.models.ProjectWorkerList;
import com.facility.management.persistence.models.StaffAttendance;
import com.facility.management.usecases.attendance.dao.AttendanceDao;
import com.facility.management.usecases.attendance.dto.*;
import com.facility.management.usecases.attendance.enums.AttendanceType;
import com.facility.management.usecases.attendance.enums.ShiftStatus;
import com.facility.management.usecases.employee_master.EmployeeMasterService;
import com.facility.management.usecases.employee_master.dto.WorkerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Service
public class AttendanceService {

    @Autowired
    AttendanceDao attendanceDao;

    @Autowired
    EmployeeMasterService employeeMasterService;


    public List<AttendanceDTO> getAttendanceByCriteria(String plant, String date,String startDate,String endDate,String empNo) throws IOException {

        List<AttendanceDTO> attendanceDTOList = null;

        date = (date == null) ? "" : date;
        startDate = (startDate == null) ? "" : startDate;
        endDate = (endDate == null) ? "" : endDate;
        empNo = (empNo == null) ? "" : empNo;


        attendanceDTOList = attendanceDao.getAttendanceByCriteria(plant, date, startDate, endDate, empNo);

        return attendanceDTOList;
    }

    public AttendanceDTO getAttendanceById(String plant, int id) {
        AttendanceDTO attendanceDTO = null;

        try {
            attendanceDTO = attendanceDao.getAttendanceById(plant, id);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex.getMessage());
        }

        return attendanceDTO;
    }

//    public Integer saveAttendance(String plant, AttendanceDTO attendanceDTO) {
//        Integer result = 0;
//        try {
//            String inTimeDirPath = "C:/U-CLO_IMAGE/ATTENDANCE/VSoftTech/Images/"+ plant +"/IN_TIME/";
//            String outTimeDirPath = "C:/U-CLO_IMAGE/ATTENDANCE/VSoftTech/Images/"+ plant +"/OUT_TIME/";
//
//            try {
//                createDirectoryIfNotExists(inTimeDirPath);
//                createDirectoryIfNotExists(outTimeDirPath);
//                System.out.println("Directories checked and created if needed.");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            if(attendanceDTO.getInTimeFace() != null && attendanceDTO.getInTimeFace().length > 0) {
//                String inTimeFacePath = saveImage(attendanceDTO.getInTimeFace(), inTimeDirPath, "inTimeFace_" + attendanceDTO.getEmpNo() + ".jpg");
//                attendanceDTO.setInTimeFacePath(inTimeFacePath);
//            }
//
//            if(attendanceDTO.getOutTimeFace() != null && attendanceDTO.getOutTimeFace().length > 0) {
//                String outTimeFacePath = saveImage(attendanceDTO.getOutTimeFace(), outTimeDirPath, "outTimeFace_" + attendanceDTO.getEmpNo() + ".jpg");
//                attendanceDTO.setOutTimeFacePath(outTimeFacePath);
//            }
//
//            result = attendanceDao.saveAttendance(plant, attendanceDTO);
//        } catch (Exception ex)
//        {
//            throw new RuntimeException(ex.getMessage());
//        }
//
//        return result;
//    }

    public Integer saveAttendance(String plant, StaffAttendanceRequestDTO attendanceRequestDTO) {
        Integer result = 0;
        try {
            WorkerDTO workerDTO = employeeMasterService.getWorkerByEmpNo(plant, attendanceRequestDTO.getEmpNo());

            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            StaffAttendance staffAttendance = null;

            if(Objects.equals(attendanceRequestDTO.getAttendanceType(), AttendanceType.PRESENT.name()) || Objects.equals(attendanceRequestDTO.getAttendanceType(), "")) {

                StaffAttendanceDTO latestStaffAttendance = attendanceDao.getStaffAttendanceById(plant, workerDTO.getId());

                if(latestStaffAttendance != null) {
                    if(Objects.equals(latestStaffAttendance.getShiftStatus(), ShiftStatus.MIN.name())) {
                        staffAttendance = StaffAttendance.builder()
                                .plant(plant)
                                .empId(workerDTO.getId())
                                .attendanceDate(latestStaffAttendance.getAttendanceDate())
                                .attendanceTime(attendanceRequestDTO.getAttendanceTime())
                                .shiftStatus(ShiftStatus.EOUT.name())
                                .locationLat(attendanceRequestDTO.getLocationLat())
                                .locationLong(attendanceRequestDTO.getLocationLong())
                                .build();
                    } else {
                        staffAttendance = StaffAttendance.builder()
                                .plant(plant)
                                .empId(workerDTO.getId())
                                .attendanceDate(attendanceRequestDTO.getAttendanceDate())
                                .attendanceTime(attendanceRequestDTO.getAttendanceTime())
                                .shiftStatus(ShiftStatus.MIN.name())
                                .locationLat(attendanceRequestDTO.getLocationLat())
                                .locationLong(attendanceRequestDTO.getLocationLong())
                                .build();
                    }
                } else {

                    staffAttendance = StaffAttendance.builder()
                            .plant(plant)
                            .empId(workerDTO.getId())
                            .attendanceDate(attendanceRequestDTO.getAttendanceDate())
                            .attendanceTime(attendanceRequestDTO.getAttendanceTime())
                            .shiftStatus(ShiftStatus.MIN.name())
                            .locationLat(attendanceRequestDTO.getLocationLat())
                            .locationLong(attendanceRequestDTO.getLocationLong())
                            .build();

                }

                result = attendanceDao.saveStaffAttendance(plant, staffAttendance);
            } else {

                staffAttendance = StaffAttendance.builder()
                        .plant(plant)
                        .empId(workerDTO.getId())
                        .attendanceDate(attendanceRequestDTO.getAttendanceDate())
                        .attendanceTime(attendanceRequestDTO.getAttendanceTime())
                        .shiftStatus(attendanceRequestDTO.getAttendanceType())
                        .locationLat(attendanceRequestDTO.getLocationLat())
                        .locationLong(attendanceRequestDTO.getLocationLong())
                        .build();

                result = attendanceDao.saveStaffAttendance(plant, staffAttendance);
            }


        } catch (Exception ex)
        {
            throw new RuntimeException(ex.getMessage());
        }

        return result;
    }

    public static void createDirectoryIfNotExists(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            System.out.println("Directory created: " + dirPath);
        } else {
            System.out.println("Directory already exists: " + dirPath);
        }
    }

    public Integer updateAttendance(String plant, AttendanceDTO newAttendance, int id) {
        Integer result = 0;

        try {

            String inTimeDirPath = "C:/U-CLO_IMAGE/ATTENDANCE/VSoftTech/Images/"+ plant +"/IN_TIME/";
            String outTimeDirPath = "C:/U-CLO_IMAGE/ATTENDANCE/VSoftTech/Images/"+ plant +"/OUT_TIME/";

            AttendanceDTO oldAttendanceDTO = this.getAttendanceById(plant, id);

            if(oldAttendanceDTO == null) {
                return result;
            }

            oldAttendanceDTO.setEmpNo(newAttendance.getEmpNo());
            oldAttendanceDTO.setEmpName(newAttendance.getEmpName());
            oldAttendanceDTO.setDate(newAttendance.getDate());
            oldAttendanceDTO.setInTime(newAttendance.getInTime());
            oldAttendanceDTO.setOutTime(newAttendance.getOutTime());
            oldAttendanceDTO.setInTimeLocation(newAttendance.getInTimeLocation());
            oldAttendanceDTO.setOutTimeLocation(newAttendance.getOutTimeLocation());
            oldAttendanceDTO.setInTimeFace(newAttendance.getInTimeFace());
            oldAttendanceDTO.setOutTimeFace(newAttendance.getOutTimeFace());

            if(oldAttendanceDTO.getInTimeFace() != null && oldAttendanceDTO.getInTimeFace().length > 0) {
                String inTimeFacePath = saveImage(oldAttendanceDTO.getInTimeFace(), inTimeDirPath, "inTimeFace_" + oldAttendanceDTO.getEmpNo() + ".jpg");
                oldAttendanceDTO.setInTimeFacePath(inTimeFacePath);
            }

            if(oldAttendanceDTO.getOutTimeFace() != null && oldAttendanceDTO.getOutTimeFace().length > 0) {
                String outTimeFacePath = saveImage(oldAttendanceDTO.getOutTimeFace(), outTimeDirPath, "outTimeFace_" + oldAttendanceDTO.getEmpNo() + ".jpg");
                oldAttendanceDTO.setOutTimeFacePath(outTimeFacePath);
            }

            result = attendanceDao.updateAttendance(plant, id, oldAttendanceDTO);


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }



    public List<AttendanceDTO> getTodayAttendance(String plant) {
        List<AttendanceDTO> attendanceDTOList = null;
        try {
            attendanceDTOList = attendanceDao.getTodayAttendance(plant);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return attendanceDTOList;
    }

    private String saveImage(byte[] imageBytes, String path, String fileName) throws IOException {
        Path filePath = Paths.get(path + fileName);
        Files.write(filePath, imageBytes);
        return filePath.toString();
    }

    public List<StaffAttendanceDetailDTO> getStaffAttendanceByCriteria(String plant, String projectNo, String date, String startDate, String endDate, String empNo) throws Exception {
        List<StaffAttendanceDetailDTO> staffAttendanceDetailDTOList = null;

        date = (date == null) ? "" : date;
        startDate = (startDate == null) ? "" : startDate;
        endDate = (endDate == null) ? "" : endDate;
        empNo = (empNo == null) ? "" : empNo;

        if(empNo.isEmpty()) {
            staffAttendanceDetailDTOList = attendanceDao.getStaffAttendanceByCriteria(plant, projectNo, date, startDate, endDate, null);
        } else {
            WorkerDTO workerDTO = employeeMasterService.getWorkerByEmpNo(plant, empNo);
            staffAttendanceDetailDTOList = attendanceDao.getStaffAttendanceByCriteria(plant, projectNo, date, startDate, endDate, workerDTO.getId());
        }


        return staffAttendanceDetailDTOList;
    }

    public HashMap<String, Object> saveProjectWorker(String plant, ProjectWorkerRequestDTO projectWorkerRequestDTO) {
        HashMap<String, Object> result = new HashMap<>();
        Integer projectWorkerSaved = 0;
        try {
            boolean isEmployeeExists = attendanceDao.checkWorkerInProjectWorkers(plant, projectWorkerRequestDTO);

            if(isEmployeeExists) {
                result.put("result", projectWorkerSaved);
                result.put("message", "The Worker already exists in the current project");

                return result;
            }

            boolean isEmployeeExistsInAnotherProject = attendanceDao.checkWorkerInOtherProjects(plant, projectWorkerRequestDTO);

            if(isEmployeeExistsInAnotherProject) {
                List<ProjectWorkerList> projectWorkerLists = attendanceDao.getWorkerInOtherProjects(plant, projectWorkerRequestDTO);

                for(ProjectWorkerList projectWorkerList: projectWorkerLists) {
                    FinRecycleProjectDTO finRecycleProject = attendanceDao.getFinRecycleProject(plant, projectWorkerList.getProjectNo());

                    if(Objects.equals(finRecycleProject.getProjectStatus(), "open")) {
                        result.put("result", projectWorkerSaved);
                        result.put("message", "The Worker already exists in the another project");

                        return result;
                    }
                }
            }

            projectWorkerSaved = attendanceDao.saveProjectWorker(plant, projectWorkerRequestDTO);

            result.put("result", projectWorkerSaved);
            result.put("message", "Worker created Successfully");


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public List<ProjectWorkerDTO> getAllProjectWorkers(String plant, String projectNo) {
        List<ProjectWorkerDTO> projectWorkerDTOList = null;
        try {
            projectWorkerDTOList = attendanceDao.getAllProjectWorkers(plant, projectNo);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return projectWorkerDTOList;
    }
}
