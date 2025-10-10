package com.owner.process.usecases.visitors_dashboard;

import com.owner.process.usecases.visitors_dashboard.dto.SaveVisitorsDTO;
import com.owner.process.usecases.visitors_dashboard.dto.VisitorsDTO;

import java.util.List;

public interface VisitorsDashboardDao {
    List<VisitorsDTO> getVisitors(String plant);

    Integer saveVisitors(SaveVisitorsDTO saveVisitorsDTO);
}
