package com.visionet.letsdesk.app.common.modules.collection;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.visionet.letsdesk.app.common.cache.EhcacheUtil;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.foundation.KeyWord;
import com.visionet.letsdesk.app.foundation.service.DashboardStatisticsService;
import com.visionet.letsdesk.app.common.modules.json.JsonDemo;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.user.entity.Role;
import com.visionet.letsdesk.app.user.vo.UserVo;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * 演示各种Collections如何简化Collection操作.
 * 
 * @author xt
 */
public class CollectionsUtil {

	public void init() {
		// 无需在等号右边重新定义泛型的创建ArrayList
		List<String> list = Lists.newArrayList();
		// 创建的同时初始化数据
		List<String> list2 = Lists.newArrayList("a", "b", "c");

		// 无需在等号右边重新定义泛型的创建HashMap
		Map<String, ? extends JsonDemo.User> map = Maps.newHashMap();
		// 创建Map的同时初始化值，不过这个map是不可修改的，主要用于编写测试用例。
		Map<Integer, String> unmodifiedMap = ImmutableMap.of(1, "foo", 2, "bar");
	}

	public void test() {
		List<String> list = Lists.newArrayList("a", "b", "c");
		List<String> list2 = Lists.newArrayList("a", "b");

		// nullsafe的判断是否为空
		assertFalse(Collections3.isEmpty(list));

		// 获取最后一个
		assertEquals("c", Collections3.getLast(list));

		// list+list2的新List
		List result = Collections3.union(list, list2);
		assertEquals("[a, b, c, a, b]", result.toString());

		// list-list2的新List
		result = Collections3.subtract(list, list2);
		assertEquals("[c]", result.toString());

		// list与list2的交集的新List
		result = Collections3.intersection(list, list2);
		assertEquals("[a, b]", result.toString());
	}

	public static void test8(){
		List<Object[]> list = Lists.newArrayList();
		list.add(new Object[]{1L,2L});
		list.add(new Object[]{13L,14L});
		list.add(new Object[]{5L,6L});
		list.add(new Object[]{8L,9L});

		List<Long> list2 = list.stream().map(obj -> (Long) obj[0]).collect(Collectors.toList());
		Collections.shuffle(list2);
		list2.stream().forEach(System.out::println);
		list2.stream().skip(1).forEach(System.out::println);
	}

	public static void testMap(){
		ConcurrentHashMap<Long,Integer> channelMap = new ConcurrentHashMap<>();
		channelMap.put(1L,11);
		channelMap.put(2L,22);
		channelMap.put(3L,33);
		int x = channelMap.reduce(1,(key, value) ->value,(a, b) -> a + b);
		System.out.println(x);

	}
	public static void testMap2(){
		ConcurrentHashMap<Long,ConcurrentHashMap<Long,AtomicInteger>> orgMap = new ConcurrentHashMap<>();

		ConcurrentHashMap<Long,AtomicInteger> channelMap1 = new ConcurrentHashMap<>();
		channelMap1.put(1L,new AtomicInteger(11));
		channelMap1.put(2L,new AtomicInteger(22));
		channelMap1.put(3L,new AtomicInteger(33));
		ConcurrentHashMap<Long,AtomicInteger> channelMap2 = new ConcurrentHashMap<>();
		channelMap2.put(1L,new AtomicInteger(-1));
		channelMap2.put(2L,new AtomicInteger(-2));
		channelMap2.put(3L,new AtomicInteger(-4));
		orgMap.put(1L,channelMap1);
		orgMap.put(2L,channelMap2);

		int x = orgMap.reduce(1, (key, value) -> value.reduce(1, (key2, value2) -> value2.get(), (a2, b2) -> a2 + b2), (a, b) -> a + b);
		System.out.println(x);

	}


	public static void testMap3(){
		ConcurrentHashMap<Long, ConcurrentHashMap<Long,AtomicInteger>> KefuTalkingNumMap = new ConcurrentHashMap<>();
		Long kefuId=11L,channelId=12L;
		ConcurrentHashMap<Long,AtomicInteger> channelMap = KefuTalkingNumMap.get(kefuId);
		if(channelMap==null) {
			channelMap = new ConcurrentHashMap() {{
				put(channelId, new AtomicInteger(1));
			}};
		}
		KefuTalkingNumMap.put(kefuId,channelMap);
		KefuTalkingNumMap.forEach((key,value)-> {
			System.out.println("---key1="+key);
			value.forEach((key2,value2) -> System.out.println(key2+"---xxx---"+value2.get()));
		});
	}


