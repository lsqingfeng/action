package com.lsqingfeng.action.es.util;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.internal.util.Assert;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author cuihaochong
 * @date 2020/6/5
 */
public class ModelMapperUtil {

    public static <D> D map(Object source, Class<D> destinationType) {
        Assert.notNull(source, "source");
        Assert.notNull(destinationType, "destinationType");
        return new ModelMapper().map(source, destinationType);
    }

    public static <D> D map(Object source, Type destinationType) {
        Assert.notNull(source, "source");
        Assert.notNull(destinationType, "destinationType");
        return new ModelMapper().map(source, destinationType);
    }



    public static <T, S> T map(S source, Class<T> targetClass, String dataFormat) {
        if (source == null) {
            return null;
        } else {
            ModelMapper mapper = new ModelMapper();
            mapper.addConverter(dateToStrConverter(dataFormat));
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            return mapper.map(source, targetClass);
        }
    }

    private static org.modelmapper.Converter<Date, String> dateToStrConverter(final String dataFormat) {
        return new AbstractConverter<Date, String>() {
            @Override
            protected String convert(Date source) {
                if (source != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
                    return sdf.format(source);
                }
                return null;

            }
        };
    }
}
