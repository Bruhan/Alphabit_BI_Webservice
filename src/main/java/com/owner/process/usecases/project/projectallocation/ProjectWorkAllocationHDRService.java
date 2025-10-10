package com.owner.process.usecases.project.projectallocation;

import com.owner.process.persistence.models.ProjectWorkAllocationHDR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectWorkAllocationHDRService {

    @Autowired
    ProjectWorkAllocationHDRRepository projectWorkAllocationHDRRepository;

    public int saveHdr(ProjectWorkAllocationHDR projectWorkAllocationHDR) throws Exception {
        int id;
        try {
            id = projectWorkAllocationHDRRepository.save(projectWorkAllocationHDR).getID();
        }catch (Exception e){
            throw new Exception(e);
        }
        return id;
    }

    public Optional<ProjectWorkAllocationHDR> getbyid(int id) throws Exception {
        Optional<ProjectWorkAllocationHDR> getVal;
        try {
            getVal = projectWorkAllocationHDRRepository.findById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public Boolean deletebyid(Integer pk0) throws Exception {
        try {
            projectWorkAllocationHDRRepository.deleteById(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return true;
    }

    public List<ProjectWorkAllocationHDR> getall(int page, int pcount) throws Exception {
        List<ProjectWorkAllocationHDR> getVal = new ArrayList<ProjectWorkAllocationHDR>();
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal=projectWorkAllocationHDRRepository.getall(page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }


    public List<ProjectWorkAllocationHDR> getbydatefdate(String fdate, int page, int pcount) throws Exception {
        List<ProjectWorkAllocationHDR> getVal = new ArrayList<ProjectWorkAllocationHDR>();
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal=projectWorkAllocationHDRRepository.getbydatefdate(fdate,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ProjectWorkAllocationHDR> getbydatetdate(String todate, int page, int pcount) throws Exception {
        List<ProjectWorkAllocationHDR> getVal = new ArrayList<ProjectWorkAllocationHDR>();
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal=projectWorkAllocationHDRRepository.getbydatetdate(todate,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ProjectWorkAllocationHDR> getbydateftdate(String fromdate,String todate, int page, int pcount) throws Exception {
        List<ProjectWorkAllocationHDR> getVal = new ArrayList<ProjectWorkAllocationHDR>();
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal=projectWorkAllocationHDRRepository.getbydateftdate(fromdate,todate,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ProjectWorkAllocationHDR> getallbyproject(int pid, int page, int pcount) throws Exception {
        List<ProjectWorkAllocationHDR> getVal = new ArrayList<ProjectWorkAllocationHDR>();
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal=projectWorkAllocationHDRRepository.getallbyproject(pid,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ProjectWorkAllocationHDR> getgetbyprojectanddatefdate(int pid, String fdate, int page, int pcount) throws Exception {
        List<ProjectWorkAllocationHDR> getVal = new ArrayList<ProjectWorkAllocationHDR>();
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal=projectWorkAllocationHDRRepository.getgetbyprojectanddatefdate(pid,fdate,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ProjectWorkAllocationHDR> getgetbyprojectanddatetdate(int pid, String tdate, int page, int pcount) throws Exception {
        List<ProjectWorkAllocationHDR> getVal = new ArrayList<ProjectWorkAllocationHDR>();
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal=projectWorkAllocationHDRRepository.getgetbyprojectanddatetdate(pid,tdate,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ProjectWorkAllocationHDR> getgetbyprojectanddate(int pid, String fdate, String tdate, int page, int pcount) throws Exception {
        List<ProjectWorkAllocationHDR> getVal = new ArrayList<ProjectWorkAllocationHDR>();
        try {
            getVal=projectWorkAllocationHDRRepository.getgetbyprojectanddate(pid,fdate,tdate,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ProjectWorkAllocationHDR> getgetbyprojectanddate(int pid, int wqid, String workdate) throws Exception {
        List<ProjectWorkAllocationHDR> getVal = new ArrayList<ProjectWorkAllocationHDR>();
        try {
            getVal=projectWorkAllocationHDRRepository.getallbyprojectandworkandworkdate(pid,wqid,workdate);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }


}
