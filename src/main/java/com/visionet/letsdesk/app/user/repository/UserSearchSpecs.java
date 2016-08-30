package com.visionet.letsdesk.app.user.repository;

import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.user.entity.Role;
import com.visionet.letsdesk.app.user.entity.User;
import com.visionet.letsdesk.app.user.vo.UserVo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class UserSearchSpecs {
	public static Specification<User> searchUserByCondition(final UserVo user) {
		return new Specification<User>() {
		    
		      public Predicate toPredicate(Root<User> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
		    	  List<Predicate> list1 = new ArrayList<Predicate>();

//		    	  List<Predicate> list2 = new ArrayList<Predicate>();

                  if(Collections3.isNotEmpty(user.getRoleNameList())){
                      SetJoin<User, Role> roleJoin = r.join(r.getModel().getSet("roleSet",Role.class), JoinType.INNER);
                      list1.add(roleJoin.get("name").in(user.getRoleNameList()));
                  }
                  if(Validator.isNotNull(user.getQueryName())){
                      list1.add(cb.or(
                              cb.like(r.<String>get("loginName"), "%" + user.getQueryName() + "%"),
                              cb.like(r.<String>get("aliasName"), "%" + user.getQueryName() + "%"),
                              cb.like(r.<String>get("firstLetter"), "%" + user.getQueryName() + "%")
                      ));
                  }

		    	  //查询名称
		    	  String userName = Validator.isNull(user.getAliasName())?null:user.getAliasName();
		    	  String loginName= Validator.isNull(user.getLoginName())?null:user.getLoginName();
		    	  //按首字母查询
		    	  String firstLetter = Validator.isNull(user.getFirstLetter())?null:user.getFirstLetter();
		    	  if(null!=userName){
		    		  list1.add(cb.like(r.<String>get("aliasName"),"%"+userName+"%"));
		    	  }
		    	  if(null!=loginName){
                      list1.add(cb.like(r.<String>get("loginName"),"%"+loginName+"%"));
		    	  }
		    	  if(null!=firstLetter){
                      list1.add(cb.like(r.<String>get("firstLetter"),"%"+firstLetter+"%"));
		    	  }
                  if(null!=user.getUserType()){
                      list1.add(cb.equal(r.<String>get("userType"), user.getUserType()));
                  }
                  if(null!=user.getEmail()){
                      list1.add(cb.like(r.<String>get("email"),"%"+user.getEmail()+"%"));
                  }
                  if(null!=user.getPhoneNumber()){
                      list1.add(cb.equal(r.<String>get("phoneNumber"), user.getUserType()));
                  }
                  if(null!=user.getTrueName()){
                      list1.add(cb.equal(r.<String>get("trueName"), user.getUserType()));
                  }

                  if(user.getIsLock()!=null){
                      list1.add(cb.equal(r.<Long>get("isLock"), user.getIsLock()));
                  }
                  if(Validator.isNotNull(user.getOrgId())) {
                      list1.add(cb.equal(r.<Long>get("orgId"), user.getOrgId()));
                  }

//		    	  if(!list2.isEmpty()){
//		    		  Predicate[] predicate2 = new Predicate[list2.size()];
//		    		  list1.add(cb.and(cb.and(cb.equal(r.<String>get("companyId"),user.getCompanyId()),cb.equal(r.<Integer>get("userType"), 1)),cb.or(list2.toArray(predicate2))));
//		    	  }else{
//		    		  list1.add(cb.and(cb.equal(r.<String>get("companyId"),user.getCompanyId()),cb.equal(r.<Integer>get("userType"), 1)));
//		    	  }
		    	  Predicate[] predicate1 = new Predicate[list1.size()];
		    	  return cb.and(list1.toArray(predicate1));
		    	  
		      }
		};
	}

//	public static Specification<User> bySearchFilter(final Collection<SearchFilter> filters) {
//		return new Specification<User>() {
//			@SuppressWarnings({ "unchecked", "rawtypes" })
//			@Override
//			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//				if (Collections3.isNotEmpty(filters)) {
//
//					List<Predicate> predicates = Lists.newArrayList();
//					for (SearchFilter filter : filters) {
//
//						if("userName".equals(filter.fieldName)){
//							predicates.add(builder.or(
//									builder.like(root.<String>get("loginName"), "%"+filter.value+"%"),
//									builder.like(root.<String>get("aliasName"), "%"+filter.value+"%")
//							));
//							continue;
//						}
//
//						// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
//						String[] names = StringUtils.split(filter.fieldName, ".");
//						Path<String> expression = root.get(names[0]);
//						for (int i = 1; i < names.length; i++) {
//							expression = expression.get(names[i]);
//						}
//
//						// logic operator
//						switch (filter.operator) {
//						case EQ:
//							predicates.add(builder.equal(expression, filter.value));
//							break;
//						case LIKE:
//							predicates.add(builder.like(expression, "%" + filter.value + "%"));
//							break;
//						case GT:
//							predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
//							break;
//						case LT:
//							predicates.add(builder.lessThan(expression, (Comparable) filter.value));
//							break;
//						case GTE:
//							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
//							break;
//						case LTE:
//							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
//							break;
//						}
//					}
//
//					// 将所有条件用 and 联合起来
//					if (predicates.size() > 0) {
//						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
//					}
//				}
//
//				return builder.conjunction();
//			}
//		};
//	}
	
	

}
