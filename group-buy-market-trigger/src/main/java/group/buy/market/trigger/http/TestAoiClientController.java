package group.buy.market.trigger.http;

import com.alibaba.fastjson.JSON;
import group.buy.market.api.dto.NotifyRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin("*")
@Slf4j
public class TestAoiClientController {

    @PostMapping("/group_buy_notify")
    public String groupBuyNotify(@RequestBody NotifyRequestDTO notifyRequestDTO) {
        log.info("第三方服务接收拼团回调 {}", JSON.toJSONString(notifyRequestDTO));
        return "success";
    }

}
