package xyz.yuanmo.springboot.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.Converter;
import org.jooq.ConverterProvider;
import org.jooq.JSON;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.DefaultConverterProvider;

/**
 * @author <a href="https://github.com/Matthew-Han">Matthew Han</a>
 * @date 2022/2/23 16:32 23
 * @since 1.0
 **/
public class JSONConverterProvider implements ConverterProvider {

    final ConverterProvider delegate = new DefaultConverterProvider();
    final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T, U> Converter<T, U> provide(Class<T> tType, Class<U> uType) {

        // Our specialised implementation can convert from JSON (optionally, add JSONB, too)
        if (tType == JSON.class) {
            return Converter.ofNullable(tType, uType, t -> {
                try {
                    return mapper.readValue(((JSON) t).data(), uType);
                } catch (Exception e) {
                    throw new DataTypeException("JSON mapping error", e);
                }
            }, u -> {
                try {
                    return (T) JSON.valueOf(mapper.writeValueAsString(u));
                } catch (Exception e) {
                    throw new DataTypeException("JSON mapping error", e);
                }
            });
        }

        // Delegate all other type pairs to jOOQ's default
        else {
            return delegate.provide(tType, uType);
        }
    }




}
