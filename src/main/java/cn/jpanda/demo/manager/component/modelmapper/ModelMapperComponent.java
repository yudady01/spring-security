package cn.jpanda.demo.manager.component.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class ModelMapperComponent {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper() {
            @Override
            public <D> D map(Object source, Class<D> destinationType) {
                if (source == null) {
                    return null;
                }
                return super.map(source, destinationType);
            }

            @Override
            public <D> D map(Object source, Type destinationType) {
                if (source == null) {
                    return null;
                }
                return super.map(source, destinationType);
            }
        };
    }
}
