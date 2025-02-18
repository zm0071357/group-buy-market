package group.buy.market.api;


import group.buy.market.api.response.Response;

public interface DCCService {

    /**
     * 更新配置
     * @param key
     * @param value
     * @return
     */
    Response<Boolean> updateConfig(String key, String value);

}
