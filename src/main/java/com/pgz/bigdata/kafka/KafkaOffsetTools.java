package com.pgz.bigdata.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * kafka工具类
 *
 * @author liquan_pgz@qq.com
 * @date 2021-01-22
 */
@Slf4j
@Component
public class KafkaOffsetTools {

    private static KafkaOffsetTools _this;

    @Autowired
    private ConsumerFactory<Long, String> consumerFactory;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostConstruct
    public void init() {
        _this = this;
    }

    /**
     * 获取lag
     *
     * @param topics
     * @return
     * @author liquan_pgz@qq.com
     * date 2021-01-22
     **/
    public static Long getLAG(String... topics) {
        return getDescribe(topics)[2];
    }

    /**
     * 获取分组下的表述信息
     *
     * @param topics
     * @return
     * @author liquan_pgz@qq.com
     * date 2021-01-22
     **/
    private static long[] getDescribe(String... topics) {
        long[] describe = new long[3];
        Consumer<Long, String> consumer = createConsumer();
        for (String topic : topics) {
            List<PartitionInfo> partitionInfos = _this.kafkaTemplate.partitionsFor(topic);
            List<TopicPartition> tp = new ArrayList<>();
            partitionInfos.forEach(str -> {
                TopicPartition topicPartition = new TopicPartition(topic, str.partition());
                tp.add(topicPartition);
                long logEndOffset = consumer.endOffsets(tp).get(topicPartition);
                consumer.assign(tp);
                consumer.position(topicPartition);
                long currentOffset = consumer.position(topicPartition);
                describe[0] += currentOffset;
                describe[1] += logEndOffset;
                describe[2] = describe[1] - describe[0];
            });
        }
        log.info(Arrays.toString(describe));
        return describe;
    }

    /**
     * 创建消费者
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2021-01-22
     **/
    private static Consumer<Long, String> createConsumer() {
        return _this.consumerFactory.createConsumer();
    }
}
