package cn.jinronga.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FunnelRateLimiter
 * @Author 郭金荣
 * @Date 2021/4/17 15:05
 * @Description FunnelRateLimiter
 * @Version 1.0
 */
public class FunnelRateLimiter {
    static class Funnel {
        int capacity;
        float leakingRake;
        int leftQuota;
        long leakingTs;

        public Funnel(int capacity, float leakingRake, int leftQuota, long leakingTs) {
            this.capacity = capacity;//漏斗容量
            this.leakingRake = leakingRake;// 漏嘴流水速率
            this.leftQuota = leftQuota; //漏斗剩余空间
            this.leakingTs = leakingTs;// 上一次漏水时间
        }

        public Funnel(int capacity, float leakingRake) {
            this.capacity = capacity;
            this.leakingRake = leakingRake;
        }

        void makeSpace() {
            long newTs = System.currentTimeMillis();
            long delaTs = newTs - leakingTs;//距离上一次漏水过去了多久
            int deltaQuota = (int) (delaTs * leakingRake);//又可以腾出不少空间了
            if (deltaQuota < 0) {
                this.leftQuota = capacity;
                this.leakingTs = newTs;
                return;
            }
            if (deltaQuota < 1) {//腾出空间太小，最小单位是 1
                return;
            }
            this.leftQuota += deltaQuota;
            this.leakingTs = newTs;//记录漏水时间
            if (this.leftQuota > this.capacity) {//剩余空间不得高于容量
                this.leftQuota = this.capacity;
            }

        }

        boolean watering(int quota) {
            makeSpace();
            if (this.leftQuota >= quota) {
                this.leftQuota = quota;//判断剩余空间是否足够
                return true;
            }
            return false;
        }
    }

    private Map<String, Funnel> funnels = new HashMap<>();

    public boolean isActionAllowed(String userId, String actionKey, int capacity, float leakingRate) {
        String key = String.format("%s:%s", userId, actionKey);
        Funnel funnel = funnels.get(key);
        if (funnel == null) {
            funnel = new Funnel(capacity, leakingRate);
            funnels.put(key, funnel);
        }
        return funnel.watering(1);
    }

}
