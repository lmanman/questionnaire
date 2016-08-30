package com.visionet.letsdesk.app.common.utils;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;

public class BeanConvertMap {

	
	private static DozerBeanMapper  mapper = new DozerBeanMapper();
	static{
		List<String> list = Lists.newArrayList();
		list.add("dozerBeanMapping.xml");
		mapper.setMappingFiles(list);
	}
	
	public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
		if(sourceList==null){
			return null;
		}
		List<T> destinationList = Lists.newArrayList();
		for (Object sourceObject : sourceList) {
			
			T destinationObject = BeanConvertMap.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}
	
	/**
	 * 基于Dozer转换对象的类型.
	 */
	public static <T> T map(Object source, Class<T> destinationClass) {
		if(source==null){
			return null;
		}
//		if(destinationClass == TeamVo.class && source instanceof Team){
//
//			return (T)DtoUtil.TeamToVo((Team)source);
//		}else if(destinationClass == UserSimpleVo.class && source instanceof User){
//			return (T)DtoUtil.UserToUserSimpleVo((User)source);
//		}else{
			return mapper.map(source, destinationClass);
//		}
		
	}
	

}
