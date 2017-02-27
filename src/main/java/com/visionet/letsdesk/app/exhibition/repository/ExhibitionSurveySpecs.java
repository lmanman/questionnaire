package com.visionet.letsdesk.app.exhibition.repository;

import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.common.utils.SearchFilterUtil;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurvey;
import com.visionet.letsdesk.app.foundation.KeyWord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ExhibitionSurveySpecs {


    public static Specification<ExhibitionSurvey> bySearchFilter(final Collection<SearchFilterUtil> filters) {
        return new Specification<ExhibitionSurvey>() {
            @Override
            public Predicate toPredicate(Root<ExhibitionSurvey> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (Collections3.isNotEmpty(filters)) {

                    List<Predicate> predicates = Lists.newArrayList();
                    for (SearchFilterUtil filter : filters) {

//                        if("tagSet".equals(filter.fieldName)){
//                            List<Tag> tagSet = (List<Tag>)filter.value;
//                            if(Collections3.isNotEmpty(tagSet)) {
//                                List<Long> tagIds = tagSet.stream().map(tag -> tag.getId()).collect(Collectors.toList());
//                                if (!tagIds.isEmpty()) {
//                                    // exists (select tagref1_.id from s_tag_ref tagref1_ where (tagref1_.tag_id in (? , ?)) and tagref1_.ref_id=message0_.id)
//                                    Subquery<TagRef> subquery = query.subquery(TagRef.class);
//                                    Root<TagRef> subRootEntity = subquery.from(TagRef.class);
//                                    subquery.select(subRootEntity);
//
//                                    Predicate predicate2 = builder.equal(subRootEntity.get("refId"), root.<String>get("id"));
//                                    Predicate predicate3 = builder.equal(subRootEntity.get("tagType"), KeyWord.TAG_TYPE_CUSTOMER);
//                                    Expression<?> exp = subRootEntity.get("tagId");
//                                    Predicate predicate1 = exp.in(tagIds);
//                                    subquery.where(builder.and(new Predicate[]{predicate1, predicate2,predicate3}));
//                                    predicates.add(builder.exists(subquery));
//                                }
//                                continue;
//                            }
//                        }

                        if("queryName".equals(filter.fieldName)){   //展厅名/品牌/品类
                            predicates.add(builder.or(
                                    builder.equal(root.<String>get("phoneNumber"), filter.value),
                                    builder.like(root.<String>get("customerName"), "%" + filter.value + "%"),
                                    builder.like(root.<String>get("email"), "%" + filter.value + "%")
                            ));
                            continue;
                        }

                        // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                        String[] names = StringUtils.split(filter.fieldName, ".");
                        Path expression = root.get(names[0]);
                        for (int i = 1; i < names.length; i++) {
                            expression = expression.get(names[i]);
                        }

                        // logic operator
                        switch (filter.operator) {
                            case EQ:
                                predicates.add(builder.equal(expression, filter.value));
                                break;
                            case LIKE:
                                predicates.add(builder.like(expression, "%" + filter.value + "%"));
                                break;
                            case GT:
                                predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                                break;
                            case LT:
                                predicates.add(builder.lessThan(expression, (Comparable) filter.value));
                                break;
                            case GTE:
                                predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case LTE:
                                predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case NEQ:
                                predicates.add(builder.notEqual(expression, filter.value));
                                break;
                            case IN:
                                Expression<?> exp = root.get(filter.fieldName);
                                predicates.add(exp.in((List)filter.value));
                                break;
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (predicates.size() > 0) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }

                }

                return builder.conjunction();
            }
        };
    }

}
