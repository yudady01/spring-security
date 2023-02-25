package cn.jpanda.demo.manager.configuration.mybatis.plus;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.Instant;
import java.util.Date;

/**
 * 自动填充公共字段
 *
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/4 17:31:19
 */
public class FillMetaObjectHandler extends MetaObjectHandler {
    private static final String FILL_CREATE_TIME = "createTime";
    private static final String FILL_UPDATE_TIME = "updateTime";


    @Override
    public void insertFill(MetaObject metaObject) {
        setIfNull(metaObject, FILL_CREATE_TIME);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setIfNull(metaObject, FILL_UPDATE_TIME);
    }

    public void setIfNull(MetaObject o, String field, Date now) {
        if (null == getFieldValByName(field, o)) {
            return;
        }
        setFieldValByName(field, now, o);
    }

    public void setIfNull(MetaObject o, String field) {
        setIfNull(o, field, Date.from(Instant.now()));
    }
}
