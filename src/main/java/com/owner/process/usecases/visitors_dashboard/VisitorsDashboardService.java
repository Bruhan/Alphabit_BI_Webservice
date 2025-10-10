package com.owner.process.usecases.visitors_dashboard;

import com.owner.process.usecases.visitors_dashboard.dto.SaveVisitorsDTO;
import com.owner.process.usecases.visitors_dashboard.dto.VisitorsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorsDashboardService {

    @Autowired
    VisitorsDashboardDao visitorsDashboardDao;

    public List<VisitorsDTO> getVisitors(String plant) {
        return visitorsDashboardDao.getVisitors(plant);
    }

    public Integer saveVisitors(SaveVisitorsDTO saveVisitorsDTO) {
        return visitorsDashboardDao.saveVisitors(saveVisitorsDTO);
    }
}
