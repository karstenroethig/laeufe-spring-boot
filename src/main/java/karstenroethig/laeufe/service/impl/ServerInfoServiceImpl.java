package karstenroethig.laeufe.service.impl;

import java.util.Date;
import java.util.Locale;

import karstenroethig.laeufe.dto.info.MemoryInfoDto;
import karstenroethig.laeufe.dto.info.ServerInfoDto;
import karstenroethig.laeufe.dto.info.SystemInfoDto;
import karstenroethig.laeufe.service.ServerInfoService;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ServerInfoServiceImpl implements ServerInfoService, ApplicationContextAware {
	
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public ServerInfoDto getInfo() {
		
		long serverStartupTime = applicationContext.getStartupDate();
		
		/*
		 * collect system infos
		 */
		SystemInfoDto systemInfo = new SystemInfoDto();
		
		systemInfo.setVersion( "1.0.0" ); // TODO tatsächliche Version ermitteln
		systemInfo.setServerTime( new Date().toString() );
		systemInfo.setUptimeMillis( System.currentTimeMillis() - serverStartupTime );
		systemInfo.setUptime( formatUptime( systemInfo.getUptimeMillis() ) );
		systemInfo.setJavaVersion( System.getProperty( "java.version" ) );
		systemInfo.setJavaVendor( System.getProperty( "java.vendor" ) );
		systemInfo.setJavaVm( System.getProperty( "java.vm.name" ) );
		systemInfo.setJavaVmVersion( System.getProperty( "java.vm.version" ) );
		systemInfo.setJavaRuntime( System.getProperty( "java.runtime.name" ) );
		systemInfo.setJavaHome( System.getProperty( "java.home" ) );
		systemInfo.setOsName( System.getProperty( "os.name" ) );
		systemInfo.setOsArchitecture( System.getProperty( "os.arch" ) );
		systemInfo.setOsVersion( System.getProperty( "os.version" ) );
		systemInfo.setFileEncoding( System.getProperty( "file.encoding" ) );
		systemInfo.setUserName( System.getProperty( "user.name" ) );
		systemInfo.setUserDir( System.getProperty( "user.dir" ) );
		systemInfo.setUserTimezone( System.getProperty( "user.timezone" ) );
		
		if( ( System.getProperty( "user.country" ) != null ) && ( System.getProperty( "user.language" ) != null ) ) {
			systemInfo.setUserLocale( new Locale( System.getProperty( "user.country" ), System.getProperty( "user.language" ) ).toString() );
		}
		
		/*
		 * collect memory infos
		 */
		MemoryInfoDto memoryInfo = new MemoryInfoDto();
		Runtime runtime = Runtime.getRuntime();
		
		memoryInfo.setTotal( runtime.maxMemory() );
		memoryInfo.setTotalFormated( formatMemory( memoryInfo.getTotal() ) );
		memoryInfo.setUsed( runtime.totalMemory() - runtime.freeMemory() );
		memoryInfo.setUsedFormated( formatMemory( memoryInfo.getUsed() ) );
		memoryInfo.setFree( memoryInfo.getTotal() - memoryInfo.getUsed() );
		memoryInfo.setFreeFormated( formatMemory( memoryInfo.getFree() ) );
		memoryInfo.setFreePercentage( memoryInfo.getFree() * 100 / memoryInfo.getTotal() );
		
		/*
		 * put system info and memory info to server info
		 */
		ServerInfoDto info = new ServerInfoDto();
		
		info.setSystemInfo( systemInfo );
		info.setMemoryInfo( memoryInfo );
		
		return info;
	}
	
	private static String formatUptime( long uptime ) {
		
		long diffInSeconds = uptime / 1000;
		long[] diff = new long[] {0, 0, 0, 0 }; // sec
		diff[3] = ( ( diffInSeconds >= 60 ) ? ( diffInSeconds % 60 ) : diffInSeconds ); // min
		diff[2] = ( ( diffInSeconds = ( diffInSeconds / 60 ) ) >= 60 ) ? ( diffInSeconds % 60 ) : diffInSeconds; // hours
		diff[1] = ( ( diffInSeconds = ( diffInSeconds / 60 ) ) >= 24 ) ? ( diffInSeconds % 24 ) : diffInSeconds; // days
		diff[0] = ( diffInSeconds = ( diffInSeconds / 24 ) );
		
		return String.format( "%d day%s, %d hour%s, %d minute%s, %d second%s", diff[0], ( diff[0] != 1 ) ? "s" : "",
				diff[1], ( diff[1] != 1 ) ? "s" : "", diff[2], ( diff[2] != 1 ) ? "s" : "", diff[3],
				( diff[3] != 1 ) ? "s" : "" );
	}
	
	private static String formatMemory( long bytes ) {
		
		if( bytes > ( 1024L * 1024L ) ) {
			return ( bytes / ( 1024L * 1024L ) ) + " MB";
		} else if( bytes > 1024L ) {
			return ( bytes / ( 1024L ) ) + " kB";
		} else {
			return bytes + " B";
		}
	}
}