package log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestLog4j {
	public static void main(String[] args) {
		PropertyConfigurator.configure("C:\\Users\\Administrator\\Desktop\\temp.properties");
		Logger logger = Logger.getLogger(TestLog4j.class);
		logger.info("This is a info message");
		logger.error("This is a error message");
		logger.debug("This is a debug message");
	}
}
