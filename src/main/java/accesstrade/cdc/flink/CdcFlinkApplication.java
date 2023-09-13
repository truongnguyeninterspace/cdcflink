package accesstrade.cdc.flink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CdcFlinkApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(
                CdcFlinkApplication.class, args);
             PipelineInit pipelineInit = new PipelineInit(applicationContext);
        pipelineInit.init();
    }

}
