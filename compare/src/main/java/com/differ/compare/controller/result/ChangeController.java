package com.differ.compare.controller.result;

import com.differ.compare.entity.db.DatabaseInfo;
import com.differ.compare.entity.enumer.StatusCode;
import com.differ.compare.service.impl.DBChangeServiceImpl;
import com.differ.compare.service.impl.ParamValidationServiceImpl;
import com.differ.compare.utils.ApiResponseUtil;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/8 19:55
 */
@RestController
@RequestMapping("/changeApi/v1")
public class ChangeController {

//    @Autowired
    private DBChangeServiceImpl dbChangeService;
    private ParamValidationServiceImpl paramValidationService;

    @Autowired
    private void setInject(DBChangeServiceImpl dbChangeService, ParamValidationServiceImpl paramValidationService){
        this.dbChangeService = dbChangeService;
        this.paramValidationService = paramValidationService;
    }

//    @GetMapping(value = "getDBCompareByDBs")
    @PostMapping(value = "getDBCompareByDBs")
    public String getChanges(@RequestBody List<DatabaseInfo> dbs){
        StringBuffer sb = new StringBuffer();
        dbs.forEach(
                databaseInfo -> {
                    String v = paramValidationService.validateObject(databaseInfo);
                    if (StringUtils.isNotBlank(v)){
                        sb.append(v);
                    }
                }
        );
        if (StringUtils.isNotBlank(sb.toString())) return ApiResponseUtil.bulidResponse(StatusCode.FAILURE, "", sb.toString()).toString();
        return this.dbChangeService.getJsonResultByDB(dbs);
//        return "{\"Master\":{\"differ_user_pwd\": [[\"3\"], [\"4\"]]}}";
//        return "success";
    }

    @GetMapping(value = "getDBCompareByDBStrings")
    public void getChanges(@RequestBody String dbs){
//        List<DatabaseInfo> databaseInfos = FastjsonUtils.fromJsonList(dbs);
    }

}
