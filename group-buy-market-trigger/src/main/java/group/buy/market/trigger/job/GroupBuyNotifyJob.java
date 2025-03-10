package group.buy.market.trigger.job;

import group.buy.market.domain.trade.service.settlement.TradeSettlementOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 定时回调
 */
@Slf4j
@Service
public class GroupBuyNotifyJob {

    @Resource
    private TradeSettlementOrderService tradeSettlementOrderService;

    @Scheduled(cron = "* 0/30 * * * ?")
    public void exec() {
        try {
            Map<String, Integer> result = tradeSettlementOrderService.execSettlementNotifyJob();
            log.info("定时任务，回调通知拼团完结任务 result:{}", result);
        } catch (Exception e) {
            log.error("定时任务，回调通知拼团完结任务失败", e);
        }
    }


}
