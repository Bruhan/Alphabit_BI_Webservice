package com.facility.management.usecases.attendance;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.persistence.models.ProjectWorkerList;
import com.facility.management.persistence.models.StaffAttendance;
import com.facility.management.usecases.activity_log.ActivityLogModel;
import com.facility.management.usecases.activity_log.ActivityLogService;
import com.facility.management.usecases.attendance.dao.AttendanceDao;
import com.facility.management.usecases.attendance.dto.*;
import com.facility.management.usecases.attendance.enums.AttendanceType;
import com.facility.management.usecases.attendance.enums.ShiftStatus;
import com.facility.management.usecases.employee_master.EmployeeMasterService;
import com.facility.management.usecases.employee_master.dto.WorkerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Service
public class AttendanceService {

    @Autowired
    AttendanceDao attendanceDao;

    @Autowired
    EmployeeMasterService employeeMasterService;

    @Autowired
    ActivityLogService activityLogService;


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

    private StaffAttendance buildStaffAttendance(String plant, int workerId, String date, String time, String shiftStatus, String locLat, String locLong) {
        return StaffAttendance.builder()
                .plant(plant)
                .empId(workerId)
                .attendanceDate(date)
                .attendanceTime(time)
                .shiftStatus(shiftStatus)
                .locationLat(locLat)
                .locationLong(locLong)
                .build();
    }