	public static void testMap4(){

		ConcurrentHashMap<Long,Long> map = new ConcurrentHashMap();
		map.put(3L,333L);
		map.put(1L,111L);
		map.put(2L,222L);
		map.put(4L,444L);

		Long result1 = map.search(1, (key, value) -> {
//			System.out.println(Thread.currentThread().getName());
			if (value.equals(222L)) {
				return key;
			}
			return null;
		});

		System.out.println(result1);


		LinkedHashMap<Long,Long> map2 = new LinkedHashMap();
		map.forEach((key,value) -> map2.put(value,key));


	}
	public static void testMap5(){
		List<Map<String,Object>> resultMap = Lists.newArrayList();
		ConcurrentHashMap<Long,AtomicInteger> channelMap = new ConcurrentHashMap(){
			{
				put(1L,new AtomicInteger(11));
				put(3L,new AtomicInteger(13));
			}
		};
		Map<String,String> dictMap = new HashMap(){
			{
				put("1","weixin");
				put("2","aaaa");
				put("3","tenxun");
				put("4","bbbb");
			}
		};
		dictMap.forEach((key, value) -> {
			if(channelMap.containsKey(new Long(key))){
				resultMap.add(new HashMap<String,Object>(){
					{
						put("name",value);
						put("num",channelMap.get(new Long(key)).get());
					}
				});
			}
		});
		resultMap.forEach(map->map.forEach((id, val) -> System.out.println(id+"-----"+val)));
	}

	public static void testMapInterface() throws InterruptedException{
//		AllocateLogicFactory.AllocateMap.forEach((key,value)-> System.out.println(key+"------"+value));

//		System.out.println(DashboardStatisticsService.GetKefuTalkingNum(3L));
//		DashboardStatisticsService.AddKefuTalkingNum(1L,11L);
//		DashboardStatisticsService.AddKefuTalkingNum(1L,12L);
//		DashboardStatisticsService.AddKefuTalkingNum(1L,12L);
//		DashboardStatisticsService.AddKefuTalkingNum(2L,13L);
//		System.out.println(DashboardStatisticsService.GetKefuTalkingNum(1L));
//		System.out.println(DashboardStatisticsService.GetKefuTalkingNum(1L,12L));
//		System.out.println(DashboardStatisticsService.GetKefuTalkingNum(1L,11L));


	}
	public static void testList(){
//		List<Long> list  = Collections.synchronizedList(new ArrayList<Long>());
//		list.add(1L);
//		list.add(2L);
//		list.add(3L);
//		list.add(4L);
//		list.add(5L);
//		list.remove(2L);
//		list.remove(3L);
//		list.remove(8L);
//		list.forEach(System.out::println);

//		List<Long> list1 = Lists.newArrayList(1L,2L,3L,4L,5L);
//		List<Long> list2 = Lists.newArrayList(8L,9L);
//		List<Long> resultList = list1.stream().filter(id -> list2.contains(id)).collect(Collectors.toList());
//		resultList.forEach(i-> System.out.println(i));

        DashboardStatisticsService.AddKefuTalkingNum(1L,11L);
		DashboardStatisticsService.AddKefuTalkingNum(1L,2L);
		DashboardStatisticsService.AddKefuTalkingNum(1L,2L);
		DashboardStatisticsService.AddKefuTalkingNum(2L,13L);
		System.out.println(DashboardStatisticsService.GetKefuTalkingNum(1L, 2L));
		System.out.println(DashboardStatisticsService.GetKefuTalkingNum(1L));

//		List<Long> onlineList=Lists.newArrayList(1L,2L,3L,4L);
//		List<UserVo> voList =
//				onlineList.stream().map(id -> BeanConvertMap.map(EhcacheUtil.GetUser(id), UserVo.class))
//						.map(user -> {
//							user.setTalkingNum(DashboardStatisticsService.GetKefuTalkingNum(user.getId()));
//							return user;
//						})
//						.collect(Collectors.toList());
//
//		voList.forEach(user -> System.out.println(user.getId()+"----"+user.getTalkingNum()));
	}

	public static void testList2(List<String> roleName){
		Set<Role> roleSet = Sets.newHashSet();
		roleSet.add(new Role(1L,"r1"));
		roleSet.add(new Role(2L,"r2"));
		roleSet.add(new Role(3L,"r3"));

//		long num= roleSet.stream().filter(r->r.getName().equals(roleName)).count();
		boolean xx = roleSet.stream().anyMatch(r -> roleName.contains(r.getName()));
		System.out.println(xx);
	}
	public static void testQueue() throws Exception{
		ArrayBlockingQueue<Long> queue = new ArrayBlockingQueue<Long>(100);
		queue.put(1L);
		queue.put(5L);
		queue.put(4L);
		queue.put(7L);
		queue.put(2L);

		System.out.println(queue.contains(2L));
		queue.iterator().forEachRemaining(i -> System.out.println(i));
		System.out.println(queue.remove(4L));
		queue.iterator().forEachRemaining(i -> System.out.println(i));
	}

	public static void main(String[] args) {
		try {
			testMapInterface();


		}catch (Exception e){
			e.printStackTrace();
		}

	}
}
