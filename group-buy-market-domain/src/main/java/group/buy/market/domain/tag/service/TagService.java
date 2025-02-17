package group.buy.market.domain.tag.service;


public interface TagService {

    /**
     * 执行人群标签批次任务
     * @param tagId 人群ID
     * @param batchId 批次ID
     */
    void execTagBatchJob(String tagId, String batchId);

}