    public Integer saveAttendance(String plant, StaffAttendanceRequestDTO attendanceRequestDTO) {
        Integer result = 0;

        try {
            WorkerDTO workerDTO = employeeMasterService.getWorkerByEmpNo(plant, attendanceRequestDTO.getEmpNo());

            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            StaffAttendance staffAttendance = null;

            if(Objects.equals(attendanceRequestDTO.getAttendanceType(), AttendanceType.PRESENT.name()) || Objects.equals(attendanceRequestDTO.getAttendanceType(), "")) {

                StaffAttendanceDTO latestStaffAttendance = attendanceDao.getStaffAttendanceByEmpId(plant, workerDTO.getId(), attendanceRequestDTO.getAttendanceDate(), attendanceRequestDTO.getIsExecutive());

                if(latestStaffAttendance != null) {
                         if(Objects.equals(latestStaffAttendance.getShiftStatus(), ShiftStatus.MIN.name())) {
                        staffAttendance = buildStaffAttendance(plant,
                                workerDTO.getId(),
                                latestStaffAttendance.getAttendanceDate(),
                                attendanceRequestDTO.getAttendanceTime(),
                                ShiftStatus.EOUT.name(),
                                attendanceRequestDTO.getLocationLat(),
                                attendanceRequestDTO.getLocationLong()
                        );
                    } else {

                        staffAttendance = buildStaffAttendance(plant,
                                workerDTO.getId(),
                                latestStaffAttendance.getAttendanceDate(),
                                attendanceRequestDTO.getAttendanceTime(),
                                ShiftStatus.MIN.name(),
                                attendanceRequestDTO.getLocationLat(),
                                attendanceRequestDTO.getLocationLong());
                    }
                } else {

                    staffAttendance = buildStaffAttendance(plant,
                            workerDTO.getId(),
                            attendanceRequestDTO.getAttendanceDate(),
                            attendanceRequestDTO.getAttendanceTime(),
                            ShiftStatus.MIN.name(),
                            attendanceRequestDTO.getLocationLat(),
                            attendanceRequestDTO.getLocationLong());
                }

            } else {

                staffAttendance = buildStaffAttendance(plant,
                        workerDTO.getId(),
                        attendanceRequestDTO.getAttendanceDate(),
                        attendanceRequestDTO.getAttendanceTime(),
                        attendanceRequestDTO.getAttendanceType(),
                        attendanceRequestDTO.getLocationLat(),
                        attendanceRequestDTO.getLocationLong());

            }
            ActivityLogModel activityLogModel = new ActivityLogModel();
            activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                    plant, "SAVE_STAFF_ATTENDANCE", "", "", "", 0.0,
                    workerDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), workerDTO.getEmpName(),
                    "CREATED", ""));
            result = attendanceDao.saveStaffAttendance(plant, staffAttendance);


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

    public Integer updateAttendance(String plant, int empId, UpdateStaffAttendanceRequestDTO attendanceData) {
        Integer result = 0;

        try {
            List<StaffAttendanceDTO> oldAttendanceDTOList = attendanceDao.getStaffAttendanceListByEmpIdAndDate(plant, empId, attendanceData.getOldAttendanceDate());
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            if(oldAttendanceDTOList.isEmpty()) {
                return result;
            }

                if(Objects.equals(attendanceData.getAttendanceType(), AttendanceType.PRESENT.name()) || Objects.equals(attendanceData.getAttendanceType(), "")) {
                    for(StaffAttendanceDTO oldAttendanceDTO : oldAttendanceDTOList) { // Size: 2 or 1 elements
                        if(Objects.equals(oldAttendanceDTO.getShiftStatus(), ShiftStatus.MIN.name()) && oldAttendanceDTOList.size() == 1) {
//                            oldAttendanceDTO.setAttendanceDate(attendanceData.getAttendanceDate());
                            oldAttendanceDTO.setAttendanceTime(attendanceData.getAttendanceInTime());

                            StaffAttendance staffAttendance = buildStaffAttendance(plant,
                                    attendanceData.getEmpId(),
                                    oldAttendanceDTO.getAttendanceDate(),
                                    attendanceData.getAttendanceOutTime(),
                                    "EOUT",
                                    oldAttendanceDTO.getLocationLat(),
                                    oldAttendanceDTO.getLocationLong()
                            );

                            result = attendanceDao.saveStaffAttendance(plant, staffAttendance);
                        } else if (Objects.equals(oldAttendanceDTO.getShiftStatus(), AttendanceType.ABSENT.name()) || Objects.equals(oldAttendanceDTO.getShiftStatus(), AttendanceType.COMPOFF.name()) || Objects.equals(oldAttendanceDTO.getShiftStatus(), AttendanceType.HOLIDAY.name()) || Objects.equals(oldAttendanceDTO.getShiftStatus(), AttendanceType.WEEKOFF.name())) {
                            oldAttendanceDTO.setAttendanceDate(attendanceData.getAttendanceDate());
                            oldAttendanceDTO.setAttendanceTime(attendanceData.getAttendanceInTime());
                            oldAttendanceDTO.setShiftStatus(ShiftStatus.MIN.name());

                            StaffAttendance staffAttendance = buildStaffAttendance(plant,
                                    attendanceData.getEmpId(),
                                    attendanceData.getAttendanceDate(),
                                    attendanceData.getAttendanceOutTime(),
                                    "EOUT",
                                    "",
                                    ""
                            );

                            result = attendanceDao.saveStaffAttendance(plant, staffAttendance);
                        } else if(Objects.equals(oldAttendanceDTO.getShiftStatus(), ShiftStatus.MIN.name())) {
                            oldAttendanceDTO.setAttendanceDate(attendanceData.getAttendanceDate());
                            oldAttendanceDTO.setAttendanceTime(attendanceData.getAttendanceInTime());
                        } else {
                            oldAttendanceDTO.setAttendanceDate(attendanceData.getAttendanceDate());
                            oldAttendanceDTO.setAttendanceTime(attendanceData.getAttendanceOutTime());
                        }
                    }



                } else {
                    for(StaffAttendanceDTO oldAttendanceDTO : oldAttendanceDTOList) {
                        oldAttendanceDTO.setShiftStatus(attendanceData.getAttendanceType());
                        oldAttendanceDTO.setAttendanceTime(null);
                        oldAttendanceDTO.setLocationLat(null);
                        oldAttendanceDTO.setLocationLong(null);
                    }


                }

            ActivityLogModel activityLogModel = new ActivityLogModel();
            activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                    plant, "UPDATE_STAFF_ATTENDANCE", "", "", "", 0.0,
                    attendanceData.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), attendanceData.getEmpNo(),
                    "CREATED", ""));

            for (StaffAttendanceDTO staffAttendanceDTO: oldAttendanceDTOList) {
                result = attendanceDao.updateAttendance(plant, staffAttendanceDTO);
            }


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

            for(StaffAttendanceDetailDTO staffAttendanceDetailDTO: staffAttendanceDetailDTOList) {
                byte[] imageBytes = null;
                if(staffAttendanceDetailDTO.getCatalogPath() != null) {
                    Resource resource = new FileSystemResource(staffAttendanceDetailDTO.getCatalogPath());
                    if(resource.exists() & !resource.getFile().isDirectory()) {
                        imageBytes = Files.readAllBytes(resource.getFile().toPath());
                    } else {
                        imageBytes = new byte[0];
                    }
                }

                staffAttendanceDetailDTO.setImage(imageBytes);
            }

        } else {
            WorkerDTO workerDTO = employeeMasterService.getWorkerByEmpNo(plant, empNo);
            staffAttendanceDetailDTOList = attendanceDao.getStaffAttendanceByCriteria(plant, projectNo, date, startDate, endDate, workerDTO.getId());

            for(StaffAttendanceDetailDTO staffAttendanceDetailDTO: staffAttendanceDetailDTOList) {
                byte[] imageBytes = null;
                if(staffAttendanceDetailDTO.getCatalogPath() != null) {
                    Resource resource = new FileSystemResource(staffAttendanceDetailDTO.getCatalogPath());
                    if(resource.exists() & !resource.getFile().isDirectory()) {
                        imageBytes = Files.readAllBytes(resource.getFile().toPath());
                    } else {
                        imageBytes = new byte[0];
                    }
                }

                staffAttendanceDetailDTO.setImage(imageBytes);
            }
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

            boolean isEmployeeExistsInAnotherProject = attendanceDao.checkWorkerInOtherProjects(plant, projectWorkerRequestDTO.getEmpNo(), projectWorkerRequestDTO.getCurrentProjectNo());

            if(isEmployeeExistsInAnotherProject) {
                List<ProjectWorkerList> projectWorkerLists = attendanceDao.getWorkerInOtherProjects(plant, projectWorkerRequestDTO.getEmpNo(), projectWorkerRequestDTO.getCurrentProjectNo());

                for(ProjectWorkerList projectWorkerList: projectWorkerLists) {
                    FinRecycleProjectDTO finRecycleProject = attendanceDao.getFinRecycleProject(plant, projectWorkerList.getProjectNo());

                    if(Objects.equals(finRecycleProject.getProjectStatus(), "open")) {
                        result.put("result", projectWorkerSaved);
                        result.put("message", "The Worker already exists in the another project");

                        return result;
                    }
                }
            }
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            ActivityLogModel activityLogModel = new ActivityLogModel();
            activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                    plant, "SAVE_PROJECT_WORKER", "", "", "", 0.0,
                    projectWorkerRequestDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), projectWorkerRequestDTO.getEmpName(),
                    "CREATED", ""));
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

    public ResultDao toggleProjectWorker(String plant, ToggleProjectWorkerDTO toggleProjectWorkerDTO) {

        Integer projectWorkerDisabled = 0;
        ResultDao resultDao = new ResultDao();

        try {
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            ActivityLogModel activityLogModel = new ActivityLogModel();
            activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                    plant, toggleProjectWorkerDTO.getStatus() == 1 ? "ENABLE_PROJECT_WORKER" : "DISABLE_PROJECT_WORKER", "", "", "", 0.0,
                    toggleProjectWorkerDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), toggleProjectWorkerDTO.getEmpNo(),
                    "CREATED", ""));

            boolean isEmployeeExistsInAnotherProject = attendanceDao.checkWorkerInOtherProjects(plant, toggleProjectWorkerDTO.getEmpNo(), toggleProjectWorkerDTO.getCurrentProjectNo());

            if(isEmployeeExistsInAnotherProject) {
                List<ProjectWorkerList> projectWorkerLists = attendanceDao.getWorkerInOtherProjects(plant, toggleProjectWorkerDTO.getEmpNo(), toggleProjectWorkerDTO.getCurrentProjectNo());

                for(ProjectWorkerList projectWorkerList: projectWorkerLists) {
                    FinRecycleProjectDTO finRecycleProject = attendanceDao.getFinRecycleProject(plant, projectWorkerList.getProjectNo());

                    if(Objects.equals(finRecycleProject.getProjectStatus(), "open")) {


                        resultDao.setMessage("FAILED");
                        resultDao.setMessage("The Worker already active in the another project");
                        resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                        resultDao.setResults(projectWorkerDisabled);

                        return resultDao;
                    }
                }
            }

            projectWorkerDisabled = attendanceDao.toggleProjectWorker(plant, toggleProjectWorkerDTO);
            resultDao.setMessage("SUCCESS");
            resultDao.setMessage("Success");
            resultDao.setStatusCode(HttpStatus.OK.value());
            resultDao.setResults(projectWorkerDisabled);


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return resultDao;
    }

    public List<AttendanceCalendarResponseDTO> hasAttendance(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) {
        List<AttendanceCalendarResponseDTO> result = null;
        try {
            result = attendanceDao.hasAttendance(plant, projectNo, calendarRequestDTO);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }
}
