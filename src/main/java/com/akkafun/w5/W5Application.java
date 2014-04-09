package com.akkafun.w5;

import com.akkafun.w5.code.service.CodeService;
import com.akkafun.platform.Application;
import com.akkafun.platform.Platform;
import com.akkafun.platform.exception.PlatformException;

import javax.servlet.ServletContext;
import java.util.Locale;

/**
 *
 */
public class W5Application extends Application {

	public static final String APP_NAME = "W5Application";
	
	public boolean init() {
		if(!super.init()) return false;
		
		try {
			// Set the default locale to custom locale
			Locale.setDefault(new Locale("zh", "CN"));
			
			ServletContext sc = this.getPlatform().getServletContext();
			
			//初始化数据字典
			CodeService codeService = Platform.getInstance().getBean(CodeService.class);
//			codeService.load();

			return true;
		} catch (Exception e) {
			logger.error("系统初始化错误！", e);
			return false;
		}
	}

	


}
