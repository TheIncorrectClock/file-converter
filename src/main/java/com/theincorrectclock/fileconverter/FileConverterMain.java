package com.theincorrectclock.fileconverter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.theincorrectclock.fileconverter.config.FileConverterConfig;

public class FileConverterMain {
	
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FileConverterConfig.class);
		
		FileConverter fc = context.getBean(FileConverter.class);
		if(args.length == 0 ){
			System.exit(1);
		} else if(args.length == 1) {
			fc.process(args[0], args[0].substring(0, args[0].lastIndexOf('.')));
		} else {
			fc.process(args[0], args[1]);
		}
		
		context.close();
	}
}
