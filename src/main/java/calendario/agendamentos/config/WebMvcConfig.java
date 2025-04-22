package calendario.agendamentos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import calendario.agendamentos.interceptadores.CacheInterceptor;



@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
    @Autowired
    private CacheInterceptor cacheInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cacheInterceptor);
    }

}
