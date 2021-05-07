package demo4.Controller;

import demo4.Service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class ChartController {

    @Autowired
    private ChartService chartService;

    /*["",""]*/
    @GetMapping("/getChartData")
    public String getChartData(){
        String resq = chartService.getAge();
        return resq;
    }

    /*    {value: 1048, name: '搜索引擎'}*/
    @GetMapping("/getChartData2")
    public List<Map<String, Object>> getChartData2(){
        List<Map<String, Object>> resultMap = chartService.getSex();
        return resultMap;
    }
}
