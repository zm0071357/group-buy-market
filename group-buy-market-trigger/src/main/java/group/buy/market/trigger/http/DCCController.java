package group.buy.market.trigger.http;

import group.buy.market.api.DCCService;
import group.buy.market.api.response.Response;
import group.buy.market.types.enums.ResponseCode;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/api/gbm/dcc")
@CrossOrigin("*")
public class DCCController implements DCCService {

    @Resource
    private RTopic dccTopic;

    @PostMapping("/updateConfig")
    @Override
    public Response<Boolean> updateConfig(@RequestParam String key, @RequestParam String value) {
        try {
            dccTopic.publish(key + "," + value);
            return Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .build();
        } catch (Exception e) {
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
